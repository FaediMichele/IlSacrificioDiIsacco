package model.component;

import com.google.common.eventbus.EventBus;

/**
 * This class manages the collision of this entity with the others;
 *
 */

public class CollisionComponent extends AbstractComponent {

    private EventBus bus = new EventBus();
    /**
     * 
     */
    public CollisionComponent() {
        super();
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

}
