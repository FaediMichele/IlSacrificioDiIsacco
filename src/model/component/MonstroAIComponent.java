package model.component;

import model.entity.Entity;
import model.entity.Player;
import model.events.TearShotEvent;
import model.game.Room;
import java.util.List;
import java.util.Optional;

/**
 * AI for the Monstro monster.
 */
public class MonstroAIComponent extends AbstractComponent {
    private static final double TOLERANCE = 30;
    private double angle;
    private boolean canShoot;

    /**
     * Each time the Monstro collides with something, re-calculates the angle to get
     * to isaac.
     * 
     * @param entity for this component
     */
    public MonstroAIComponent(final Entity entity) {
        super(entity);
        calculateAngle();
        angle = 0;
    }

    /**
     * returns the angle to get to Isaac.
     * 
     * @return
     */
    private boolean calculateAngle() {
        final Room r = this.getEntity().getRoom();
        if (r == null) {
            return false;
        }
        final List<? extends Entity> entitys = r.getEntities();
        if (entitys == null) {
            return false;
        }
        final Optional<? extends Entity> player = entitys.stream().filter(i -> i.getClass().equals(Player.class))
                .findAny();
        if (!player.isPresent()) {
            return false;
        }
        final BodyComponent playerBody = player.get().getComponent(BodyComponent.class).get();
        if (playerBody == null) {
            return false;
        }
        final BodyComponent myBody = this.getEntity().getComponent(BodyComponent.class).get();

        final Double diffX = playerBody.getPosition().getX() - myBody.getPosition().getX();
        final Double diffY = playerBody.getPosition().getY() - myBody.getPosition().getY();

        this.canShoot = (Math.abs(diffX) < TOLERANCE || Math.abs(diffY) < TOLERANCE);
        this.angle = Math.atan2(diffY, diffX) * 180.0 / Math.PI;
        return true;
    }

    /**
     * Update of the MoveComponent done by this AI.
     */
    @Override
    public void update(final Double deltaTime) {
        if (calculateAngle()) {
            super.getEntity().getComponent(MoveComponent.class).get().move(angle);
            if (this.canShoot) {
                getEntity().postEvent(new TearShotEvent(getEntity(), (int) this.angle));
            }
        } else {
            super.getEntity().getComponent(MoveComponent.class).get().stop();
        }
        super.update(deltaTime);
    }
}
