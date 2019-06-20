package view.javafx.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import view.AnimatedView;
import view.TimedViews;
import view.javafx.AnimatedViewJavafx;
import view.javafx.TimedViewsJavafx;

/**
 * This class contains the method animate that can be used by all the entity view 
 * after having extracted the sprites from the sheet set the right list in entityActualSprites.
 */
public class AbstractAnimatedEntityView implements AnimatedEntityView {
    private static final long FRAMETIME = 300;
    private List<Image> entityActualSprites = new ArrayList<>();
    private final TimedViews entityTimedViews = new TimedViewsJavafx();
    private AnimatedView entityAnimated;

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
        // TODO Auto-generated method stub
        // entityActualSprites = ??
    }

    /**
     * {@inheritDoc}
     */
    public void animate() {
        this.entityAnimated = new AnimatedViewJavafx();
        entityAnimated.setFrames(entityActualSprites);
        entityTimedViews.add(entityAnimated);
        entityTimedViews.setMilliseconds(FRAMETIME);
        entityTimedViews.start();
    }
    /**
     * {@inheritDoc}
     */
    public List<Image> toFXImageList(final List<BufferedImage> list) {
        final List<Image> imageList = new ArrayList<>();
        list.stream().forEach(i -> imageList.add(SwingFXUtils.toFXImage(i, null)));
        return imageList;
    }
}
