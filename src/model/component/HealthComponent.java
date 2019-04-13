package model.component;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.events.DamageEvent;
import model.entity.events.EventListener;

/**
 * This component controls the health of the entity.
 *
 */

public class HealthComponent extends AbstractComponent {

    private static final Heart DEFAULT_HEART_KIND = new SimpleHeart();
    private final List<Heart> hearts;

    /**
     * 
     * @param hearts    list of hearts
     * @param entity    entity for this component
     */
    public HealthComponent(final Entity entity, final List<Heart> hearts) {
        super(entity);
        this.hearts = hearts;
        this.registerListener(new EventListener<DamageEvent>() {

            @Override
            @Subscribe
            public void listenEvent(final DamageEvent event) {
                getDamaged(event.getSourceEntity().getComponent(DamageComponent.class).isPresent()
                        ? ((DamageComponent) event.getSourceEntity().getComponent(DamageComponent.class).get()).getDamage()
                        : 0);
            }

        });
    }

    /**
     * Default HealthComponent constructor.
     * 
     * @param entity entity for this component
     */
    public HealthComponent(final Entity entity) {
        super(entity);
        hearts = new ArrayList<>();
        this.hearts.add(DEFAULT_HEART_KIND);
    }

    /**
     * 
     * @return the state of the entity: death or alive
     */
    public boolean isAlive() {
        return !hearts.isEmpty();
    }

    /**
     * Adds an heart to the list (probably the entity captured it).
     * 
     * @param h the heart
     */
    protected void addHeart(final Heart h) {
        if (hearts.stream().filter(i -> i.getClass().equals(h.getClass())).count() == 0) {
            hearts.add(h);
        } else {
            hearts.stream().filter(i -> i.getClass().equals(h.getClass())).findAny().get().addHeart(h);
        }
    }

    /**
     * The health is damaged, it could loose part of an heart or multiple hearts
     * based on the damageValue.
     * 
     * @param totalDamageValue the value of damage
     */
    private void getDamaged(final double totalDamageValue) {
        double actualDamageValue = totalDamageValue;
        Heart lastHeart;
        while (isAlive() && actualDamageValue != 0) {
            lastHeart = hearts.get(hearts.size());
            actualDamageValue = lastHeart.getDamaged(actualDamageValue);
            if (lastHeart.getNumberOfHearts() == 0) {
                hearts.remove(lastHeart);
            }
        }
    }
}
