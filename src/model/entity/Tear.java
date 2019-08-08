package model.entity;

import model.component.BodyComponent;
import model.component.Component;
import model.component.DamageComponent;
import model.component.MoveComponent;
import model.component.TearAIComponent;
import model.enumeration.EntityEnum;
import model.util.Position;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {
    private static final double HEIGHT = 10;
    private static final double WIDTH = 10;
    private final EntityEnum name;


    /**
     * Default constructor.
     * 
     * @param angle               the angle of the tear when it's shot
     * @param positions 
     * @param damage              the tear damage
     * @param lifetime .
     * @param speed .
     * @param name .
     * @param mentality .
     */
    public Tear(final int angle, 
                final Position positions, 
                final double damage, 
                final double lifetime, 
                final double speed, 
                final EntityEnum name,
                final Component mentality) {
        super();
        this.attachComponent(mentality);
        this.attachComponent(new TearAIComponent(this, angle));
        this.attachComponent(new DamageComponent(this, damage));
        this.attachComponent(new MoveComponent(this, speed));
        this.attachComponent(new BodyComponent(this, positions,
                                                        HEIGHT,
                                                        WIDTH,
                                                        0));
        this.name = name;
    }

//    /**
//     * @param entityBody          the {@link BodyComponent}
//     * @param entityCollision     the {@link CollisionComponent}
//     * @param entityStatus        the {@link StatusComponent}
//     * @param angle               the angle of the tear when it's shot
//     * @param entityThatShootedMe shooter entity
//     * @param damage              the damage of the Tear
//     */
//    public Tear(final BodyComponent entityBody, 
//                final CollisionComponent entityCollision,
//                final StatusComponent entityStatus, 
//                final int angle, 
//                final Entity entityThatShootedMe,
//                final double damage) {
//        this(angle, entityThatShootedMe, damage);
//        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return this.name;
    }
}
