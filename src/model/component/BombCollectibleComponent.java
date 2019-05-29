package model.component;

//import model.entity.BombTriggered;
import model.entity.Entity;

/**
 * Collectible Component of the bomb: how the bomb have to act when it's collected.
 */
public class BombCollectibleComponent extends AbstractCollectableComponent {

    private final double explosionScale;
    private final int timeBeforeExplodes;
    private final int explosionTime;

    /**
     * 
     * @param explosionScale it is the extension of the bomb when it explodes.
     * @param timeBeforeExplodes is time before it explodes in milliseconds.
     * @param explosionTime is duration of the explosion in milliseconds.
     * 
     * {@inheritDoc}.
     */
    public BombCollectibleComponent(final Entity entity, final double explosionScale, final int timeBeforeExplodes, final int explosionTime) {
        super(entity);
        this.explosionScale = explosionScale;
        this.timeBeforeExplodes = timeBeforeExplodes;
        this.explosionTime = explosionTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void use() {
        ((InventoryComponent) this.getEntityThatCollectedMe().get()
                .getComponent((InventoryComponent.class)).get()).releaseThing(this.getEntity());

        ((MentalityComponent) this.getEntity().getComponent(MentalityComponent.class).get())
            .setMentality(Mentality.PSYCHO);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeBeforeExplodes);
                    ((BodyComponent) getEntity().getComponent(BodyComponent.class).get()).scaleDimension(explosionScale);
                    /* qui la collisione con le altre entità fa danno e il suo sprite si modifica, ci sarà un'evento da postare per modificare lo sprite?*/
                    Thread.sleep(explosionTime);
                    deleteThisEntity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
