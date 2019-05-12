package model.component;

import model.entity.Entity;

/**
 * Collectible Component of the heart entity: how the heart have to act when
 * it's collected.
 */
public class HeartCollectibleComponent extends AbstractCollectibleComponent {

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
     * {@inheritDoc}
     */
    @Override
    protected void init(final Entity entity) {
        final HealthComponent healthComponent = ((HealthComponent) entity
                .getComponent(HealthComponent.class).get());
        try {
            if (healthComponent.addHeart(this.heartKind.newInstance())) {
                deleteThisEntity();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
