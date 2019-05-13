package model.component;

import model.entity.Entity;
import model.entity.Door;

/**
 * Collectible Component of the key entity: how the key have to act when it's
 * collected. It the key case, it just has to be "present" so the main point of
 * the code is setting the collectible boolean to true.
 */
public class KeyCollectibleComponent extends AbstractCollectibleComponent {

    private final Door door;

    /**
     * 
     * @param entity source Entity
     * @param doors  door this key allows the player to go through
     */
    KeyCollectibleComponent(final Entity entity, final Door door) {
        super(entity);
        this.door = door;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init(final Entity entity) {
        if (entity.hasComponent(KeychainComponent.class)) {
            if (((KeychainComponent) entity.getComponent(KeychainComponent.class).get()).addKey(this.door)) {
                this.deleteThisEntity();
            }
        } else {
            final KeychainComponent keychainComponent = new KeychainComponent(entity);
            keychainComponent.addKey(this.door);
            entity.attachComponent(keychainComponent);
            this.deleteThisEntity();
        }
    }
}
