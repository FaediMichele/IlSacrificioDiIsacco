package view.javafx.game;

import java.util.Optional;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * This class contains the method animate that can be used by all the entity view 
 * after having extracted the sprites from the sheet set the right list in entityActualSprites.
 */
public abstract class AbstractEntityView implements EntityView {
    private final Optional<GameView> gameView;

    AbstractEntityView(final GameView gv) {
        super();
        this.gameView = Optional.of(gv);
    }

    AbstractEntityView() {
        super();
        this.gameView = Optional.empty();
    }

    /**
     * 
     * @return the GameView to which the entityView is attached (if there is one);
     */
    public Optional<GameView> getGameView() {
        return gameView;
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
