package model.component.collectible;

import model.entity.Entity;

/***
 *  .
 *
 */
public class TearCollectibleComponent extends AbstractCollectableComponent {

    /**
     * 
     * @param entity .
     */
    public TearCollectibleComponent(final Entity entity) {
        super(entity);
        super.setEntityThatCollectedMe(entity);
    }

    @Override
    public void use() {
    }

}
