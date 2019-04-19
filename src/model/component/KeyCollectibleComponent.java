package model.component;

import model.entity.Entity;

/**
 * 
 *
 */
public class KeyCollectibleComponent extends CollectibleComponent{

    KeyCollectibleComponent(Entity entity) {
        super(entity);
        setCollectible(true);
    }

    @Override
    protected boolean usable() {
        return false;
    }

    @Override
    protected boolean needInitialized() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void use() {
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        
    }

}
