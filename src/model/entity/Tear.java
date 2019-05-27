package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.Directions;
import model.component.Mentality;
import model.component.MentalityComponent;
import model.component.MoveComponent;
import model.component.TearComponent;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {

    /**
     * Default constructor.
     * 
     * @param direction           the tear is moving
     * @param entityThatShootedMe shooter entity
     */
    public Tear(final Directions direction, final Entity entityThatShootedMe) {
        super();
        this.attachComponent(new MentalityComponent(this, Mentality.GOOD));
        this.attachComponent(new TearComponent(this, direction));
        this.attachComponent(new MoveComponent(this, getMoveComponent(entityThatShootedMe).getSpeed(),
                getMoveComponent(entityThatShootedMe).getMaxSpeed(),
                getMoveComponent(entityThatShootedMe).getFriction()));
        this.attachComponent(new BodyComponent(this, this.getBodyComponent(entityThatShootedMe).getPosition(),
                this.getBodyComponent(entityThatShootedMe).getHeight(),
                this.getBodyComponent(entityThatShootedMe).getWidth(),
                this.getBodyComponent(entityThatShootedMe).getWeight()));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Tear(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super();
        this.setDefaultComponents(entityBody, entityCollision);
    }

    private MoveComponent getMoveComponent(final Entity e) {
        return ((MoveComponent) e.getComponent(MoveComponent.class).get());
    }

    private BodyComponent getBodyComponent(final Entity e) {
        return ((BodyComponent) e.getComponent(BodyComponent.class).get());
    }
}
