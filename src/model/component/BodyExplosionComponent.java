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
    private boolean exploded;

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
        setPosition(positions);
    }

    /**
     * 
     * @param deltaTime .
     */
    @Override
    public void update(final Double deltaTime) {
        this.timePassed = this.timePassed + deltaTime;
        if (this.timePassed > this.timeBeforeExplodes && !this.exploded) {
            System.out.println("exploded");
            this.scaleDimension(explosionScale);
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.EXPLODED);
//          this.getEntity().attachComponent(new PsychoMentalityComponent(this.getEntity()));
//                            .getStatusComponent().setStatus(BasicStatusEnum.EXPLODED);
            this.exploded = true;
        }
        if (this.exploded && this.timePassed > this.explosionTime + this.timeBeforeExplodes) {
//            this.getEntity().getRoom().deleteEntity(this.getEntity());
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        }
    }

}
