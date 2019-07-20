package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.CollisionEvent;
import util.EventListener;

/**
 * This component manages all the movements and actions that the tear must do
 * independently once generated.
 */
public class TearAIComponent extends AbstractAIComponent {

    private int angle;

    /**
     * the listener of this component handles the disappearance of the entity when
     * it collides with something.
     * 
     * @param entity this entity
     */
    public TearAIComponent(final Entity entity) {
        this(entity, 0);
    }

    /**
     * @param entity this entity
     * @param angle  direction angle of the tear
     */
    public TearAIComponent(final Entity entity, final int angle) {
        super(entity);
        this.angle = angle;

        this.registerListener(new EventListener<CollisionEvent>() {
            @Subscribe
            @Override
            public void listenEvent(final CollisionEvent event) {
                getEntity().getRoom().deleteEntity(getEntity());
            }
        });
    }

    /**
     * 
     * @return direction
     */
    public int getAngle() {
        return this.angle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void moveUpdate() {
        this.getMoveComponent(this.getEntity()).move(Math.cos(Math.toRadians(this.angle)),
                Math.sin(Math.toRadians(this.angle)), 0);
    }
}
