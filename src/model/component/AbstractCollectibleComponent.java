package model.component;

import model.entity.Entity;

/**
 * 
 * This class is the component that determines whether an entity can be
 * collected.
 *
 */
public abstract class AbstractCollectibleComponent extends AbstractComponent<AbstractCollectibleComponent> {

    private boolean collectible;

    AbstractCollectibleComponent(final Entity entity) {
        super(entity);
        this.collectible = false;
    }

    /**
     * 
     * @return true if the entity can be collected false otherwise
     */
    protected boolean isCollectible() {
        return this.collectible;
    }



    /**
     * 
     * @param collectible it is true if the object has become otherwise false
     *                    collectible.
     */
    protected void setCollectible(final boolean collectible) {
        this.collectible = collectible;
    }


    /**
     * This method removes the entity from the room, it may be useful to call this
     * function when an object has been successfully collected.
     */
    protected void deleteThisEntity() {
        getEntity().getRoom().deleteEntity(this.getEntity());
    }


    /**
     * 
     * @return true if a newly picked object must be initialized false otherwise
     */
//    protected abstract boolean needInitialized();



    /**
     * If the object must be initialized once the object has been collected from the
     * ground, this function must be called.
     */
//    protected abstract void init();

    /**
     * It is the function that is called when the entity tries to be collected.
     * 
     * @param entity is the entity that tries to collect the object.
     */
    protected abstract void init(Entity entity);

}
