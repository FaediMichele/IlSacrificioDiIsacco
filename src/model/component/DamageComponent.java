package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.ChangeDamageValueEvent;
import util.EventListener;

/**
 * 
 * Component of damage that the entity inflicts on other enemy entities.
 *
 */
public class DamageComponent extends AbstractComponent {

    private double damage;

    /**
     * 
     * @param entity the entity this component is attached to
     * @param damage the value of the damage this entity provides
     */
    public DamageComponent(final Entity entity, final double damage) {
        super(entity);
        this.damage = damage;
        registerListener(new EventListener<ChangeDamageValueEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final ChangeDamageValueEvent event) {
                if (event.getDamageValue().isPresent()) {
                    addDamage(event.getDamageValue().get());
                }
            }
        });
    }

    /**
     * 
     * @return the value of the damage that the entity inflicts at this time
     */
    protected double getDamage() {
        return this.damage;
    }

    /**
     * @param damage the damage output to add to the current damage
     */
    protected void addDamage(final double damage) {
        this.damage += damage;
    }
}
