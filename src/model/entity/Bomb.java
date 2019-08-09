package model.entity;

import model.component.BodyComponent;
import model.component.collectible.BombCollectableComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * 
 * Entity of the bomb positioned and primed.
 *
 */
public class Bomb extends AbstractEntity {
    private static final double WIDTH = 20.0;
    private static final double HEIGHT = 20.0;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.BOMB;


    /**
     * Default empty constructor.
     */
    public Bomb() {
        super();
        this.attachComponent(new BombCollectableComponent(this))
            .attachComponent(new BodyComponent(this, HEIGHT, WIDTH, WEIGHT))
            .attachComponent(new NeutralMentalityComponent(this));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
