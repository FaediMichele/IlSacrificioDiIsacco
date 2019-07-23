package model.component;


import java.util.Set;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Player;
import model.events.CollisionEvent;
import model.game.Room;
import util.EventListener;


/**
 * AI for the Gaper monster.
 */
public class GaperAIComponent extends AbstractAIComponent {

    private double angle;
    /**
     * Each time the Gaper collides with somethig, re-calculates the angle to get to isaac.
     * @param entity for this component
     */
    public GaperAIComponent(final Entity entity) {
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
        final Set<? extends Entity> entitys = r.getEntity();
        if (entitys == null) {
            return;
        }
        final BodyComponent isaacBody = (BodyComponent) entitys.stream()
                .filter(i -> i.getClass().equals(Player.class)).findAny().get().getComponent(BodyComponent.class).get();
        if (isaacBody == null) {
            return;
        }
        final BodyComponent myBody = (BodyComponent) this.getEntity().getComponent(BodyComponent.class).get();
        final Double diffX = isaacBody.getPosition().getV1() - myBody.getPosition().getV1();
        final Double diffY = isaacBody.getPosition().getV2() - myBody.getPosition().getV2();
        this.angle = Math.atan2(diffX, diffY);
    }

    /**
     * Update of the MoveComponent done by this AI.
     */
    protected void moveUpdate() {
        super.getMoveComponent(getEntity()).move(Math.cos(this.angle), Math.sin(this.angle), 0);
    }
}
