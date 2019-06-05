package view.character;

/**
 * This interface is for a simple info a the implemented character.
 */
public interface CharacterInfo {
    /**
     * Get the heart that the character have when the run is starter.
     * @return the default heart
     */
    double getLife();

    /**
     * Get the movement speed of the character.
     * @return the speed.
     */
    double getSpeed();

    /**
     * Get the output damage of the character.
     * @return the output damage.
     */
    double getDamage();
}
