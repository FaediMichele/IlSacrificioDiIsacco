package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.entity.AbstractEnemyMovable;
import model.entity.Entity;

/**
 * Mentality of the {@link Enemy}.
 */
public class EnemyMentalityComponent extends AbstractMentalityComponent {

    private static final Set<Class<? extends Entity>> CANNOT_HURT_ME = new HashSet<>(
            Arrays.asList(AbstractEnemyMovable.class));

    private static final Set<Class<? extends Entity>> CANNOT_DAMAGE = new HashSet<>(
            Arrays.asList(AbstractEnemyMovable.class));
 
    /**
     * 
     */
    public EnemyMentalityComponent() {
        super(CANNOT_DAMAGE, CANNOT_HURT_ME);
    }
}
