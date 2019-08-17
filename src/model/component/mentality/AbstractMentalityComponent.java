package model.component.mentality;

import java.util.HashSet;
import java.util.Set;
import model.component.AbstractComponent;
import model.entity.Entity;
import util.StaticMethodsUtils;

/**
 * This component manages the entity from who can receive damage.
 */
public class AbstractMentalityComponent extends AbstractComponent {

    private final Set<Class<? extends Entity>> cannotDamage = new HashSet<>();
    private final Set<Class<? extends Entity>> cannotHurtMe = new HashSet<>();

    /**
     * Create a new {@link AbstractMentalityComponent} with mentality set.
     * 
     * @param cannotDamage set of Entity who I can hurt
     * @param cannotHurtMe set of Entity who can hurt me
     */
    public AbstractMentalityComponent(
            final Set<Class<? extends Entity>> cannotDamage,
            final Set<Class<? extends Entity>> cannotHurtMe) {
        super();
        this.cannotDamage.clear();
        this.cannotDamage.addAll(cannotDamage);
        this.cannotHurtMe.clear();
        this.cannotHurtMe.addAll(cannotHurtMe);
    }

    /**
     * 
     * @param entities the Entity that can no longer
     *                          hurt me
     */
    public void addEntitiesCannotHurtMe(final Set<Class<? extends Entity>> entities) {
        this.cannotHurtMe.addAll(entities);
    }

    /**
     * 
     * @param entities the Entity that I can no longer
     *                          damage
     */
    public void addEntitiesCannotDamage(final Set<Class<? extends Entity>> entities) {
        this.cannotDamage.addAll(entities);
    }

    /**
     * 
     * @param entities the Entity that can hurt me
     */
    public void removeEntitiesCannotHurtMe(final Set<Class<? extends Entity>> entities) {
        this.cannotHurtMe.removeAll(entities);
    }

    /**
     * 
     * @param entities the Entity that I can damage
     */
    public void removeEntitiesCannotDamage(final Set<Class<? extends Entity>> entities) {
        this.cannotDamage.removeAll(entities);
    }

    /**
     * 
     * @param entity the {@link Entity}
     * @return if it can hurt me
     */
    public boolean canHurtMe(final Class<? extends Entity> entity) {
        return !this.cannotHurtMe.contains(entity)
                && !this.cannotHurtMe.stream().anyMatch(chm -> StaticMethodsUtils.isTypeOf(entity, chm));
    }

    /**
     * 
     * @param entity the {@link Entity}
     * @return if I can damage
     */
    public boolean isDamageableByMe(final Class<? extends Entity> entity) {
        return !this.cannotDamage.contains(entity)
                && !this.cannotDamage.stream().anyMatch(chm -> StaticMethodsUtils.isTypeOf(entity, chm));
    }
}
