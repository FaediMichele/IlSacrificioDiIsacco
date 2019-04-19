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

    @Override
    protected boolean usable() {
        return true;
    }

    @Override
    protected boolean needInitialized() {
        return false;
    }

    @Override
    protected void use() {
        ((BodyComponent) super.getEntity().getComponent(BodyComponent.class).get()).setState(true);
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        
    }

}
