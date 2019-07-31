package model.entity;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.TearAIComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.PlayerMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {
    private static final double DEFAULT_DAMAGE = 0.5;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.PLAYER_TEAR;

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
        return (e.getComponent(MoveComponent.class).get());
    }

    private BodyComponent getBodyComponent(final Entity e) {
        return (e.getComponent(BodyComponent.class).get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
