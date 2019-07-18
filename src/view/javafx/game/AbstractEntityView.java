package view.javafx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.AnimatedView;
import view.TimedViews;
import view.javafx.AnimatedViewJavafx;
import view.javafx.TimedViewsJavafx;

/**
 * This class contains the method animate that can be used by all the entity view 
 * after having extracted the sprites from the sheet set the right list in entityActualSprites.
 */
public class AbstractEntityView implements EntityView {
    private List<Image> entityActualSprites = new ArrayList<>();
    private Optional<List<Image>> lowerEntityActualSprites; 

    /**
     * {@inheritDoc}
     */
    public List<Image> getEntityActualSprites() {
        return entityActualSprites;
    }

    /**
     * {@inheritDoc}
     */
    public void setEntityActualSprites(final List<Image> entityActualSprites) {
        this.entityActualSprites = entityActualSprites;
    }

    /**
     * {@inheritDoc}
     */
    public void setEntityActualSprites(final List<Image> upSprites, final List<Image> downSprites) {
        this.entityActualSprites = upSprites;
        this.lowerEntityActualSprites = Optional.of(downSprites);
    }

    /**
     * {@inheritDoc}
     */
    public Image resize(final Image image, final int height, final int width) {
        ImageView resizedImage = new ImageView(image);
        resizedImage.setFitHeight(height);
        resizedImage.setFitWidth(width);
        return resizedImage.snapshot(null, null);
    }
}
