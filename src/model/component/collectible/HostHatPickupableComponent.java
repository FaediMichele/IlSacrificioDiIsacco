package model.component.collectible;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.PsychoMentalityComponent;
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
        final Set<Class<? extends AbstractMentalityComponent>> mentalities = new HashSet<>();
        mentalities.add(PsychoMentalityComponent.class);
        entity.getComponent(AbstractMentalityComponent.class).get().addEntitiesCannotHurtMe(mentalities);

        this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        this.getEntity().getRoom().deleteEntity(this.getEntity());
    }
}

