package model.component.collectible;

import java.util.Objects;
import java.util.Random;

import model.component.BlackHeart;
import model.component.HealthComponent;
import model.component.SimpleHeart;
import model.entity.Entity;
import model.enumeration.BasicHeartEnum;
import model.enumeration.BasicMovementEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.HeartEnum;

/**
 * Collectible Component of the heart entity: how the heart have to act when
 * it's collected.
 */
public class HeartPickupableComponent extends AbstractPickupableComponent {

    private static final HeartEnum DEFAULT_HEART_COLOUR = BasicHeartEnum.RED;
    private final HeartEnum color;
    private final double actualValue;

    /**
     * 
     * @param entity {@link Entity}
     */
    public HeartPickupableComponent(final Entity entity) {
        this(entity, DEFAULT_HEART_COLOUR, new Random().nextDouble());
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

        if (this.color.equals(BasicHeartEnum.RED)) {
            healthComponent.addHeart(new SimpleHeart(entity, actualValue));
        } else if (this.color.equals(BasicHeartEnum.BLACK)) {
            healthComponent.addHeart(new BlackHeart(entity, actualValue));
        }
    }

    private HealthComponent getHealthComponent(final Entity e) {
        if (e.getComponent(HealthComponent.class).isPresent()) {
            return e.getComponent(HealthComponent.class).get();
        } else {
            throw new IllegalStateException();
        }
    }

}
