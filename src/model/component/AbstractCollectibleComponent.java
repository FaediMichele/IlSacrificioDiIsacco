package model.component;

import model.entity.Entity;

/**
 * 
 * This class is the component that determines whether an entity can be
 * collected.
 *
 */
public abstract class AbstractCollectibleComponent extends AbstractComponent<AbstractCollectibleComponent> {

    AbstractCollectibleComponent(final Entity entity) {
        super(entity);
    }

    /**
     * This method removes the entity from the room, it may be useful to call this
     * function when an object has been successfully collected.
     */
    protected void deleteThisEntity() {
        getEntity().getRoom().deleteEntity(this.getEntity());
    }


    /**
     * It is the function that is called when the entity tries to be collected.
     * 
     * @param entity is the entity that tries to collect the object.
     */
    protected abstract void init(Entity entity);

}
