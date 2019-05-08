package model.component;

import model.entity.Entity;

/**
 * Collectible Component of the heart entity: how the heart have to act when it's collected.
 */
public class HeartCollectibleComponent extends AbstractCollectibleComponent {

    private static final Class<? extends Heart> DEFAULT_HEART_KIND = SimpleHeart.class;
    private final Class<? extends Heart> heartKind;
    /**
     * 
     * @param entity {@link Entity}
     */
    public HeartCollectibleComponent(final Entity entity) {
        this (entity, DEFAULT_HEART_KIND);
    }

    /**
     * 
     * @param entity    heart entity {@link Entity}
     * @param heartKind kind of this heart
     */
    public HeartCollectibleComponent(final Entity entity, final Class<? extends Heart> heartKind) {
        super(entity);
        setCollectible(false);
        this.heartKind = heartKind;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean usable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean needInitialized() {
        return true;
    }

    @Override
    protected void use() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        final HealthComponent h = ((HealthComponent) super.getEntityThatCollectedMe().get().getComponent(HealthComponent.class).get());
        try {
            h.addHeart(this.heartKind.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
