package view;

/**
 * This interface is for a simple info a the implemented character.
 */
public class CharacterInfo {
    private static final double MAXLIFE = 9;
    private static final double MAXSPEED = 10;
    private static final double MAXDAMAGE = 10;

    private final Object nameImage;
    private final boolean locked;
    private final double life;
    private final double speed;
    private final double damage;
    private final Object img;

    /**
     * Create a character info with values and a image.
     * @param nameImage the image with the name of the character.
     * @param locked true if the character cannot be used.
     * @param life the life.
     * @param speed the speed. 
     * @param damage the damage output.
     * @param img an object to use in the GUI representing the character.
     */
    public CharacterInfo(final Object nameImage, final boolean locked, final double life, final double speed, final double damage, final Object img) {
        this.nameImage = nameImage;
        this.locked = locked;
        this.life = life;
        this.speed = speed;
        this.damage = damage;
        this.img = img;
    }

    /**
     * Get the name of the character.
     * @return the name.
     */
    public Object getNameImage() {
        return nameImage;
    }

    /**
     * Return true if the character is locked or unlocked.
     * @return the lock status of the character.
     */
    public boolean isActive() {
        return locked;
    }

    /**
     * Get the heart that the character have when the run is starter.
     * @return the default heart
     */
    public double getLife() {
        return life / MAXLIFE;
    }

    /**
     * Get the movement speed of the character.
     * @return the speed.
     */
    public double getSpeed() {
        return speed / MAXSPEED;
    }

    /**
     * Get the output damage of the character.
     * @return the output damage.
     */
    public double getDamage() {
        return damage / MAXDAMAGE;
    }

    /**
     * Get the image of the character.
     * @return the image (the type is based on the implementation).
     */
    public Object getImage() {
        return img;
    }
}
