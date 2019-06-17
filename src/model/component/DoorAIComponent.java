package model.component;



import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Key;
import model.events.CollisionEvent;
import model.events.UseThingEvent;
import model.events.DoorChangeEvent;
import model.events.EventListener;

/**
 * This component is used by the doors.
 *
 */
public class DoorAIComponent extends AbstractComponent<DoorAIComponent> {

    private final Integer destination;
    private final Integer location;
    private boolean hasPlayerPassed;

    /**
     * Create a door component with a destination room index.
     * 
     * @param location The {@link Room} where the player is
     * @param destinationIndex index of the room
     * @param entity Entity that possess the component
     */
    public DoorAIComponent(final Entity entity, final Integer location, final Integer destinationIndex) {
        super(entity);
        this.hasPlayerPassed = false;
        this.location = location;
        this.destination = destinationIndex;
        entity.registerListener(new EventListener<CollisionEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final CollisionEvent event) {
                final CollisionEvent coll = (CollisionEvent) event;
                if (coll.getSourceEntity().hasComponent(HealthComponent.class)) {
                    final LockComponent lc = (LockComponent) getEntity().getComponent(LockComponent.class).get(); 
                    final InventoryComponent ic = (InventoryComponent) coll.getSourceEntity().getComponent(InventoryComponent.class).get();
                    if (lc != null && lc.isLocked() && ic != null) {
                        if (ic.thingsOfThisKind(Key.class) > 0) {
                            coll.getSourceEntity().postEvent(new UseThingEvent(coll.getSourceEntity(), Key.class));
                            lc.unlock();
                        } else {
                            return;
                        }
                    } else if (lc != null) {
                        return;
                    }
                    coll.getSourceEntity().getRoom().getFloor()
                    .changeEntityRoom(coll.getSourceEntity(), location, destination);
                    postPlayerPassed();
                }
            }
        });
    }

    /**
     * Verify if the player touched the door and is ready to change the
     * {@link Room}.
     * 
     * @return if the door is triggered
     */
    public boolean playerPassed() {
        return this.hasPlayerPassed;
    }

    /**
     * Get the {@link Room} that this door conducts.
     * 
     * @return the {@link Room} index
     */
    public Integer getDestination() {
        return this.destination;
    }

    /**
     * Post the event for the player that has passed.
     */
    private void postPlayerPassed() {
        this.getEntity().postEvent(new DoorChangeEvent(getEntity()));
        this.hasPlayerPassed = false;
    }

    /**
     * Get the room where the door is.
     * 
     * @return the location
     */
    protected Integer getLocation() {
        return this.location;
    }

    @Override
    public final String toString() {
        return this.destination + " " + this.location + " " + this.hasPlayerPassed;
    }
}
