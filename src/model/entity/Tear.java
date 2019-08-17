package model.entity;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.LifeTimeComponent;
import model.component.MoveComponent;
import model.component.collision.TearCollisionComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.enumeration.EntityEnum;
import model.enumeration.TearEnum;
import model.util.Position;
import util.Triplet;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {
    private static final double HEIGHT = 10;
    private static final double WIDTH = 10;
    private final TearEnum name;


    /**
     * Default constructor.
     * 
     * @param angle          the angle of the tear when it's shot.
     * @param positions      the position from which the tear should be thrown.
     * @param movementPlayer the velocity of the player.
     * @param damage         the tear damage.
     * @param lifetime       life time of the tear.
     * @param speed          tear speed.
     * @param name           name of tear.
     * @param mentality      mentality of tear.
     */
    public Tear(final int angle, 
                final Position positions, 
                final Triplet<Double, Double, Double> movementPlayer, 
                final double damage, 
                final double lifetime, 
                final double speed, 
                final TearEnum name, 
                final AbstractMentalityComponent mentality) {
        super();
        final MoveComponent moveC = new MoveComponent(this, speed, 1, speed);
        this.attachComponent(mentality);
        this.attachComponent(new TearCollisionComponent(this))
            .attachComponent(new DamageComponent(this, damage))
            .attachComponent(moveC)
            .attachComponent(new BodyComponent(this, positions, HEIGHT, WIDTH, 0))
            .attachComponent(new LifeTimeComponent(this, lifetime));
        moveC.move(((Position) movementPlayer).getClone().clipToLength(speed * 0.01));
        moveC.move(angle);
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return this.name;
    }
}
