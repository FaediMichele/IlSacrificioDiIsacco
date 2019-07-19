package model.component.mentality;

import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * Mentality for a neutral entity.
 */
public class NeutralMentalityComponent extends AbstractMentalityComponent {

    /**
     * 
     * @param entity the {@link Entity}
     */
    public NeutralMentalityComponent(final Entity entity) {
        super(entity, generateCannotDamageSet(), generateCannotHurtMeSet());
    }

    private static Set<Class<? extends AbstractMentalityComponent>> generateCannotDamageSet() {
        return new HashSet<>();
    }

    private static Set<Class<? extends AbstractMentalityComponent>> generateCannotHurtMeSet() {
        return new HashSet<>();
    }

}
