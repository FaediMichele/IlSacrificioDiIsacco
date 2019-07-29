package model.component;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.entity.Player;
import model.events.CollisionEvent;
import model.game.Room;
import util.EventListener;
import java.util.List;
import java.util.Optional;


/**
 * AI for the Gaper monster.
 */
public class FlyAIComponent extends AbstractAIComponent {

    private double angle;
    /**
     * Each time the Gaper collides with something, re-calculates the angle to get to isaac.
     * @param entity for this component
     */
    public FlyAIComponent(final Entity entity) {
        super(entity);
        calculateAngle();

        this.registerListener(new EventListener<CollisionEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final CollisionEvent event) {
                calculateAngle();
            }
        });
    }

    /**
     * returns the angle to get to Isaac.
     * @return 
     */
    private void calculateAngle() {
        final Room r = this.getEntity().getRoom();
        if (r == null) {
            return;
        }
        final List<? extends Entity> entitys = r.getEntities();
        if (entitys == null) {
            return;
        }
        final Optional<? extends Entity> isaac = entitys.stream().filter(i -> i.getClass().equals(Player.class)).findAny();
        if (!isaac.isPresent()) {
            return;
        }
        final BodyComponent isaacBody = isaac.get().getComponent(BodyComponent.class).get();
        if (isaacBody == null) {
            return;
        }
        final BodyComponent myBody = this.getEntity().getComponent(BodyComponent.class).get();
        final Double diffX = isaacBody.getPosition().getZ() - myBody.getPosition().getZ();
        final Double diffY = isaacBody.getPosition().getY() - myBody.getPosition().getY();
        this.angle = Math.atan2(diffX, diffY);
    }

    /**
     * Update of the MoveComponent done by this AI.
     */
    @Override
    protected void moveUpdate() {
        super.getMoveComponent(getEntity()).move(Math.cos(this.angle), Math.sin(this.angle), 0);
    }
}
