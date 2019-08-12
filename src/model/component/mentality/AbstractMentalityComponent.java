package model.component.mentality;

import java.util.HashSet;
import java.util.Set;
import model.component.AbstractComponent;
import model.entity.Entity;

/**
 * This component manages the entity from who can receive damage.
 */
public class AbstractMentalityComponent extends AbstractComponent<AbstractMentalityComponent> {

    private final Set<Class<? extends AbstractMentalityComponent>> cannotDamage = new HashSet<>();
    private final Set<Class<? extends AbstractMentalityComponent>> cannotHurtMe = new HashSet<>();
    private final Set<Class<? extends AbstractMentalityComponent>> cannotCollide = new HashSet<>();

    /**
     * Create a new {@link AbstractMentalityComponent} with mentality set.
     * 
     * @param entity       the entity
     * @param cannotDamage set of AbstractMentalityComponent who I can hurt
     * @param cannotHurtMe set of AbstractMentalityComponent who can hurt me
     * @param cannotCollide set of AbstractMentalityComponent who can pass though me.
     */
    public AbstractMentalityComponent(final Entity entity,
            final Set<Class<? extends AbstractMentalityComponent>> cannotDamage,
            final Set<Class<? extends AbstractMentalityComponent>> cannotHurtMe,
            final Set<Class<? extends AbstractMentalityComponent>> cannotCollide) {
        super(entity);
        this.cannotDamage.clear();
        this.cannotDamage.addAll(cannotDamage);
        this.cannotHurtMe.clear();
        this.cannotHurtMe.addAll(cannotHurtMe);
        this.cannotCollide.clear();
        this.cannotCollide.addAll(cannotCollide);
    }

    /**
     * 
     * @param entityMentalities the AbstractMentalityComponent that can no longer
     *                          hurt me
     */
    public void addEntitiesCannotHurtMe(final Set<Class<? extends AbstractMentalityComponent>> entityMentalities) {
        this.cannotHurtMe.addAll(entityMentalities);
    }

    /**
     * 
     * @param entityMentalities the AbstractMentalityComponent that I can no longer
     *                          damage
     */
    public void addEntitiesCannotDamage(final Set<Class<? extends AbstractMentalityComponent>> entityMentalities) {
        this.cannotDamage.addAll(entityMentalities);
    }

    /**
     * 
     * @param entityMentalities the AbstractMentalityComponent that can hurt me
     */
    public void removeEntitiesCannotHurtMe(final Set<Class<? extends AbstractMentalityComponent>> entityMentalities) {
        this.cannotHurtMe.removeAll(entityMentalities);
    }

    /**
     * 
     * @param entityMentalities the AbstractMentalityComponent that I can damage
     */
    public void removeEntitiesCannotDamage(final Set<Class<? extends AbstractMentalityComponent>> entityMentalities) {
        this.cannotDamage.removeAll(entityMentalities);
    }

    /**
     * 
     * @param entityMentalities the AbstractMentalityComponent that can hurt me
     */
    public void removeEntitiesCannotCollide(final Set<Class<? extends AbstractMentalityComponent>> entityMentalities) {
        this.cannotCollide.removeAll(entityMentalities);
    }

    /**
     * 
     * @param entityMentalities the AbstractMentalityComponent that I can no longer
     *                          damage
     */
    public void addEntitiesCannotCollide(final Set<Class<? extends AbstractMentalityComponent>> entityMentalities) {
        this.cannotCollide.addAll(entityMentalities);
    }

    /**
     * 
     * @param entityMentality the {@link AbstractMentalityComponent}
     * @return if it can hurt me
     */
    public boolean canHurtMe(final Class<? extends AbstractMentalityComponent> entityMentality) {
        return !this.cannotHurtMe.contains(entityMentality)
                && !this.cannotHurtMe.stream().anyMatch(chm -> entityMentality.isInstance(chm));
    }

    /**
     * 
     * @param entityMentality the {@link AbstractMentalityComponent}
     * @return if I can damage
     */
    public boolean isDamageableByMe(final Class<? extends AbstractMentalityComponent> entityMentality) {
        return !this.cannotDamage.contains(entityMentality)
                && !this.cannotDamage.stream().anyMatch(chm -> entityMentality.isInstance(chm));
    }

    /**
     * 
     * @param entityMentality the {@link AbstractMentalityComponent}
     * @return if I can collide with.
     */
    public boolean canCollide(final Class<? extends AbstractMentalityComponent> entityMentality) {
        return entityMentality != null && !this.cannotCollide.contains(entityMentality);
    }
}
