package model.component;

import model.entity.Entity;

/**
 * Component for entities that permit to change the floor.
 */
public abstract class WinComponent extends AbstractComponent {
    /**
     * Component that win the floor.
     * @param e the entity.
     */
    public WinComponent(final Entity e) {
        super(e);
    }

    /**
     * Change the floor.
     */
    public void win() {
        this.getEntity().getRoom().getFloor().getGameWorld().nextFloor();
    }
}
