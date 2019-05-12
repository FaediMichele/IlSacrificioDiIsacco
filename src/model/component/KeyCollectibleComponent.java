package model.component;

import model.entity.Entity;
//import java.util.List;
//import model.entity.Door;

/**
 * Collectible Component of the key entity: how the key have to act when it's collected.
 * It the key case, it just has to be "present" so the main point of the code is setting the collectible boolean to true.
 */
public class KeyCollectibleComponent extends AbstractCollectibleCollectableComponent {

    //private final List<Door> doors;

    /**
     * 
     * @param entity source Entity
     * @param doors  list of doors this key allows the player to go through
     */
    KeyCollectibleComponent(final Entity entity) { /*final List<Door> doors*/
        super(entity);
        //this.doors = doors;
        setCollectible(true);
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
    protected void init(final Entity entity) {
        // TODO Auto-generated method stub
    }

}
