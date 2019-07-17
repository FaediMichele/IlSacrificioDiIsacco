package view.javafx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private Optional<List<Image>> lowerEntityActualSprites; 
    private final TimedViews lowerEntityTimedViews = new TimedViewsJavafx();
    private AnimatedView lowerEntityAnimated;

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
    public void animate() {
        this.entityAnimated = new AnimatedViewJavafx();
        this.entityAnimated.setFrames(entityActualSprites);
        this.entityTimedViews.add(entityAnimated);
        this.entityTimedViews.setMilliseconds(FRAMETIME);
        this.entityTimedViews.start();
        if (this.lowerEntityActualSprites.isPresent()) {
            this.lowerEntityAnimated = new AnimatedViewJavafx();
            this.lowerEntityAnimated.setFrames(this.lowerEntityActualSprites.get());
            this.lowerEntityTimedViews.add(this.lowerEntityAnimated);
            this.lowerEntityTimedViews.setMilliseconds(FRAMETIME);
            this.lowerEntityTimedViews.start();
        }
    }
}
