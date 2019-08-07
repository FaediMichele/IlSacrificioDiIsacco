package controller.menu;

import model.enumeration.PlayerEnum;

/**
 * This interface is for a simple info a the implemented character.
 */
public class CharacterInfo {
    private final Object nameImage;
    private final PlayerEnum info;
    private final Object img;

    /**
     * Create a character info with values and a image.
     * @param nameImage the image with the name of the character.
     * @param img an object to use in the GUI representing the character.
     * @param info The enum of the player.
     */
    public CharacterInfo(final Object nameImage, final Object img, final PlayerEnum info) {
        this.nameImage = nameImage;
        this.img = img;
        this.info = info;
    }

    /**
     * Get the name of the character.
     * @return the name.
     */
    public Object getNameImage() {
        return nameImage;
    }

    /**
     * Get the info of the character.
     * @return the {@link PlayerMenuInfo}.
     */
    public PlayerEnum getInfo() {
        return info;
    }

    /**
     * Get the image of the character.
     * @return the image (the type is based on the implementation).
     */
    public Object getImage() {
        return img;
    }
}
