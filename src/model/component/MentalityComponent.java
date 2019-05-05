package model.component;

import model.entity.Entity;

/**
 * Component that contains the informations about the mentality of the entity.
 */
public class MentalityComponent extends AbstractComponent<MentalityComponent> {

    private Mentality actualMentality;

    /**
     * Create a new {@link MentalityComponent} with mentality set.
     * 
     * @param entity the entity
     * @param mentality the default mentality
     */
    public MentalityComponent(final Entity entity, final Mentality mentality) {
        super(entity);
        this.actualMentality = mentality;
    }

    /**
     * Create a new {@link Mentality Component} replacing the old mentality.
     * 
     * @param entity the entity
     * @param mentalityToReplace is the mentality to be replaced
     * @param mentality the default mentality
     */
    public MentalityComponent(final Entity entity, final MentalityComponent mentalityToReplace, final Mentality mentality) {
        super(entity, mentalityToReplace);
        this.actualMentality = mentality;
    }

    /**
     * 
     * @param mentality new mentality of the entity;
     */
    protected void setMentality(final Mentality mentality) {
        this.actualMentality = mentality;
    }

    /**
     * 
     * @return actualMentality of the entity.
     */
    protected Mentality getMentality() {
        return this.actualMentality;
    }
}
