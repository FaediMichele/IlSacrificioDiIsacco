package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * Mentality for {@link Monstro}.
 */
public class MonstroBossMentalityComponent extends AbstractMentalityComponent {
    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_COLLIDE = new HashSet<>(
            Arrays.asList(EnemyTearsMentalityComponent.class));
    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_DAMAGE = new HashSet<>(
            Arrays.asList(MonstroBossMentalityComponent.class, EnemyMentalityComponent.class, EnemyTearsMentalityComponent.class));
    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_HURTME = new HashSet<>(
            Arrays.asList(MonstroBossMentalityComponent.class, EnemyMentalityComponent.class, EnemyTearsMentalityComponent.class));

    /**
     * Create a new flying mentality component.
     * @param entity the entity that have the mentality.
     */
    public MonstroBossMentalityComponent(final Entity entity) {
        super(entity, CANNOT_DAMAGE, CANNOT_HURTME, CANNOT_COLLIDE);
    }

}
