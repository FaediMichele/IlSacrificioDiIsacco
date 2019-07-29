package model.component;


import java.util.Set;

import model.entity.Entity;
import model.entity.Player;
import model.game.Room;
import util.Pair;

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
    protected void moveUpdate() {
        if (SEARCHTICK % tick == 0) {
            final Room r = this.getEntity().getRoom();
            if (r == null) {
                return;
            }
            final Set<? extends Entity> entitys = r.getEntity();
            if (entitys == null) {
                return;
            }
            final Optional<? extends Entity> isaac = entitys.stream().filter(i -> i.getClass().equals(Player.class)).findAny();
            if (!isaac.isPresent()) {
                return;
            }
            lastDest = r.getRoute(getEntity(), isaac.get());
        }
        super.getMoveComponent(getEntity()).move(lastDest.getX(), lastDest.getY(), 0);
        tick++;
    }
}
