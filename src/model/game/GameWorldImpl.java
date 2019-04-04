package model.game;

import java.util.LinkedList;
import java.util.List;

import model.entity.Player;

/**
 * 
 *
 */
public class GameWorldImpl implements GameWorld {

    private final Player player;
    private final List<Floor> floors;

    /**
     * @param player {@link Player}
     */
    public GameWorldImpl(final Player player) {
        this.floors = new LinkedList<>();
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Floor> getFloors() {
        return new LinkedList<>(this.floors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Floor getActiveFloor() {
        return this.floors.stream().filter(f -> f.isActive()).findFirst().get();
    }

}
