package model.component;

import java.util.Optional;
import model.entity.Entity;


/**
 * 
 * This class is the component that determines whether an entity can be
 * collected.
 *
 */
public abstract class CollectibleComponent extends AbstractComponent<CollectibleComponent> {

    private boolean collectible;
    private Optional<Entity> entityThatCollectedMe;

    CollectibleComponent(final Entity entity, final boolean collectible) {
        super(entity);
        this.collectible = collectible;
        this.entityThatCollectedMe = Optional.empty();
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
     * @return {@link Optional} of {@link Entity} that collect this entity.
     */
    protected Optional<Entity> getEntityThatCollectedMe() {
        return entityThatCollectedMe;
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
     * 
     * @param entityThatCollectedMe memorizes the entity that collected the object.
     */
    protected void setEntityThatCollectedMe(final Optional<Entity> entityThatCollectedMe) {
        // non sarebbe meglio usare final Entity entityThatCollectedMe e poi aggiungerla come Optional.of(entityThatCollectedMe)?
        this.entityThatCollectedMe = entityThatCollectedMe;
    }

    /**
     * @return the entity that collected this entity
     */
    protected Optional<Entity> getEntityCollectedMe() {
        return this.entityThatCollectedMe;
    }

    /**
     * 
     * @return true if it is an object that can be used false otherwise
     */
    protected abstract boolean usable();

    /**
     * 
     * @return true if a newly picked object must be initialized false otherwise
     */
    protected abstract boolean needInitialized();

    /**
     * Consumes the actions of the subject, for example if you want to trigger a
     * bomb this bomb will consume the bombs that were collected in the inventory.
     */
    protected abstract void use();

    /**
     * If the object must be initialized once the object has been collected from the
     * ground, this function must be called.
     */
    protected abstract void init();

}
