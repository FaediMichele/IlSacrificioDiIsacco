package model.component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This component controls the health of the entity.
 *
 */

public class HealthComponent {

    private int maxHearts;
    private List<Heart> hearts;

    /**
     * 
     * @param maxHearts max number of hearts
     * @param heartsNumber initial number of hearts
     * @param defaultHeart kind of heart that has to be added at the list to initialize it
     */
    public HealthComponent(final int maxHearts, final int heartsNumber, final Heart defaultHeart) {
        this.maxHearts = maxHearts;
        hearts = Stream.iterate(0, i -> i + 1).limit(heartsNumber).map(i -> defaultHeart).collect(Collectors.toList());
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
     * @param h the heart
     */
    public void addHeart(final Heart h) {
        if (hearts.size() != maxHearts) {
            hearts.add(h);
        }
    }

    /**
     * The health is damaged, it could loose part of an heart or multiple hearts based on the damageValue.
     * @param totalDamageValue the value of damage
     */
    public void getDamaged(final double totalDamageValue) {
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
