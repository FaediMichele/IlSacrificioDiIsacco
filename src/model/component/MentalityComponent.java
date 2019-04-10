package model.component;

import model.entity.Entity;

/**
 * Component that contains the informations about the mentality of the entity.
 */
public class MentalityComponent extends AbstractComponent {

    private Mentality actualMentality;

    /**
     * Create a new {@link MentalityComponent} with mentality set.
     * @param entity the entity
     * @param m the default mentality
     */
    public MentalityComponent(final Entity entity, final Mentality m) {
        super(entity);
        this.actualMentality = m;
    }
    /**
     * 
     * @param m new mentality of the entity;
     */
    protected void setMentality(final Mentality m) {
        this.actualMentality = m;
    }
    /**
     * 
     * @return actualMentality of the entity.
     */
    protected Mentality getMentality() {
        return this.actualMentality;
    }
}
