package view.javafx.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicMovementEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;
import util.PlayerSpritesExtractor;

/**
 * View and animations of a Player.
 */

public abstract class AbstractPlayerView extends AbstractEntityView {

    private final Map<MovementEnum, List<Image>> bodySprites;
    private final Map<MovementEnum, List<Image>> faceSprites;

    private final Image sufferSprite;
    private final Image deadSprite;

    private final Map<MovementEnum, Integer> bodyIndex = new HashMap<>();
    private final Map<MovementEnum, Integer> faceIndex = new HashMap<>();

    private Image face;
    private Image body;

    /**
     * Base constructor, initializes the indexes.
     * 
     * @param id the {@link UUID}
     * @param path the path of the sheet of the player
     */
    AbstractPlayerView(final String path) {
        bodyIndex.put(BasicMovementEnum.UP, 0);
        bodyIndex.put(BasicMovementEnum.DOWN, 0);
        bodyIndex.put(BasicMovementEnum.RIGHT, 0);
        bodyIndex.put(BasicMovementEnum.LEFT, 0);
        bodyIndex.put(BasicMovementEnum.STATIONARY, 0);

        faceIndex.put(BasicMovementEnum.UP, 0);
        faceIndex.put(BasicMovementEnum.DOWN, 0);
        faceIndex.put(BasicMovementEnum.RIGHT, 0);
        faceIndex.put(BasicMovementEnum.LEFT, 0);
        faceIndex.put(BasicMovementEnum.STATIONARY, 0);

        final PlayerSpritesExtractor extractor = new PlayerSpritesExtractor(path);
        this.bodySprites = extractor.getBodySprites();
        this.faceSprites = extractor.getFaceSprites();
        this.sufferSprite = extractor.getSufferSprite();
        this.deadSprite = extractor.getDeadSprite();
    }

    /**
     * @return the faces of isaac
     */
    public Map<MovementEnum, List<Image>> getFaceSprites() {
        return this.faceSprites;
    }

    /**
     * {@inheritDoc}
     */
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.DEAD)) {
            gc.drawImage(deadSprite, super.getX(), super.getY(), super.getHeight(), super.getWidth());
            super.setStatus(BasicStatusEnum.DEFAULT);
            return;
        }
        final double heightScale = 1;
        final double bodyShift = 1.1 / 5;
        gc.drawImage(body, super.getX(), super.getY() + (super.getHeight() * bodyShift), (super.getHeight() * heightScale), super.getWidth());
        gc.drawImage(face, super.getX(), super.getY(), super.getHeight() * heightScale, super.getWidth());
    }

    /**
     * Default animation for {@link AbstractPlayerView}.
     */
    @Override
    public void def(final MovementEnum move) {
        this.setSprites(move, faceSprites, faceIndex);
        this.faceIndex.compute(move, (k, v) -> (v + 1) % faceSprites.get(move).size());
    }

    /**
     * 
     * @param move the movement fo the entity
     * @param actualFaceSprites the sprites it needs to use for the face
     * @param actualFaceIndex 
     */
    protected void setSprites(final MovementEnum move, final Map<MovementEnum, List<Image>> actualFaceSprites, final Map<MovementEnum, Integer> actualFaceIndex) {
        this.face = actualFaceSprites.get(move).get(actualFaceIndex.get(move));
        this.body = bodySprites.get(move).get(bodyIndex.get(move));
        this.bodyIndex.compute(move, (k, v) -> (v + 1) % bodySprites.get(move).size());
    }

    /**
     * Damaging animation for {@link AbstractPlayerView}.
     */
    @Override
    public void damaging(final MovementEnum move) {
        this.face = sufferSprite;
        this.body = bodySprites.get(move).get(bodyIndex.get(move));
        this.bodyIndex.compute(move, (k, v) -> (v + 1) % bodySprites.get(move).size());
    }

    /**
     * Dead animation for {@link AbstractPlayerView}.
     */
    @Override
    public void dead(final MovementEnum move) {
        this.setStatus(BasicStatusEnum.DEAD);
    }
}
