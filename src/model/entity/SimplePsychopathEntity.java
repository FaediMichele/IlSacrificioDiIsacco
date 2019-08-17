package model.entity;

import model.component.DamageComponent;
import model.component.mentality.PsychoMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * 
 *Entity test for psychopath mentality.
 *
 */
public class SimplePsychopathEntity extends AbstractEntity {

    /**
     * Simple constructor. 
     */
    public SimplePsychopathEntity() {
        this.attachComponent(new PsychoMentalityComponent())
            .attachComponent(new DamageComponent(this, 1));
    }
    /**
     * Example.
     */
    @Override
    public EntityEnum getNameEntity() {
        return BasicEntityEnum.BOMB;
    }

}
