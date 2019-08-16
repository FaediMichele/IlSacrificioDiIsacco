package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.EntityEnum;

public class HostHat extends AbstractStaticEntity {

    public HostHat(BodyComponent entityBody, CollisionComponent entityCollision, StatusComponent entityStatus) {
        super(entityBody, entityCollision, entityStatus);
        // TODO Auto-generated constructor stub
    }

    public HostHat() {
        super();
    }

    @Override
    public EntityEnum getNameEntity() {
        // TODO Auto-generated method stub
        return null;
    }

}
