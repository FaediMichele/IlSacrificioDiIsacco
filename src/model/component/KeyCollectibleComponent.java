package model.component;

import model.entity.Entity;


import model.entity.Door;

/**
 * Collectible Component of the key entity: how the key have to act when it's
 * collected. It the key case, it just has to be "present" so the main point of
 * the code is setting the collectible boolean to true.
 */
public class KeyCollectibleComponent extends AbstractCollectableComponent {

    /**
     * 
     * @param entity source Entity
     * @param doors  door this key allows the player to go through
     */
    KeyCollectibleComponent(final Entity entity, final Door door) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void use() {
        ((InventoryComponent) this.getEntityThatCollectedMe().get()
                .getComponent((InventoryComponent.class)).get()).consumeThing(this.getEntity());
    }
}
