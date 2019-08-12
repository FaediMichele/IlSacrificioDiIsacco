package model.component;

import model.entity.Entity;

/**
 * Abstract Class for the entities that needs to have an Artificial Intelligence.
 */

public abstract class AbstractAIComponent extends AbstractComponent<AbstractAIComponent> {

    AbstractAIComponent(final Entity entity) {
        super(entity);
    }

    /**
     * Moves the entity a reasonable number of times based on the deltaTime.
     * @param deltaTime time since the last update
     */
    public void update(final Double deltaTime) {
        moveUpdate();
    }

    /**
     * The move that needs to me done must be implemented in the specific AI.
     */
    protected abstract void moveUpdate();

    /**
     * Getter for the MoveComponent of the Entity.
     * @param e entity to which this component is attached
     * @return the MoveComponent
     */
    protected MoveComponent getMoveComponent(final Entity e) {
        if (e.getComponent(MoveComponent.class).isPresent()) {
            return e.getComponent(MoveComponent.class).get();
        } else {
            throw new IllegalStateException();
        }
    }
}
