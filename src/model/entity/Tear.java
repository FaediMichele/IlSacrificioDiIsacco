package model.entity;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.TearAIComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.PlayerMentalityComponent;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {
    private static final double DEFAULT_DAMAGE = 0.5;
    /**
     * Default constructor.
     * 
     * @param angle           the angle of the tear when it's shot
     * @param entityThatShootedMe shooter entity
     */
    public Tear(final int angle, final Entity entityThatShootedMe) {
        super();
        final int minimizeBodySprite = 2;
        this.attachComponent(new PlayerMentalityComponent(this));
        this.attachComponent(new TearAIComponent(this, angle));
        this.attachComponent(new DamageComponent(this, DEFAULT_DAMAGE));
        this.attachComponent(new MoveComponent(this, getMoveComponent(entityThatShootedMe).getSpeed(),
                getMoveComponent(entityThatShootedMe).getMaxSpeed(),
                getMoveComponent(entityThatShootedMe).getFriction()));
        this.attachComponent(new BodyComponent(this, this.getBodyComponent(entityThatShootedMe).getPosition(),
                this.getBodyComponent(entityThatShootedMe).getHeight() / minimizeBodySprite,
                this.getBodyComponent(entityThatShootedMe).getWidth() / minimizeBodySprite,
                this.getBodyComponent(entityThatShootedMe).getWeight()));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     * @param angle           the angle of the tear when it's shot
     * @param entityThatShootedMe shooter entity
     */
    public Tear(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus, final int angle, final Entity entityThatShootedMe) {
        this(angle, entityThatShootedMe);
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

    private MoveComponent getMoveComponent(final Entity e) {
        return ((MoveComponent) e.getComponent(MoveComponent.class).get());
    }

    private BodyComponent getBodyComponent(final Entity e) {
        return ((BodyComponent) e.getComponent(BodyComponent.class).get());
    }
}
