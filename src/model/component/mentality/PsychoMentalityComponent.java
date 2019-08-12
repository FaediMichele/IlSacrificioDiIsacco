package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * Mentality for a psycho entity.
 */
public class PsychoMentalityComponent extends AbstractMentalityComponent {

    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_HURT_ME = new HashSet<>(
                                                                                                Arrays.asList(EnemyMentalityComponent.class, 
                                                                                                              NeutralMentalityComponent.class));

    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_DAMAGE = new HashSet<>();
    /**
     * 
     * @param entity the {@link Entity}
     */
    public PsychoMentalityComponent(final Entity entity) {
        super(entity, CANNOT_DAMAGE, CANNOT_HURT_ME, new HashSet<>());
    }
}
