package model.component.collectible;

import model.component.BodyComponent;
import model.component.BodyExplosionComponent;
import model.entity.Entity;

/**
 * Collectible Component of the bomb: how the bomb have to act when it's
 * collected.
 */
public class BombCollectableComponent extends AbstractCollectableComponent {

    private static final  double EXPLOSION_SCALE = 10;
    private static final  double TIME_BEFORE_EXPLODES = 1000;
    private static final  double EXPLOSION_TIME = 1000;

    /**
     * 
     *@param entity 
     */
    public BombCollectableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void use() {
        this.getEntity()
            .attachComponent(new BodyExplosionComponent(this.getEntity(), 
                                                        this.getEntity().getComponent(BodyComponent.class).get()
                                                                        .getPosition(), 
                                                        this.getEntity().getComponent(BodyComponent.class).get()
                                                                        .getHeight(), 
                                                        this.getEntity().getComponent(BodyComponent.class).get()
                                                                        .getWidth(),
                                                        0, 
                                                        TIME_BEFORE_EXPLODES, 
                                                        EXPLOSION_SCALE, 
                                                        EXPLOSION_TIME));
    }

}
