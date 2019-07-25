package model.component.collectible;

import model.component.AbstractComponent;
import model.entity.Entity;
import util.enumeration.BasicKeyMapStatusEnum;
import util.enumeration.BasicStatusEnum;

/**
 * 
 * This class is the component that determines whether an entity can be
 * collected.
 *
 */
public abstract class AbstractPickupableComponent extends AbstractComponent<AbstractPickupableComponent> {

    AbstractPickupableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * This method removes the entity from the room, it may be useful to call this
     * function when an object has been successfully collected.
     */
    protected void deleteThisEntity() {
        this.getEntity().getRoom().deleteEntity(this.getEntity());
        this.getEntity().getStatusComponent().setStatus(BasicKeyMapStatusEnum.STATUS, BasicStatusEnum.DEAD);
    }

    /**
     * It is the function that is called when the entity tries to be collected.
     * 
     * @param entity is the entity that tries to collect the object.
     */
    public abstract void init(Entity entity);

}
