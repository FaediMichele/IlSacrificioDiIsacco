package model.component;

import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * This component manages the entity from who can receive damage.
 */
public class AbstractMentalityComponent extends AbstractComponent<AbstractMentalityComponent> {

    private final Set<Class<? extends AbstractMentalityComponent>> cannotDamage = new HashSet<>();
    private final Set<Class<? extends AbstractMentalityComponent>> cannotHurtMe = new HashSet<>();

    /**
     * Create a new {@link AbstractMentalityComponent} with mentality set.
     * 
     * @param entity    the entity
     * @param cannotDamage set of AbstractMentalityComponent who I can hurt
     * @param cannotHurtMe set of AbstractMentalityComponent who can hurt me
     */
    public AbstractMentalityComponent(final Entity entity, final Set<Class<? extends AbstractMentalityComponent>> cannotDamage,
            final Set<Class<? extends AbstractMentalityComponent>> cannotHurtMe) {
        super(entity);
        this.cannotDamage.clear();
        this.cannotDamage.addAll(cannotDamage);
        this.cannotHurtMe.clear();
        this.cannotHurtMe.addAll(cannotHurtMe);
    }

    /**
     * 
     * @param entities the AbstractMentalityComponent that can no longer hurt me
     */
    public void addEntitiesCannotHurtMe(final Set<Class<? extends AbstractMentalityComponent>> entities) {
        this.cannotHurtMe.addAll(entities);
    }

    /**
     * 
     * @param entities the AbstractMentalityComponent that I can no longer damage
     */
    public void addEntitiesCannotDamage(final Set<Class<? extends AbstractMentalityComponent>> entities) {
        this.cannotDamage.addAll(entities);
    }

    /**
     * 
     * @param entities the AbstractMentalityComponent that can hurt me
     */
    public void removeEntitiesCannotHurtMe(final Set<Class<? extends AbstractMentalityComponent>> entities) {
        this.cannotHurtMe.removeAll(entities);
    }

    /**
     * 
     * @param entities the AbstractMentalityComponent that I can damage
     */
    public void removeEntitiesCannotDamage(final Set<Class<? extends AbstractMentalityComponent>> entities) {
        this.cannotDamage.removeAll(entities);
    }

    /**
     * 
     * @param entity the {@link AbstractMentalityComponent}
     * @return if it can hurt me
     */
    public boolean isHurtableByMe(final Class<? extends AbstractMentalityComponent> entity) {
        return !this.cannotHurtMe.contains(entity);
    }

    /**
     * 
     * @param entity the {@link AbstractMentalityComponent}
     * @return if I can damage
     */
    public boolean isDamageable(final Class<? extends AbstractMentalityComponent> entity) {
        return !this.cannotDamage.contains(entity);
    }
}
