package model.component.collectible;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.mentality.PsychoMentalityComponent;
import model.entity.Entity;

/**
 * Collectible Component of the bomb: how the bomb have to act when it's
 * collected.
 */
public class BombCollectableComponent extends AbstractCollectableComponent {

    private final double explosionScale;
    private final int timeBeforeExplodes;
    private final int explosionTime;

    /**
     * 
     * @param explosionScale     it is the extension of the bomb when it explodes.
     * @param timeBeforeExplodes is time before it explodes in milliseconds.
     * @param explosionTime      is duration of the explosion in milliseconds.
     * 
     *                           {@inheritDoc}. 
     */
    public BombCollectableComponent(final Entity entity, final double explosionScale, 
            final int timeBeforeExplodes, final int explosionTime) {
        super(entity);
        this.explosionScale = explosionScale;
        this.timeBeforeExplodes = timeBeforeExplodes;
        this.explosionTime = explosionTime;
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void init(final Entity entity) {
//        super.init(entity);
//        //getEntity().getStatusComponent().setStatus(new Pair<>(1, "collectible"));
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void use() {
        super.getInventoryComponent().releaseThing(this.getEntity());
        new Thread() {
            @Override
            public void run() {
                try {
                    //getEntity().getStatusComponent().setStatus(new Pair<>(1, "triggered"));
                    Thread.sleep(timeBeforeExplodes);
                    getEntity().getComponent(BodyComponent.class).get()
                            .scaleDimension(explosionScale);
                    //getEntity().getStatusComponent().setStatus(new Pair<>(1, "explode"));
                    getEntity().attachComponent(new DamageComponent(getEntity(), 0.5))
                            .attachComponent(new PsychoMentalityComponent(getEntity()));
                    Thread.sleep(explosionTime);
                    deleteThisEntity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
