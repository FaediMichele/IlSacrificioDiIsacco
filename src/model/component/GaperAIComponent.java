package model.component;


import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Player;
import model.events.CollisionEvent;
import util.EventListener;


/**
 * AI for the Gaper monster.
 */
public class GaperAIComponent extends AbstractAIComponent {

    private double angle;
    /**
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
        final BodyComponent isaacBody = (BodyComponent) this.getEntity().getRoom().getEntity().stream()
                .filter(i -> i.getClass().equals(Player.class)).findAny().get().getComponent(BodyComponent.class).get();
        final BodyComponent myBody = (BodyComponent) this.getEntity().getComponent(BodyComponent.class).get();
        final Double diffX = isaacBody.getPosition().getV1() - myBody.getPosition().getV1();
        final Double diffY = isaacBody.getPosition().getV2() - myBody.getPosition().getV2();
        angle = Math.atan2(diffX, diffY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void moveUpdate() {
        super.getMoveComponent(getEntity()).move(Math.cos(this.angle), Math.sin(this.angle), 0);
    }
}
