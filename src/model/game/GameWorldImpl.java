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
    private int activeFloor = 0;

    /**
     * @param player {@link Player}
     */
    public GameWorldImpl(final Player player) {
        this.floors = new LinkedList<>();
        this.player = player;
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
    public final void setActiveFloor(final Integer activeFloor) {
        this.activeFloor = activeFloor;
    }

}
