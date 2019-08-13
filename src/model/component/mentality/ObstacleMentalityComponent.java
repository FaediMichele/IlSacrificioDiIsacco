package model.component.mentality;

import java.util.HashSet;

import model.entity.Entity;

/**
 * This component is used to define a entity that operate as a wall for the path finding algorithm
 * Should remain empty.
 */
public class ObstacleMentalityComponent extends AbstractMentalityComponent {
    /**
     * Create a component and assign it to the entity.
     * @param entity the entity of this component
     */
    public ObstacleMentalityComponent(final Entity entity) {
        super(entity, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

}
