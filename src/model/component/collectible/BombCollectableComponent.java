package model.component.collectible;

import model.component.BodyComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.PsychoMentalityComponent;
import model.entity.Entity;
import model.enumeration.BasicStatusEnum;

/**
 * Collectible Component of the bomb: how the bomb have to act when it's
 * collected.
 */
public class BombCollectableComponent extends AbstractCollectableComponent {

    private static final  double EXPLOSION_SCALE = 3;
    private static final  double TIME_BEFORE_EXPLODES = 1000;
    private static final  double EXPLOSION_TIME = 1000;
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
                BodyComponent body = this.getEntity().getComponent(BodyComponent.class).get();
//                Position positionBefore = body.getPosition();
                System.out.println(body.getPosition());
                body.scaleDimension(explosionScale);
                Position positionScale = body.getPosition();
                final double width1 = body.getWidth() / (this.explosionScale * 2);
                final double width2 = body.getWidth() / 2;
                final double height1 = body.getHeight() / (this.explosionScale * 2);
                final double height2 = body.getHeight() / 2;
                //System.out.println(height1 + " " + height2 + " " + width1 + " " + width2);
                positionScale.setX(positionScale.getX() - width2 + width1);
                positionScale.setY(positionScale.getY() - height2 + height1);
                body.setPosition(positionScale);
                System.out.println(body.getPosition());

//                body.setPosition(new Position(body.getPosition().getX() - 40, 
//                                              body.getPosition().getY() - 40, 0.0));
                //change of mentality
                this.getEntity().detachComponent(this.getEntity().getComponent(AbstractMentalityComponent.class).get());
                this.getEntity().attachComponent(new PsychoMentalityComponent(this.getEntity()));
                //set status component
                this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.EXPLODED);
                this.exploded = true;
            }
//            if (this.exploded && this.timePassed > this.explosionTime + this.timeBeforeExplodes) {
//               this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
//               this.getEntity().getRoom().deleteEntity(this.getEntity());
//            }

        }
    }


    /**
     * 
     * @return explosionScale
     */
    public double getExplosionScale() {
        return explosionScale;
    }

    /**
     * 
     * @param explosionScale .
     * @return this
     */
    public BombCollectableComponent setExplosionScale(final double explosionScale) {
        this.explosionScale = explosionScale;
        return this;
    }

    /**
     * 
     * @return timeBeforeExplodes
     */
    public double getTimeBeforeExplodes() {
        return timeBeforeExplodes;
    }

    /**
     * 
     * @param timeBeforeExplodes .
     * @return this
     */
    public BombCollectableComponent setTimeBeforeExplodes(final double timeBeforeExplodes) {
        this.timeBeforeExplodes = timeBeforeExplodes;
        return this;
    }

    /**
     * 
     * @return explosionTime;
     */
    public double getExplosionTime() {
        return explosionTime;
    }

    /**
     * 
     * @param explosionTime .
     * @return this
     */
    public BombCollectableComponent setExplosionTime(final double explosionTime) {
        this.explosionTime = explosionTime;
        return this;
    }

}
