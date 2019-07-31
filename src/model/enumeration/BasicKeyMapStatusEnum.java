package model.enumeration;

/**
 * It contains the basic key maps for the status map.
 */
public enum BasicKeyMapStatusEnum implements KeyMapStatusEnum {
    /**
     * ENTITY = the entity that needs to be represented in the View.
     * STATUS = the entity status(damaged, alive, dead..).
     * MOVEMENT = UP, DOWN, LEFT, RIGHT.
     * UPGRADE = the possible upgrades.
     */
     STATUS, MOVEMENT, UPGRADE
}
