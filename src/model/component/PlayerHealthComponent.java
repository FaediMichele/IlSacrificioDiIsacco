package model.component;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.enumeration.BasicHeartEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.HeartEnum;
import model.events.DamageEvent;
import model.events.DeadEvent;
import util.EventListener;
import util.Pair;

/**
 * This component controls the health of the entity.
 *
 */

public class PlayerHealthComponent extends AbstractComponent {

    private static final int DEFAULT_HEART_NUMBER = 3;
    private static final int MAX_HEARTS = 12;
    private static final int TIME_NO_DAMAGE = 1000;
    private List<Heart> hearts;
    private double time;
    /**
     * @param defaultHearts number of hearts of this kind
     * @param entity    entity for this component
     */
    public PlayerHealthComponent(final Entity entity, final double defaultHearts) {
        super(entity);
        this.time = 1000;
        final int realHeartNumber = Math.min((int) Math.floor(defaultHearts), MAX_HEARTS);
        this.hearts = new LinkedList<Heart>(Stream.iterate(0, i -> i + 1)
                .limit(realHeartNumber)
                .map(i -> new SimpleHeart(this.getEntity(), 1))
                .collect(Collectors.toList()));
        if ((int) Math.ceil(defaultHearts) <= MAX_HEARTS && defaultHearts - (int) Math.floor(defaultHearts) != 0) {
            this.hearts.add(new SimpleHeart(this.getEntity(), defaultHearts - (int) Math.floor(defaultHearts)));
        }
        this.sortHearts();
        this.registListener();
    }

    private void registListener() {
        this.registerListener(new EventListener<DamageEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final DamageEvent event) {
                if (time < TIME_NO_DAMAGE) {
                    return;
                } else {
                    time = 0;
                }
                if (event.getDamageValue().isPresent()) {
                    getDamaged(event.getDamageValue().get());
                } else {
                getDamaged(event.getSourceEntity().getComponent(DamageComponent.class).isPresent()
                        ? (event.getSourceEntity().getComponent(DamageComponent.class).get()).getDamage()
                        : 0);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {
        super.update(deltaTime);
        this.time += deltaTime;
    }
    /**
     * Default PlayerHealthComponent constructor.
     * 
     * @param entity entity for this component
     */
    public PlayerHealthComponent(final Entity entity) {
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
    public List<Pair<HeartEnum, Double>> getHeartPairs() {
        final List<Pair<HeartEnum, Double>> list = new LinkedList<>();
        hearts.forEach(h -> list.add(new Pair<HeartEnum, Double>(h.getColour(), h.getValue())));
        return list;
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
            return this.hearts.stream().map(h -> h.getValue()).reduce((x, y) -> x + y).get();
        }
        return 0;
    }

    /**
     * Adds an heart to the list (probably the entity captured it).
     * 
     * @param heart the heart
     * @return true if the operation was successful false otherwise.
     */
    public boolean addHeart(final Heart heart) {
        if (this.hearts.size() < MAX_HEARTS) {
            final List<Heart> heartsOfSameKind = this.hearts.stream().filter(h -> heart.getColour().equals(h.getColour())).collect(Collectors.toList());
            if (heartsOfSameKind.isEmpty()) {
                this.hearts.add(heart);
                return true;
            }
            final boolean checkMaxHearts = ((heart.getMaxHearts().isPresent() && heartsOfSameKind.size() < heart.getMaxHearts().get()) 
                    || !heart.getMaxHearts().isPresent());
                final Optional<Heart> lastHeart = heartsOfSameKind.stream().filter(h -> h.getValue() != h.getMaxValue()).findAny();
                if (lastHeart.isPresent()) {
                    final double remainingValue = lastHeart.get().addValue(heart.getValue());
                    if (remainingValue != 0 && checkMaxHearts) {
                        final Class<? extends Heart> heartClass = heart.getClass();
                        try {
                            this.hearts.add(heartClass.getConstructor(Entity.class, double.class)
                                                      .newInstance(this.getEntity(), remainingValue));
                        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (checkMaxHearts) {
                    this.hearts.add(heart);
                } else {
                    return false;
                }
                this.sortHearts();
                return true;
        }
        return false;
    }

    private void sortHearts() {
        Collections.sort(hearts, (h1, h2) -> h1.getValue() > h2.getValue() ? 1 : -1);
        Collections.sort(hearts, (h1, h2) -> h1.getColour().equals(BasicHeartEnum.BLACK) ? 1 : -1);
    }

    /**
     * The health is damaged, it could loose part of an heart or multiple hearts
     * based on the damageValue.
     * 
     * @param totalDamageValue the value of damage
     */
    protected void getDamaged(final double totalDamageValue) {
        double actualDamageValue = totalDamageValue;
        while (this.isAlive() && actualDamageValue != 0) {
            actualDamageValue = this.hearts.get(this.hearts.size() - 1).getDamaged(actualDamageValue);
            if (this.hearts.get(this.hearts.size() - 1).getValue() == 0.0) {
                this.hearts.remove(this.hearts.size() - 1);
            }
        }

        if (!this.isAlive()) {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DEAD);
            this.getEntity().postEvent(new DeadEvent(this.getEntity()));
        } else {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DAMAGING);
        }
    }

    /**
     * 
     * @return the number of Hearts.
     */

    public int getNumberOfHearts() {
        return this.hearts.size();
    }
}
