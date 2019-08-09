package model.component;

import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.util.Position;
/**
 * 
 * body component for bomb explosion.
 *
 */
public class BodyExplosionComponent extends BodyComponent {

    private final double timeBeforeExplodes;
    private final double explosionScale;
    private final double explosionTime;
    private double timePassed;
    private final boolean exploded;

    /**
     * 
     * @param entity 
     * @param height 
     * @param width 
     * @param weight 
     * @param timeBeforeExplodes 
     * @param explosionScale 
     * @param explosionTime 
     * @param positions 
     */
    public BodyExplosionComponent(final Entity entity,
                                  final Position positions,
                                  final double height, 
                                  final double width, 
                                  final int weight, 
                                  final double timeBeforeExplodes,
                                  final double explosionScale,
                                  final double explosionTime) {
        super(entity, height, width, weight);
        this.timeBeforeExplodes = timeBeforeExplodes;
        this.explosionScale = explosionScale;
        this.explosionTime = explosionTime;
        this.timePassed = 0;
        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.TRIGGERED);
        this.exploded = false;
    }

    /**
     * 
     * @param deltaTime .
     */
    @Override
    public void update(final Double deltaTime) {
        this.timePassed = this.timePassed + deltaTime;
        if (this.timePassed > this.timeBeforeExplodes && !this.exploded) {
            this.scaleDimension(explosionScale);
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.EXPLODED);
        }
        if (this.exploded && this.timePassed > this.explosionTime + this.timeBeforeExplodes) {
            this.getEntity().getRoom().deleteEntity(this.getEntity());
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        }
    };

}
