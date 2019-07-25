package model.game;

import java.util.LinkedList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import model.entity.Player;
import model.events.CollisionEvent;
import model.events.Event;
import model.events.FloorChangedEvent;
import model.events.RoomChangedEvent;
import util.EventListener;

/**
 * 
 *
 */
public class GameWorldImpl implements GameWorld {
    private final Player player;
    private final List<Floor> floors;
    private final EventBus eventBus = new EventBus();
    private int activeFloor;
    private final EventListener<RoomChangedEvent> changeRoom = new EventListener<RoomChangedEvent>() {
        @Override
        @Subscribe
        public void listenEvent(final RoomChangedEvent event) {
            eventBus.post(event);
        }
    };

    /**
     * Create a new Game World.
     */
    public GameWorldImpl() {
        this.floors = new LinkedList<>();
        floors.add(0, new FloorImpl());
        this.player = new Player();
        this.activeFloor = 0;
    }

    @Override
    public final Player getPlayer() {
        return this.player;
    }

    @Override
    public final List<Floor> getFloors() {
        return new LinkedList<>(this.floors);
    }

    @Override
    public final Floor getActiveFloor() {
        return this.floors.get(activeFloor);
    }

    @Override
    public final void update(final double deltaTime) {
        getActiveFloor().update(deltaTime);
        getActiveFloor().calculateCollision();
    }

    @Override
    public final void setActiveFloor(final Integer activeFloor) {
        if (this.activeFloor != activeFloor) {
            getActiveFloor().unregisterListener(changeRoom);
            eventBus.post(new FloorChangedEvent(getActiveFloor(), floors.get(activeFloor)));
        }
        this.activeFloor = activeFloor;
        getActiveFloor().registerListener(changeRoom);
    }

    @Override
    public final void registerListener(final EventListener<?> eventListener) {
        this.eventBus.register(eventListener);
    }

    @Override
    public final void unregisterListener(final EventListener<?> eventListener) {
        this.eventBus.unregister(eventListener);
    }

}
