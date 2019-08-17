package model.component.mentality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.entity.Entity;
import model.entity.Player;

/**
 * Mentality of the {@link Player}.
 */
public class PlayerMentalityComponent extends AbstractMentalityComponent {

    private static final Set<Class<? extends Entity>> CANNOT_HURT_ME = new HashSet<>(
            Arrays.asList(Player.class));

    private static final Set<Class<? extends Entity>> CANNOT_DAMAGE = new HashSet<>(
            Arrays.asList(Player.class));
    /**
     * 
     */
    public PlayerMentalityComponent() {
        super(CANNOT_DAMAGE, CANNOT_HURT_ME);
    }
}
