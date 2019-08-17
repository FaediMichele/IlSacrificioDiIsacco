package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.CollisionEvent;
import util.EventListener;

/**
 * Component that change the floor when the entity is touched.
 */
public class WinOnCollisionComponent extends WinComponent {
    /**
     * Initialize the component.
     * @param e the entity.
     * @param typeOfEntity the entity to trigger the win.
     */
    public WinOnCollisionComponent(final Entity e, final Class<? extends Entity> typeOfEntity) {
        super(e);
        this.registerListener(new EventListener<CollisionEvent>() {
            @Subscribe
            @Override
            public void listenEvent(final CollisionEvent event) {
                if (typeOfEntity.isInstance(event.getSourceEntity())) {
                    win();
                }
            }
        });
    }
}
