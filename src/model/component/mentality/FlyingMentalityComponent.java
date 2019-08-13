package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.component.ObstacleMentalityComponent;
import model.entity.Entity;

/**
 * Mentality for the entity that fly.
 */
public class FlyingMentalityComponent extends AbstractMentalityComponent {
    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_COLLIDE = new HashSet<>(
            Arrays.asList(ObstacleMentalityComponent.class));

    /**
     * Create a new flying mentality component.
     * @param entity the entity that have the mentality.
     */
    public FlyingMentalityComponent(final Entity entity) {
        super(entity, new HashSet<>(), new HashSet<>(), CANNOT_COLLIDE);
    }

}
