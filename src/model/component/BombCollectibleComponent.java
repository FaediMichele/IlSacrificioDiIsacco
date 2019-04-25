package model.component;

//import model.entity.BombTriggered;
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
        //getEntity().getRoom().insertEntity(new BombTriggered(entityBody, entityCollision, entityExplosion, timeBeforeExplodes, explosionTime));
    }

    @Override
    protected void init() {
    //to perfect
    }

}
