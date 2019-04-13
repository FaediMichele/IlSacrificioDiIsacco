package model.component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.events.DamageEvent;
import model.entity.events.EventListener;

/**
 * This component controls the health of the entity.
 *
 */

public class HealthComponent extends AbstractComponent<HealthComponent> {

    private static final int DEFAULT_MAX_HEARTS = 9;
    private static final int DEFAULT_HEARTS_NUMBER = 3;
    private static final Heart DEFAULT_HEART_KIND = new SimpleHeart();
    private final int maxHearts;
    private final List<Heart> hearts;

    /**
     * 
     * @param maxHearts    max number of hearts
     * @param heartsNumber initial number of hearts
     * @param heartKind    kind of heart that has to be added at the list to
     *                     initialize it
     * @param entity       entity for this component
     */
    public HealthComponent(final Entity entity, final int maxHearts, final int heartsNumber, final Heart heartKind) {
        super(entity);
        this.maxHearts = maxHearts;
        hearts = Stream.iterate(0, i -> i + 1).limit(heartsNumber).map(i -> heartKind).collect(Collectors.toList());
        this.registerListener(new EventListener<DamageEvent>() {

            @Override
            @Subscribe
            public void listenEvent(final DamageEvent event) {
                getDamaged(event.getSourceEntity().getComponent(DamageComponent.class).isPresent()
                        ? ((DamageComponent) event.getSourceEntity().getComponent(DamageComponent.class).get()).getDamege()
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
        this.maxHearts = DEFAULT_MAX_HEARTS;
        hearts = Stream.iterate(0, i -> i + 1).limit(DEFAULT_HEARTS_NUMBER).map(i -> DEFAULT_HEART_KIND)
                .collect(Collectors.toList());
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
        if (hearts.size() != maxHearts) {
            hearts.add(h);
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
            if (lastHeart.getValue() == 0) {
                hearts.remove(lastHeart);
            }
        }
    }
}
