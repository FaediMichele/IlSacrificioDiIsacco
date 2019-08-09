package model.component.collectible;

import java.util.Objects;
import model.component.BlackHeart;
import model.component.HealthComponent;
import model.component.SimpleHeart;
import model.entity.Entity;
import model.enumeration.BasicHeartEnum;
import model.enumeration.HeartEnum;

/**
 * Collectible Component of the heart entity: how the heart have to act when
 * it's collected.
 */
public class HeartPickupableComponent extends AbstractPickupableComponent {

    private static final HeartEnum DEFAULT_HEART_COLOUR = BasicHeartEnum.RED;
    private static final double DEFAULT_VALUE = 1;
    private final HeartEnum color;
    private final double actualValue;

    /**
     * 
     * @param entity {@link Entity}
     */
    public HeartPickupableComponent(final Entity entity) {
        this(entity, DEFAULT_HEART_COLOUR, DEFAULT_VALUE);
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

// la pesno che i get di di stato debbano essere fatti a livello di componente della vita
//        if (actualValue > 0.5 && actualValue < 1) {
//            this.getEntity().getStatusComponent().setStatus(new Pair<Integer, String>(1, "full"));
//        } else if (actualValue > 0.0 && actualValue <= 0.5) {
//            this.getEntity().getStatusComponent().setStatus(new Pair<Integer, String>(1, "half"));
//        }
    }

    private HealthComponent getHealthComponent(final Entity e) {
        if (e.getComponent(HealthComponent.class).isPresent()) {
            return e.getComponent(HealthComponent.class).get();
        } else {
            throw new IllegalStateException();
        }
    }

}
