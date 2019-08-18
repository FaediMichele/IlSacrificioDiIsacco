package model.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import model.component.BodyComponent;
import model.component.PlayerHealthComponent;
import model.component.InventoryComponent;
import model.entity.Bomb;
import model.entity.Entity;
import model.entity.Key;
import model.entity.Player;
import model.enumeration.GameEndStatus;
import model.enumeration.HeartEnum;
import model.events.FloorChangedEvent;
import model.events.InputEvent;
import model.events.RoomChangedEvent;
import model.util.EntityInformation;
import model.util.Position;
import model.util.StatisticsInformations;
import util.Command;
import util.EventListener;
import util.NotEquals;
import util.NotHashCode;
import util.Pair;
import util.StaticMethodsUtils;

/**
 * 
 *
 */
public class GameWorldImpl implements GameWorld {

    private final List<Floor> floors;
    private int activeFloor;
    private boolean changedFloor;
    @NotEquals
    @NotHashCode
    private final EventListener<RoomChangedEvent> changeRoom;
    @NotEquals
    @NotHashCode
    private final EventBus eventBus = new EventBus();
    @NotEquals
    @NotHashCode
    private final Player player;
    private boolean win;

    /**
     * 
     * @param game 
     * @param typePlayer 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws ClassNotFoundException 
     */
    public GameWorldImpl(final String game, final Player typePlayer)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        this.changeRoom = new EventListener<RoomChangedEvent>() {
                                @Override
                                @Subscribe
                                public void listenEvent(final RoomChangedEvent event) {
                                    eventBus.post(event);
                                }
                          };
        this.player = typePlayer;
        this.activeFloor = 0;
        this.floors = new LinkedList<>();
        final Document docXML = StaticMethodsUtils.getDocumentXML("/xml/Game.xml");
        if (docXML != null) {
            final List<Node> ls = StaticMethodsUtils
                    .getNodesFromNodelList(docXML.getElementsByTagName(game)
                    .item(0)
                    .getChildNodes());

            final Optional<Node> node = ls.stream()
                                          .filter(n -> n.getNodeName()
                                          .equals("Floors"))
                                          .findFirst();

            if (node.isPresent()) {
                final NodeList nl = node.get().getChildNodes();
                for (int i = 0; i < nl.getLength(); i++) {
                    final Node floor = nl.item(i);
                    if (floor.getNodeType() == Node.ELEMENT_NODE) {
                        final String floorName = floor.getNodeName();
                        this.floors.add(new FloorImpl(floorName, this));
                    }
                }
            } else {
                //floors.add(0, new FloorImpl());
                throw new IllegalArgumentException();
            }
            changedFloor = false;
            getActiveFloor().getActiveRoom().insertEntity(player);
            getActiveFloor().getActiveRoom().updateEntityList();
            this.player.getComponent(BodyComponent.class).get().setPosition(new Position(this.player.getRoom().getWidth() / 2, this.player.getRoom().getHeight() / 2, 0.0));
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
    public final GameEndStatus update(final double deltaTime) {
        getActiveFloor().update(deltaTime);
        changedFloor = false;
        if (win) {
            return GameEndStatus.WIN;
        }
        return player.getComponent(PlayerHealthComponent.class).get().isAlive()
                ? GameEndStatus.RUNNING : GameEndStatus.LOOSE;
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
    public final void input(final PriorityQueue<Command> c) {
        // da correggere
        player.postEvent(new InputEvent(player, c));
    }

    /**
     * 
     * @return .
     */
    public StatisticsInformations getStatistics() {
        final List<Entity> things = this.getPlayer().getComponent(InventoryComponent.class).get().getThings();
        final List<Pair<HeartEnum, Double>> hearts = this.getPlayer().getComponent(PlayerHealthComponent.class).get()
                .getHearts().stream().map(h -> new Pair<HeartEnum, Double>(h.getColour(), h.getValue()))
                .collect(Collectors.toList());
        return new StatisticsInformations()
                .setBombs(things.stream().filter(t -> t.getClass().isInstance(Bomb.class)).collect(Collectors.toList())
                        .size())
                .setKeys(things.stream().filter(t -> t.getClass().isInstance(Key.class)).collect(Collectors.toList())
                        .size())
                .setHearts(hearts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EntityInformation> getEntityInformation() {
        return new ArrayList<EntityInformation>(getActiveFloor().getActiveRoom().getEntitiesStatus());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChangeRoom() {
        return getActiveFloor().isChangeRoom();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextFloor() {
        activeFloor++;
        if (activeFloor >= floors.size()) {
            win = true;
        }
    }

}
