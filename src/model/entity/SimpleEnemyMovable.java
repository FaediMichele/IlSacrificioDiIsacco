package model.entity;

import model.component.BodyComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * 
 * Simple enemy entity used for testing.
 *
 */
public class SimpleEnemyMovable extends AbstractEnemyMovable {
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.SIMPLE_ENEMY_MOVABLE;

    /**
     * Basic constructor.
     */
    public SimpleEnemyMovable() {
        super();
        this.attachComponent(new BodyComponent(this));
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
