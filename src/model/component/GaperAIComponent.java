package model.component;


import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Player;
import model.events.CollisionEvent;
import util.EventListener;


/**
 * AI for the Gaper monster.
 */
public class GaperAIComponent extends AbstractComponent<GaperAIComponent> {

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
     * returns the angle to move to get to Isaac.
     * @return 
     */
    private void calculateAngle() {
        BodyComponent isaacBody = (BodyComponent) this.getEntity().getRoom().getEntity().stream()
                .filter(i -> i.getClass().equals(Player.class)).findAny().get().getComponent(BodyComponent.class).get();
        BodyComponent myBody = (BodyComponent) this.getEntity().getComponent(BodyComponent.class).get();
        Double diffX = isaacBody.getPosition().getV1() - myBody.getPosition().getV1();
        Double diffY = isaacBody.getPosition().getV2() - myBody.getPosition().getV2();
        angle = Math.atan2(diffX, diffY);
    }

    /**
     * {@inheritDoc}
     */
    public void update() {
        ((MoveComponent) this.getEntity().getComponent(MoveComponent.class).get())
        .move(Math.cos(this.angle), Math.sin(this.angle), 0);
    }
}
