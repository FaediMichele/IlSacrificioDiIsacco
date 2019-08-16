package model.component.collectible;

import java.util.Objects;

import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.events.ChangeDamageValueEvent;

/**
 * Collectible Component for the sticky bomb entity.
 */
public class EightInchNailsPickupableComponent extends AbstractPickupableComponent {
    private static final double DAMAGE = 10;

    /**
     * @param entity {@link Entity}
     */
    public EightInchNailsPickupableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Entity entity) {
        Objects.requireNonNull(entity);
        entity.postEvent(new ChangeDamageValueEvent(entity, DAMAGE));

        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        this.getEntity().getRoom().deleteEntity(this.getEntity());
        entity.detachComponent(this.getClass());
    }
}

