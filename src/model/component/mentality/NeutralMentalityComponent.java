package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
     */
    public NeutralMentalityComponent() {
        super(CANNOT_DAMAGE_AND_HURT_ME, CANNOT_DAMAGE_AND_HURT_ME);
    }

}
