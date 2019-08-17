package model.component.collectible;

import model.component.BodyComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.PsychoMentalityComponent;
import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.util.Position;

/**
 * Collectible Component of the bomb: how the bomb have to act when it's
 * collected.
 */
public class BombCollectableComponent extends AbstractCollectableComponent {

    private static final  double EXPLOSION_SCALE = 3;
    private static final  double TIME_BEFORE_EXPLODES = 1000;
    private static final  double EXPLOSION_TIME = 500;
    private boolean triggered;
    private boolean exploded;
    private double explosionScale;
    private double timeBeforeExplodes;
    private double explosionTime;
    private double timePassed;



    /**
     * 
     *@param entity 
     */
    public BombCollectableComponent(final Entity entity) {
        super(entity);
        this.triggered = false;
        this.exploded = false;
        this.explosionScale = EXPLOSION_SCALE;
        this.timeBeforeExplodes = TIME_BEFORE_EXPLODES;
        this.explosionTime = EXPLOSION_TIME;
        this.timePassed = 0;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void use() {
        this.triggered = true;
        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.TRIGGERED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {
        if (this.triggered) {
            this.timePassed = this.timePassed + deltaTime;
            if (this.timePassed > this.timeBeforeExplodes && !this.exploded) {
                //set body component
                final BodyComponent body = this.getEntity().getComponent(BodyComponent.class).get();
                body.scaleDimension(explosionScale);
                final Position positionScale = body.getPosition();
                final double width1 = body.getWidth() / (this.explosionScale * 2);
                final double width2 = body.getWidth() / 2;
                final double height1 = body.getHeight() / (this.explosionScale * 2);
                final double height2 = body.getHeight() / 2;
                positionScale.setX(positionScale.getX() - width2 + width1);
                positionScale.setY(positionScale.getY() - height2 + height1);
                body.setPosition(positionScale);
                //change of mentality
                this.getEntity().detachComponent(this.getEntity().getComponent(AbstractMentalityComponent.class).get());
                this.getEntity().attachComponent(new PsychoMentalityComponent());
                //set status component
                this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.EXPLODED);
                this.exploded = true;
            }
            if (this.exploded && this.timePassed <= this.explosionTime + this.timeBeforeExplodes) {
                this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.EXPLODED);
             }
            if (this.exploded && this.timePassed > this.explosionTime + this.timeBeforeExplodes) {
               this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
               this.getEntity().getRoom().deleteEntity(this.getEntity());
            }

        }
    }


    /**
     * 
     * @return explosionScale how much the bomb is enlarged during the explosion.
     */
    public double getExplosionScale() {
        return explosionScale;
    }

    /**
     * 
     * @param explosionScale how much the bomb is enlarged during the explosion.
     * @return this
     */
    public BombCollectableComponent setExplosionScale(final double explosionScale) {
        this.explosionScale = explosionScale;
        return this;
    }

    /**
     * 
     * @return timeBeforeExplodes how long wait for the bomb before it explodes.
     */
    public double getTimeBeforeExplodes() {
        return timeBeforeExplodes;
    }

    /**
     * 
     * @param timeBeforeExplodes how long wait for the bomb before it explodes.
     * @return this
     */
    public BombCollectableComponent setTimeBeforeExplodes(final double timeBeforeExplodes) {
        this.timeBeforeExplodes = timeBeforeExplodes;
        return this;
    }

    /**
     * 
     * @return explosionTime how long the explosion lasts.
     */
    public double getExplosionTime() {
        return explosionTime;
    }

    /**
     * 
     * @param explosionTime how long the explosion lasts. .
     * @return this
     */
    public BombCollectableComponent setExplosionTime(final double explosionTime) {
        this.explosionTime = explosionTime;
        return this;
    }

}
