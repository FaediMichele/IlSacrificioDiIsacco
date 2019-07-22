package model.component.collectible;

import model.component.InventoryComponent;
import model.entity.Entity;

/**
 * Collectible Component of the key entity: how the key have to act when it's
 * collected. It the key case, it just has to be "present" so the main point of
 * the code is setting the collectible boolean to true.
 */
public class KeyCollectableComponent extends AbstractCollectableComponent {

    /**
     * 
     * @param entity source Entity
     */
    public KeyCollectableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void use() {
        ((InventoryComponent) this.getEntityThatCollectedMe().get()
                .getComponent((InventoryComponent.class)).get()).consumeThing(this.getEntity());
    }
}
