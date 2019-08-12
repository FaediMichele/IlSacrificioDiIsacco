package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * Mentality for the tears of the player.
 */
public class PlayerTearsMentalityComponent extends AbstractMentalityComponent{
    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_HURT_ME = new HashSet<>(
            Arrays.asList(PlayerMentalityComponent.class));

    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_DAMAGE = new HashSet<>(
            Arrays.asList(PlayerMentalityComponent.class));
    private static final Set<Class<? extends AbstractMentalityComponent>> CANNOT_COLLIDE = new HashSet<>(
            Arrays.asList(PlayerMentalityComponent.class,
                          PlayerTearsMentalityComponent.class));
    /**
    * 
    * @param entity the {@link Entity}
    */
    public PlayerTearsMentalityComponent(final Entity entity) {
        super(entity, CANNOT_DAMAGE, CANNOT_HURT_ME, CANNOT_COLLIDE);
    }
}
