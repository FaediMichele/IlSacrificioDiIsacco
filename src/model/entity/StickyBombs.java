package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.EntityEnum;

public class StickyBombs extends AbstractStaticEntity {

    public StickyBombs(BodyComponent entityBody, CollisionComponent entityCollision, StatusComponent entityStatus) {
        super(entityBody, entityCollision, entityStatus);
        // TODO Auto-generated constructor stub
    }

    public StickyBombs() {
        super();
    }

    @Override
    public EntityEnum getNameEntity() {
        // TODO Auto-generated method stub
        return null;
    }

}
