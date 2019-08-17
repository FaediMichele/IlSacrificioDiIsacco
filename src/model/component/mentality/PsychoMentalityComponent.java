package model.component.mentality;

import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * Mentality for a psycho entity.
 */
public class PsychoMentalityComponent extends AbstractMentalityComponent {

    private static final Set<Class<? extends Entity>> CANNOT_HURT_ME = new HashSet<>();

    private static final Set<Class<? extends Entity>> CANNOT_DAMAGE = new HashSet<>();
    /**
     * 
     */
    public PsychoMentalityComponent() {
        super(CANNOT_DAMAGE, CANNOT_HURT_ME);
    }
}
