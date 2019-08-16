package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.events.DamageEvent;
import model.events.DeadEvent;
import util.EventListener;

/**
 * The life for the enemy.
 */
public class EnemyHealthComponent extends AbstractComponent {
    private double life;

    /**
     * Component for the life of the enemy.
     * @param entity entity for this component
     * @param life the life of the enemy.
     */
    public EnemyHealthComponent(final Entity entity, final double life) {
        super(entity);
        this.life = life;
        this.registListener();
    }

    private void registListener() {
        this.registerListener(new EventListener<DamageEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final DamageEvent event) {
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
     * 
     * @return the state of the entity: death or alive
     */
    public boolean isAlive() {
        return this.life > 0;
    }

    /**
     * 
     * @return the life left to this entity
     */
    public double getLife() {
        return this.life;
    }

    /**
     * Add life.
     * @param life the heart to add,
     */
    public void addHeart(final double life) {
        this.life += life;
    }

    /**
     * The health is damaged, it could loose part of an heart or multiple hearts
     * based on the damageValue.
     * 
     * @param totalDamageValue the value of damage
     */
    protected void getDamaged(final double totalDamageValue) {
        life -= totalDamageValue;

        if (!this.isAlive()) {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DEAD);
            this.getEntity().postEvent(new DeadEvent(this.getEntity()));
        } else {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DAMAGING);
        }
    }
}
