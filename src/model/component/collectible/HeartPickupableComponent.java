package model.component.collectible;

import java.util.Objects;
import java.util.Random;

import model.component.BlackHeart;
import model.component.HealthComponent;
import model.component.SimpleHeart;
import model.entity.Entity;
import model.enumeration.BasicHeartEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.HeartEnum;

/**
 * Collectible Component of the heart entity: how the heart have to act when
 * it's collected.
 */
public class HeartPickupableComponent extends AbstractPickupableComponent {
    private final HeartEnum color;
    private final double actualValue;
    private static final Random RND = new Random();

    /**
     * @param entity {@link Entity}
     * @param color of this hear
     */
    public HeartPickupableComponent(final Entity entity, final HeartEnum color) {
        this(entity, color, RND.nextDouble());
    }

    /**
     * 
     * @param entity    heart entity {@link Entity}
     * @param color of this heart
     * @param actualValue value of the heart
     */
    public HeartPickupableComponent(final Entity entity, final HeartEnum color, final double actualValue) {
        super(entity);
        this.color = color;
        this.actualValue = actualValue;

        if (actualValue > 0.5 && actualValue < 1) {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.FULL);
        } else if (actualValue > 0.0 && actualValue <= 0.5) {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.HALF);
        }
    }

    /**
     * @return heartKind
     */
    public HeartEnum getHeartColor() {
        return this.color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Entity entity) {
        Objects.requireNonNull(entity);
        final HealthComponent healthComponent = this.getHealthComponent(entity);
        final double realValue = actualValue > 0.5 && actualValue < 1 ? 1 : 0.5;

        if (this.color.equals(BasicHeartEnum.RED)) {
            healthComponent.addHeart(new SimpleHeart(entity, realValue));
        } else if (this.color.equals(BasicHeartEnum.BLACK)) {
            healthComponent.addHeart(new BlackHeart(entity, realValue));
        }

        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        this.getEntity().getRoom().deleteEntity(this.getEntity());
    }

    private HealthComponent getHealthComponent(final Entity e) {
        if (e.getComponent(HealthComponent.class).isPresent()) {
            return e.getComponent(HealthComponent.class).get();
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {
        if (actualValue > 0.5 && actualValue < 1) {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.FULL);
        } else if (actualValue > 0.0 && actualValue <= 0.5) {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.HALF);
        }
    }
}
