package model.component;

import model.entity.Entity;
import model.entity.Player;
import model.game.Room;
import util.Pair;
import util.StaticMethodsUtils;

import java.util.List;
import java.util.Optional;


/**
 * AI for the Gaper monster.
 */
public class FollowAIComponent extends AbstractComponent<FollowAIComponent> {

    private Pair<Double, Double> lastDest;
    /**
     * Each time the Gaper collides with something, re-calculates the angle to get to isaac.
     * @param entity for this component
     */
    public FollowAIComponent(final Entity entity) {
        super(entity);
        lastDest = null;
    }

    /**
     * Update of the MoveComponent done by this AI.
     */
    @Override
    public void update(final Double deltaTime) {
        final BodyComponent body = getEntity().getComponent(BodyComponent.class).get();
        if (lastDest == null || between(lastDest.getX(), body.getPosition().getX(), 4.0) || between(lastDest.getY(), body.getPosition().getY(), 4.0)) {
            final Room r = this.getEntity().getRoom();
            if (r == null) {
                return;
            }
            final List<? extends Entity> entitys = r.getEntities();
            if (entitys == null) {
                return;
            }
            final Optional<? extends Entity> player = entitys.stream().filter(i -> i instanceof Player).findAny();
            if (!player.isPresent()) {
                return;
            }
            lastDest = r.getRoute(getEntity(), player.get());
        }
        super.getEntity().getComponent(MoveComponent.class).get().move(StaticMethodsUtils.getAngle(lastDest, new Pair<Double, Double>(body.getPosition().getX(), body.getPosition().getY())));
        super.update(deltaTime);
    }
    private boolean between(final double d1, final double d2, final double range) {
        return Math.abs(d1 - d2) < range;
    }
}
