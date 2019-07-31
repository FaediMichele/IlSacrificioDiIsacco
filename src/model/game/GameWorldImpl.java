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
import model.events.InputEvent;
import model.events.RoomChangedEvent;
import util.EventListener;
import util.NotEquals;
import util.NotHashCode;
import util.StaticMethodsUtils;
import view.Command;

/**
 * 
 *
 */
public class GameWorldImpl implements GameWorld {
    @NotEquals
    @NotHashCode
    private final Player player;
    private final List<Floor> floors;
    @NotEquals
    @NotHashCode
    private final EventBus eventBus = new EventBus();
    private int activeFloor;
    private boolean changedFloor;

    @NotEquals
    @NotHashCode
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
        changedFloor = false;
    }

    /**
     * Initialize the {@link GameWorld} via xml.
     * 
     * @param game the game settings
     * @param typePlayer is type player start game.
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    //da fixare il type player con cui deve iniziare il gioco
    public GameWorldImpl(final String game, final Player typePlayer)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final Document docXML = StaticMethodsUtils.getDocumentXML("/xml/Game.xml");
        final List<Node> ls = StaticMethodsUtils.getNodesFromNodelList(docXML.getElementsByTagName(game).item(0).getChildNodes());
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
        changedFloor = false;
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
        changedFloor = false;
    }

    @Override
    public final void setActiveFloor(final Integer activeFloor) {
        if (this.activeFloor != activeFloor) {
            getActiveFloor().unregisterListener(changeRoom);
            eventBus.post(new FloorChangedEvent(getActiveFloor(), floors.get(activeFloor)));
            changedFloor = true;
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
        return changedFloor;
    }

    @Override
    public final boolean equals(final Object obj) {
        return StaticMethodsUtils.equals(this, obj);
    }

    @Override
    public final int hashCode() {
        return StaticMethodsUtils.hashCode(this);
    }

    @Override
    public final void input(final Command c) {
        //da correggere
        player.postEvent(new InputEvent(new Player(), c));
    }

}
