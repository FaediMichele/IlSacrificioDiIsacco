package model.component.collectible;

import java.util.Objects;

import model.entity.Bomb;
import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.events.PickUpEvent;

/**
 * Collectible Component for the sticky bomb entity.
 */
public class StickyBombsPickupableComponent extends AbstractPickupableComponent {
    private static final int NUM_BOMB = 10;

    /**
     * @param entity {@link Entity}
     */
    public StickyBombsPickupableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Entity entity) {
        Objects.requireNonNull(entity);
        for (int i = 0; i < NUM_BOMB; i++) {
            final Bomb b = new Bomb();
            b.changeRoom(entity.getRoom());
            entity.postEvent(new PickUpEvent(b));
        }

        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        this.getEntity().getRoom().deleteEntity(this.getEntity());
        entity.detachComponent(this.getClass());
    }
}

