package model.component;

import model.entity.Entity;

/**
 * Collectible Component of the key entity: how the key have to act when it's collected.
 * It the key case, it just has to be "present" so the main point of the code is setting the collectible boolean to true.
 */
public class KeyCollectibleComponent extends CollectibleComponent {

    KeyCollectibleComponent(final Entity entity) {
        super(entity);
        setCollectible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean usable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean needInitialized() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void use() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
    }

}
