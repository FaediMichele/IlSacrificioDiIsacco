package view.javafx.game;

import java.util.List;
import javafx.scene.image.Image;

/**
 * Common methods to all the entity views.
 */
public interface AnimatedEntityView {
    /**
     * @return the list of images which is being animated
     */
    List<Image> getEntityActualSprites();
 
    /**
     * @param entityActualSprites the new list of images that has to animated from now on
     */
    void setEntityActualSprites(List<Image> entityActualSprites);

    /**
     * Handles the entities with two sprites, one above the other (examples: head and body, fire and its grid).
     * @param upperSprites the sprites that have to be animated in the upper part
     * @param lowerSprites the sprites that have to be animted in the lower part
     */
    void setEntityActualSprites(List<Image> upperSprites, List<Image> lowerSprites);

    /**
     * this method starts the animation.
     */
    void animate();

}
