package model.component.collectible;

import java.util.Objects;
import java.util.Optional;

import model.component.Component;
import model.component.InventoryComponent;
import model.entity.Entity;

/**
 * 
 * This class is the abstract component that models the components that are
 * collected and kept in the inventory.
 *
 */
public abstract class AbstractCollectableComponent extends AbstractPickupableComponent {

    private Optional<Entity> entityThatCollectedMe;

    AbstractCollectableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Entity entity) {
        Objects.requireNonNull(entity);
        final Optional<Component> optComponent = entity.getComponents().stream().filter(c -> c.getClass().equals(InventoryComponent.class)).findFirst();
        if (optComponent.isPresent()) {
            final InventoryComponent inventoryComponent = (InventoryComponent) optComponent.get();
            if (inventoryComponent.addThing(getEntity())) {
                this.entityThatCollectedMe = Optional.of(entity);
                this.deleteThisEntity();
            }
        }
    }

    /**
     * 
     * @return {@link Entity} that collect this entity, throws an exception if it's not present.
     */
    public Entity getEntityThatCollectedMe() {
        if (this.entityThatCollectedMe.isPresent()) {
            return this.entityThatCollectedMe.get();
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * 
     * @param entityThatCollectedMe memorizes the entity that collected the object.
     */
    protected void setEntityThatCollectedMe(final Entity entityThatCollectedMe) {
        this.entityThatCollectedMe = Optional.of(entityThatCollectedMe);
    }

    /**
     * @return the InventoryComponent ot the Entity that collected this entity.
     */
    protected InventoryComponent getInventoryComponent() {
        if (this.getEntityThatCollectedMe().getComponent(InventoryComponent.class).isPresent()) {
            return ((InventoryComponent) this.getEntityThatCollectedMe().getComponent(InventoryComponent.class).get());
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Consumes the actions of the subject, for example if you want to trigger a
     * bomb this bomb will consume the bombs that were collected in the inventory.
     */
    public abstract void use();
}
