package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.CollisionEvent;
import util.EventListener;
import util.enumeration.BasicKeyMapStatusEnum;
import util.enumeration.BasicStatusEnum;

/**
 * This component manages all the movements and actions that the tear must do
 * independently once generated.
 */
public class TearAIComponent extends AbstractAIComponent {

    private static final double DEFAULT_TEAR_LIFETIME = 2000000;
    private final double lifetime;
    private final int angle;
    private double time = 0.0;

    /**
     * @param entity this entity
     */
    public TearAIComponent(final Entity entity) {
        this(entity, 0, DEFAULT_TEAR_LIFETIME);
    }

    /**
     * @param entity this entity
     * @param angle direction angle of the tear
     */
    public TearAIComponent(final Entity entity, final int angle) {
        this(entity, angle, DEFAULT_TEAR_LIFETIME);
    }

    /**
     * @param entity this entity
     * @param lifetime time before the tear disappears
     */
    public TearAIComponent(final Entity entity, final double lifetime) {
        this(entity, 0, lifetime);
    }

    /**
     * the listener of this component handles the disappearance of the entity when
     * it collides with something.
     * @param entity this entity
     * @param angle direction angle of the tear
     * @param lifetime time before the tear disappears
     */
    public TearAIComponent(final Entity entity, final int angle, final double lifetime) {
        super(entity);
        this.angle = angle;
        this.lifetime = lifetime;

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
     * Update of the MoveComponent done by this AI.
     */
    protected void moveUpdate() {
        this.getMoveComponent(this.getEntity()).move(Math.cos(Math.toRadians(this.angle)),
                Math.sin(Math.toRadians(this.angle)), 0);
    }

    /**
     * {@inheritDoc}
     * Checks if the lifetime of the tear is expired. If so, makes it disappear.
     */
    @Override
    public void update(final Double deltaTime) {
        time += deltaTime;
        if (time > lifetime) {
            getEntity().getRoom().deleteEntity(this.getEntity());
            getEntity().getStatusComponent().setStatus(BasicKeyMapStatusEnum.STATUS, BasicStatusEnum.DEAD);
        } else {
            super.update(deltaTime);
        }
    }
}
