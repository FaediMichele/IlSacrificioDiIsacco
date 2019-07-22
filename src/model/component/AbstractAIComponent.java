package model.component;

import model.entity.Entity;

/**
 * Abstract Class for the entities that needs to have an Artificial Intelligence.
 */

public abstract class AbstractAIComponent extends AbstractComponent<AbstractAIComponent> {

    private static final double TIME_BETWEEN_MOVES = 200;

    AbstractAIComponent(final Entity entity) {
        super(entity);
    }

    /**
     * Moves the entity a reasonable number of times based on the deltaTime.
     * @param deltaTime time since the last update
     */
    public void update(final Double deltaTime) {
        Double time = deltaTime;
        while (time > TIME_BETWEEN_MOVES) {
           moveUpdate();
           time -= TIME_BETWEEN_MOVES;
        }
    }

    /**
     * The move that needs to me done must be implemented in the specific AI.
     */
    protected abstract void moveUpdate();

    /**
     * Getter for the MoveComponent of the Entity.
     * @param e entity to which this componente is attached
     * @return the MoveComponent
     */
    protected MoveComponent getMoveComponent(final Entity e) {
        return ((MoveComponent) e.getComponent(MoveComponent.class).get());
    }
}
