package model.component.collectible;

import java.util.Objects;

import model.entity.BlackPickupableHeart;
import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.events.ChangeDamageValueEvent;
import model.events.PickUpEvent;

/**
 * Collectible Component for the sticky bomb entity.
 */
public class MawOfTheVoidPickupableComponent extends AbstractPickupableComponent {
    private static final int DAMAGE = 50;

    /**
     * @param entity {@link Entity}
     */
    public MawOfTheVoidPickupableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Entity entity) {
        Objects.requireNonNull(entity);
        entity.postEvent(new ChangeDamageValueEvent(entity, DAMAGE));
        final BlackPickupableHeart blackHeart = new BlackPickupableHeart();
        blackHeart.changeRoom(entity.getRoom());
        entity.postEvent(new PickUpEvent(blackHeart));
        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        this.getEntity().getRoom().deleteEntity(this.getEntity());
        entity.detachComponent(this.getClass());
    }
}

