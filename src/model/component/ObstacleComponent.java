package model.component;

import model.entity.Entity;

/**
 * This component is used to define a entity that operate as a wall for the path finding algorithm
 * Should remain empty.
 */
public class ObstacleComponent extends AbstractComponent<ObstacleComponent> {
    /**
     * Create a component and assign it to the entity.
     * @param entity the entity of this component
     */
    public ObstacleComponent(final Entity entity) {
        super(entity);
    }

}
