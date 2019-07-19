package model.component.collectible;

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
     * @return {@link Optional} of {@link Entity} that collect this entity.
     */
    protected Optional<Entity> getEntityThatCollectedMe() {
        return this.entityThatCollectedMe;
    }

    /**
     * 
     * @param entityThatCollectedMe memorizes the entity that collected the object.
     */
    protected void setEntityThatCollectedMe(final Entity entityThatCollectedMe) {
        this.entityThatCollectedMe = Optional.of(entityThatCollectedMe);
    }

    /**
     * Consumes the actions of the subject, for example if you want to trigger a
     * bomb this bomb will consume the bombs that were collected in the inventory.
     */
    public abstract void use();
}
