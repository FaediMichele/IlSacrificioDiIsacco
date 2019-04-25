package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * 
 * Entity of the bomb positioned and primed.
 *
 */
public class BombTriggered extends AbstractEntity {

    private final BodyComponent entityExplosion;
    /**
     * 
     * @param entityExplosion it is the extension of the bomb when it explodes.
     * @param timeBeforeExplodes is time before it explodes in milliseconds.
     * @param explosionTime is duration of the explosion in milliseconds.
     * 
     * {@inheritDoc}.
     */
    public BombTriggered(final BodyComponent entityBody, final CollisionComponent entityCollision, final BodyComponent entityExplosion, final int timeBeforeExplodes, final int explosionTime) {
        super(entityBody, entityCollision);
        this.entityExplosion = entityExplosion;
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeBeforeExplodes);
                    explode(explosionTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void explode(final int explosionTime) {
        this.detachComponent(this.getComponent(entityExplosion.getClass()).get());
        this.attachComponent(entityExplosion);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(explosionTime);
                    deleteThisEntity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void deleteThisEntity() {
        getRoom().deleteEntity(this);
    }
}
