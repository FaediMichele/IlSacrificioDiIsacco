package model.component;

import java.util.Collections;
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

    private static final int DEFAULT_HEART_NUMBER = 3;
    private static final int MAX_HEARTS = 12;
    private List<Heart> hearts;

    /**
     * @param defaultHearts number of hearts of this kind
     * @param entity    entity for this component
     */
    public HealthComponent(final Entity entity, final int defaultHearts) {
        super(entity);
        int realHeartNumber = Math.min(defaultHearts, MAX_HEARTS);
        hearts = Stream.iterate(0, i -> i + 1).limit(realHeartNumber).map(i -> new SimpleHeart()).collect(Collectors.toList());
        /*hearts = new ArrayList<>();
        for (int i = 0; i < realHeartNumber; i++) {
                hearts.add(new SimpleHeart());
        }*/

        this.registListener();
    }

    private void registListener() {
        registerListener(new EventListener<DamageEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final DamageEvent event) {
                if (event.getDamageValue().isPresent()) {
                    getDamaged(event.getDamageValue().get());
                } else {
                getDamaged(event.getSourceEntity().getComponent(DamageComponent.class).isPresent()
                        ? ((DamageComponent) event.getSourceEntity().getComponent(DamageComponent.class).get()).getDamage()
                        : 0);
                }
            }
        });
    }

    /**
     * Default HealthComponent constructor.
     * 
     * @param entity entity for this component
     */
    public HealthComponent(final Entity entity) {
        this(entity, DEFAULT_HEART_NUMBER);
    }

    /**
     * 
     * @return the state of the entity: death or alive
     */
    public boolean isAlive() {
        return !this.hearts.isEmpty();
    }

    /**
     * 
     * @return the list of hearts
     */
    public List<Heart> getHearts() {
        return Collections.unmodifiableList(this.hearts);
    }

    /**
     * 
     * @return the life left to this entity
     */
    public double getLife() {
        if (this.isAlive()) {
            return hearts.size() - 1 + getLastHeart().getValue();
        }
        return 0;
    }

    /**
     * Adds an heart to the list (probably the entity captured it).
     * 
     * @param h the heart
     * @return true if the operation was successful false otherwise.
     */
    public boolean addHeart(final Heart h) {
        if (hearts.size() < MAX_HEARTS) {
            hearts.add(h);
            return true;
        }
        return false;
    }
    /**
     * The health is damaged, it could loose part of an heart or multiple hearts
     * based on the damageValue.
     * 
     * @param totalDamageValue the value of damage
     */
    protected void getDamaged(final double totalDamageValue) {
        double actualDamageValue = totalDamageValue;
        while (isAlive() && actualDamageValue != 0) {
            actualDamageValue = getLastHeart().getDamaged(actualDamageValue);
            if (getLastHeart().getValue() == 0) {
                hearts.remove(getLastHeart());
            }
        }
    }

    /**
     * 
     * @return the number of Hearts.
     */

    public int getNumberOfHearts() {
        return hearts.size();
    }

    private Heart getLastHeart() {
        return hearts.get(hearts.size() - 1);
    }
}