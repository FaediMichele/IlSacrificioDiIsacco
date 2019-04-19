package model.component;

import model.entity.Entity;

/**
 * Collectible Component of the bomb: how the bomb have to act when it's collected.
 */
public class BombCollectibleComponent extends CollectibleComponent {

    BombCollectibleComponent(final Entity entity) {
        super(entity);
        setCollectible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean usable() {
        return true;
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
        ((BodyComponent) super.getEntity().getComponent(BodyComponent.class).get()).setState(true);
        //waiting for response on how the bomb acts
    }

    @Override
    protected void init() {
    }

}
