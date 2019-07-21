package model.component.collectible;

import model.component.BlackHeart;
import model.component.HealthComponent;
import model.component.Heart;
import model.component.SimpleHeart;
import model.entity.Entity;
import util.Pair;

/**
 * Collectible Component of the heart entity: how the heart have to act when
 * it's collected.
 */
public class HeartPickupableComponent extends AbstractPickupableComponent {

    private static final Class<? extends Heart> DEFAULT_HEART_KIND = SimpleHeart.class;
    private static final double DEFAULT_VALUE = 1;
    private final Class<? extends Heart> heartKind;
    private final double actualValue;

    /**
     * 
     * @param entity {@link Entity}
     */
    public HeartPickupableComponent(final Entity entity) {
        this(entity, DEFAULT_HEART_KIND, DEFAULT_VALUE);
    }

    /**
     * 
     * @param entity    heart entity {@link Entity}
     * @param heartKind kind of this heart
     * @param actualValue value of the heart
     */
    public HeartPickupableComponent(final Entity entity, final Class<? extends Heart> heartKind, final double actualValue) {
        super(entity);
        this.heartKind = heartKind;
        this.actualValue = actualValue;
    }

    /**
     * @return heartKind
     */
    public Class<? extends Heart> getHeartKind() {
        return heartKind;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Entity entity) {
        final HealthComponent healthComponent = ((HealthComponent) entity
                .getComponent(HealthComponent.class).get());

        if (heartKind.equals(BlackHeart.class)) {
            healthComponent.addHeart(new BlackHeart.Builder(entity).heartValue(actualValue).build());
        } else if (heartKind.equals(SimpleHeart.class)) {
            healthComponent.addHeart(new SimpleHeart(actualValue));
        } else {
            try {
                if (healthComponent.addHeart(this.heartKind.newInstance())) {
                    deleteThisEntity();
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (actualValue > 0.5 && actualValue < 1) {
            this.getEntity().getStatusComponent().setStatus(new Pair<Integer, String>(1, "full"));
        } else if (actualValue > 0.0 && actualValue <= 0.5) {
            this.getEntity().getStatusComponent().setStatus(new Pair<Integer, String>(1, "half"));
        }
    }

}
