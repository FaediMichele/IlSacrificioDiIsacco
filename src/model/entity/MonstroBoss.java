package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.MovableCollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 *
 */
public class MonstroBoss extends AbstractEnemyMovable {
    private static final double WIDTH = 50;
    private static final double HEIGHT = 50;
    private static final int WEIGHT = 500;
    private static final double DSPEED = 2;
    private static final double DAMAGE = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.MONSTRO;
    /**
     * 
     * @param <C> 
     * @param entityBody 
     * @param entityCollision 
     * @param entityStatus 
     */ 
    public <C extends MovableCollisionComponent> MonstroBoss(final BodyComponent entityBody, final C entityCollision,
            final StatusComponent entityStatus) {
        super(entityBody, entityCollision, entityStatus);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    public MonstroBoss() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    @Override
    public EntityEnum getNameEntity() {
        // TODO Auto-generated method stub
        return null;
    }

}
