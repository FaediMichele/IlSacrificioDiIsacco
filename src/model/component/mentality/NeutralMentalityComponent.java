package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.entity.AbstractEntity;
import model.entity.Entity;

/**
 * Mentality for a neutral entity.
 */
public class NeutralMentalityComponent extends AbstractMentalityComponent {

    private static final Set<Class<? extends Entity>> CANNOT_DAMAGE_AND_HURT_ME = new HashSet<>(
                                                        Arrays.asList(AbstractEntity.class));
    /**
     * 
     */
    public NeutralMentalityComponent() {
        super(CANNOT_DAMAGE_AND_HURT_ME, CANNOT_DAMAGE_AND_HURT_ME);
    }

}
