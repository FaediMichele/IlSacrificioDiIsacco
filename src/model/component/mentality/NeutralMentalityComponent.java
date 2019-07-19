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
        final Set<Class<? extends AbstractMentalityComponent>> cannotDamage = new HashSet<>();
        return cannotDamage;
    }

    private static Set<Class<? extends AbstractMentalityComponent>> generateCannotHurtMeSet() {
        final Set<Class<? extends AbstractMentalityComponent>> cannotHurtMe = new HashSet<>();
        return cannotHurtMe;
    }

}
