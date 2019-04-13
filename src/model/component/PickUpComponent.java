package model.component;


import model.entity.Entity;
/**
 * 
 * This class is the component that determines whether an entity can be collected.
 *
 */
public abstract class PickUpComponent extends AbstractComponent<PickUpComponent> {

    private boolean collectible;

//    private Entity entityThatCollectedMe;


    PickUpComponent(final Entity entity, final boolean collectible) {
        super(entity);
        this.collectible = collectible;
    }


    /**
     * 
     * @return true if the entity can be collected false otherwise
     */
    protected boolean isCollectible() {
        return this.collectible;
    }

/**
 * Hello.
 */
    @Override
    public Entity getEntity() {
        return super.getEntity();
    }

    abstract boolean usable();

    abstract boolean toBeInitialized();

    abstract void use();

    abstract void init();
 
}
