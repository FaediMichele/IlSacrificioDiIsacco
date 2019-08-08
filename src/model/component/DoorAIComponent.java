package model.component;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.entity.Key;
import model.entity.Player;
import model.events.CollisionEvent;
import model.events.UseThingEvent;
import util.EventListener;
import model.events.DoorChangeEvent;

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
     * @param locationIndex The {@link Room} where the player is
     * @param destinationIndex index of the room
     * @param entity Entity that possess the component
     */
    public DoorAIComponent(final Entity entity, final Integer locationIndex, final Integer destinationIndex) {
        super(entity);
        this.hasPlayerPassed = false;
        this.location = locationIndex;
        this.destination = destinationIndex;
        entity.registerListener(new EventListener<CollisionEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final CollisionEvent event) {
                final CollisionEvent coll = (CollisionEvent) event;
                if (coll.getSourceEntity().getClass().equals(Player.class)) {
                    System.out.println("COLISIONE PORTA");
                    final LockComponent lc = getEntity().getComponent(LockComponent.class).get(); 
                    final InventoryComponent ic = coll.getSourceEntity().getComponent(InventoryComponent.class).get();
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
