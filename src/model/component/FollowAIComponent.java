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
public class FollowAIComponent extends AbstractAIComponent {
    private static final int SEARCHTICK = 10;

    private Pair<Double, Double> lastDest;
    private int tick;
    /**
     * Each time the Gaper collides with something, re-calculates the angle to get to isaac.
     * @param entity for this component
     */
    public FollowAIComponent(final Entity entity) {
        super(entity);
        tick = 0;
    }

    /**
     * Update of the MoveComponent done by this AI.
     */
    @Override
    public void moveUpdate() {
        final BodyComponent body = getEntity().getComponent(BodyComponent.class).get();
        if (tick % SEARCHTICK == 0) {
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
                lastDest = new Pair<Double, Double>(body.getPosition().getX(), body.getPosition().getY());
                return;
            }
            lastDest = r.getRoute(getEntity(), player.get());
        }
        super.getMoveComponent(getEntity()).move(StaticMethodsUtils.getAngle(lastDest, new Pair<Double, Double>(body.getPosition().getX(), body.getPosition().getY())));
        tick++;
    }
}
