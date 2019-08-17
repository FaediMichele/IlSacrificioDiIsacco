package model.component.collectible;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import model.component.mentality.AbstractMentalityComponent;
import model.entity.Bomb;
import model.entity.Entity;
import model.enumeration.BasicStatusEnum;

/**
 * Collectible Component for the host hat entity.
 */
public class HostHatPickupableComponent extends AbstractPickupableComponent {
    /**
     * @param entity {@link Entity}
     */
    public HostHatPickupableComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Entity entity) {
        Objects.requireNonNull(entity);
        final Set<Class<? extends Entity>> entities = new HashSet<>();
        entities.add(Bomb.class);
        entity.getComponent(AbstractMentalityComponent.class).get().addEntitiesCannotHurtMe(entities);

        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        this.getEntity().getRoom().deleteEntity(this.getEntity());
        entity.detachComponent(this.getClass());
    }
}

