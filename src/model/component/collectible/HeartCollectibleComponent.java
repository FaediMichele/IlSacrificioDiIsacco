package model.component.collectible;

import model.component.BlackHeart;
import model.component.HealthComponent;
import model.component.Heart;
import model.component.SimpleHeart;
import model.entity.Entity;

/**
 * Collectible Component of the heart entity: how the heart have to act when
 * it's collected.
 */
public class HeartCollectibleComponent extends AbstractPickupableComponent {

    private static final Class<? extends Heart> DEFAULT_HEART_KIND = SimpleHeart.class;
    private final Class<? extends Heart> heartKind;

    /**
     * 
     * @param entity {@link Entity}
     */
    public HeartCollectibleComponent(final Entity entity) {
        this(entity, DEFAULT_HEART_KIND);
    }

    /**
     * 
     * @param entity    heart entity {@link Entity}
     * @param heartKind kind of this heart
     */
    public HeartCollectibleComponent(final Entity entity, final Class<? extends Heart> heartKind) {
        super(entity);
        this.heartKind = heartKind;
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
            healthComponent.addHeart(new BlackHeart.Builder(entity).build());
        } else {
            try {
                if (healthComponent.addHeart(this.heartKind.newInstance())) {
                    deleteThisEntity();
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
