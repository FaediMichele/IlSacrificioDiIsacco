package model.component.mentality;

import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * Mentality for a psycho entity.
 */
public class PsychoMentalityComponent extends AbstractMentalityComponent {

    /**
     * 
     * @param entity the {@link Entity}
     */
    public PsychoMentalityComponent(final Entity entity) {
        super(entity, generateCannotDamageSet(), generateCannotHurtMeSet());
    }

    private static Set<Class<? extends AbstractMentalityComponent>> generateCannotDamageSet() {
        final Set<Class<? extends AbstractMentalityComponent>> cannotDamage = new HashSet<>();
        return cannotDamage;
    }

    private static Set<Class<? extends AbstractMentalityComponent>> generateCannotHurtMeSet() {
        final Set<Class<? extends AbstractMentalityComponent>> cannotHurtMe = new HashSet<>();
        cannotHurtMe.add(NeutralMentalityComponent.class);
        cannotHurtMe.add(EnemyMentalityComponent.class);
        return cannotHurtMe;
    }

}
