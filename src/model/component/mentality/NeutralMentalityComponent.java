package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import model.entity.Entity;

/**
 * Mentality for a neutral entity.
 */
public class NeutralMentalityComponent extends AbstractMentalityComponent {

    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_DAMAGE_AND_HURT_ME = new HashSet<>(
                                                        Arrays.asList(EnemyMentalityComponent.class, 
                                                                        PlayerMentalityComponent.class, 
                                                                        NeutralMentalityComponent.class,
                                                                        PsychoMentalityComponent.class));
    /**
     * 
     * @param entity the {@link Entity}
     */
    public NeutralMentalityComponent(final Entity entity) {
        super(entity, CANNOT_DAMAGE_AND_HURT_ME, CANNOT_DAMAGE_AND_HURT_ME);
    }

}
