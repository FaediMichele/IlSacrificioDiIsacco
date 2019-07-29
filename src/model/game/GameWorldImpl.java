package model.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import model.entity.Player;
import model.events.FloorChangedEvent;
import model.events.RoomChangedEvent;
import util.EventListener;
import util.StaticMethodsUtils;

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

    /**
     * Initialize the {@link GameWorld} via xml.
     * 
     * @param game the game settings
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public GameWorldImpl(final String game) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final Document docXML = StaticMethodsUtils.getDocumentXML("/xml/Game.xml");
        final List<Node> ls = StaticMethodsUtils.getNodesFromNodelList(docXML.getElementsByTagName(game));
        final Optional<Node> node = ls.stream().filter(n -> n.getNodeName().equals("Floors")).findFirst();
        this.player = new Player();
        this.activeFloor = 0;
        this.floors = new LinkedList<>();
        if (node.isPresent()) {
            final NodeList nl = node.get().getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                final Node floor = nl.item(i);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    final String floorName = floor.getNodeName();
                    this.floors.add(new FloorImpl(floorName));
                }
            }
        } else {
            floors.add(0, new FloorImpl());
        }
    }

    @Override
    public final Player getPlayer() {
        return this.player;
    }

    @Override
    public final void addFloor(final Floor floor) {
        Objects.requireNonNull(floor);
        this.floors.add(floor);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChangeFloor() {
        // TODO Auto-generated method stub
        return false;
    }

}
