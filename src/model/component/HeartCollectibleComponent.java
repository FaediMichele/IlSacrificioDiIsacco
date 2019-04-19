package model.component;

import model.entity.Entity;

/**
 * Collectible Component of the heart entity: how the heart have to act when it's collected.
 */
public class HeartCollectibleComponent extends CollectibleComponent {

    HeartCollectibleComponent(final Entity entity) {
        super(entity);
        setCollectible(false);
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
        return true;
    }

    @Override
    protected void use() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        //HealthComponent h = ((HealthComponent) super.getEntityThatCollectedMe().get().getComponent(HealthComponent.class).get());
        //h.addHeart(...); waiting for heart entity to be created
    }

}
