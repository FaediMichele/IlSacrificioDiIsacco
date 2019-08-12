package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * Mentality of the {@link Enemy}.
 */
public class EnemyMentalityComponent extends AbstractMentalityComponent {

    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_HURT_ME = new HashSet<>(
            Arrays.asList(PlayerMentalityComponent.class, 
                          NeutralMentalityComponent.class));

    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_DAMAGE = new HashSet<>(
            Arrays.asList(NeutralMentalityComponent.class));
 
    /**
     * 
     * @param entity the {@link Entity}
     */
    public EnemyMentalityComponent(final Entity entity) {
        super(entity, CANNOT_DAMAGE, CANNOT_HURT_ME, new HashSet<>());
    }
}
