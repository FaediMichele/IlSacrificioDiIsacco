package view.javafx.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicMovementEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;
import util.SpritesExtractor;

/**
 * View and animations of Isaac.
 */

public class IsaacView extends AbstractEntityView {

    private static Map<MovementEnum, List<Image>> bodySprites = new HashMap<>();
    private static Map<MovementEnum, List<Image>> faceSprites = new HashMap<>();
    private static Image sufferSprite;
    private static Image deadSprite;

    private final Map<MovementEnum, Integer> bodyIndex = new HashMap<>();
    private final Map<MovementEnum, Integer> faceIndex = new HashMap<>();

    private Image face;
    private Image body;
    private MovementEnum lastMove = BasicMovementEnum.STATIONARY;
    private int countStationary;

    static {
        BufferedImage img = null;
        try {
            List<Image> movingUpSprite;
            List<Image> movingDownSprite;
            List<Image> movingRightSprite;
            List<Image> movingLeftSprite;

            List<Image> movingUpFaceSprite;
            List<Image> movingDownFaceSprite;
            List<Image> movingRightFaceSprite;
            List<Image> movingLeftFaceSprite;

            img = ImageIO.read(IsaacView.class.getResource("/gameImgs/character_001_isaac.png"));
            final List<Image> isaacBody = new ArrayList<>();
            final int deltaFace = 32;
            final int faces = 6;
            final int deltaBody = 32;
            final int bodies = 18;
            final int cols = 8;
            final int spritesEachMove = 10;
            final int spritesFaces = 2;
            isaacBody.add(SwingFXUtils.toFXImage(img.getSubimage(deltaFace * faces, 0, deltaBody, deltaBody), null));
            isaacBody.add(SwingFXUtils
                    .toFXImage(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody), null));
            isaacBody
                    .addAll((new SpritesExtractor(img, bodies, 3, cols, deltaBody, deltaBody, 0, deltaFace)).extract());

            movingDownSprite = isaacBody.subList(0, spritesEachMove - 1);
            movingUpSprite = new ArrayList<Image>();
            movingUpSprite.addAll(movingDownSprite);
            movingRightSprite = isaacBody.subList(spritesEachMove, spritesEachMove * 2 - 1);
            movingLeftSprite = new ArrayList<Image>();
            movingRightSprite.forEach(l -> {
                final BufferedImage tmp1 = SwingFXUtils.fromFXImage(l, null);
                final BufferedImage mir = new BufferedImage(tmp1.getWidth(), tmp1.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);

                final Graphics2D graphics = (Graphics2D) mir.getGraphics();
                final AffineTransform trans = new AffineTransform();
                trans.setToScale(-1, 1);
                trans.translate(-tmp1.getWidth(), 0);
                graphics.setTransform(trans);
                graphics.drawImage(tmp1, 0, 0, null);
                movingLeftSprite.add(SwingFXUtils.toFXImage(mir, null));
            });

            bodySprites.put(BasicMovementEnum.UP, movingUpSprite);
            bodySprites.put(BasicMovementEnum.DOWN, movingDownSprite);
            bodySprites.put(BasicMovementEnum.RIGHT, movingRightSprite);
            bodySprites.put(BasicMovementEnum.LEFT, movingLeftSprite);
            bodySprites.put(BasicMovementEnum.STATIONARY, movingDownSprite.subList(0, 1));

            final List<Image> isaacFace = (new SpritesExtractor(img, faces, 1, faces, deltaFace, deltaFace)).extract();
            movingDownFaceSprite = isaacFace.subList(0, spritesFaces - 1);
            movingRightFaceSprite = isaacFace.subList(spritesFaces, spritesFaces * 2 - 1);
            movingUpFaceSprite = isaacFace.subList(spritesFaces * 2, spritesFaces * 3 - 1);
            movingLeftFaceSprite = new ArrayList<Image>();
            movingRightFaceSprite.forEach(l -> {
                final BufferedImage tmp1 = SwingFXUtils.fromFXImage(l, null);
                final BufferedImage mir = new BufferedImage(tmp1.getWidth(), tmp1.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);

                final Graphics2D graphics = (Graphics2D) mir.getGraphics();
                final AffineTransform trans = new AffineTransform();
                trans.setToScale(-1, 1);
                trans.translate(-tmp1.getWidth(), 0);
                graphics.setTransform(trans);
                graphics.drawImage(tmp1, 0, 0, null);
                movingLeftFaceSprite.add(SwingFXUtils.toFXImage(mir, null));
            });

            faceSprites.put(BasicMovementEnum.UP, movingUpFaceSprite);
            faceSprites.put(BasicMovementEnum.DOWN, movingDownFaceSprite);
            faceSprites.put(BasicMovementEnum.RIGHT, movingRightFaceSprite);
            faceSprites.put(BasicMovementEnum.LEFT, movingLeftFaceSprite);
            faceSprites.put(BasicMovementEnum.STATIONARY, movingDownFaceSprite.subList(0, 1));

            sufferSprite = SwingFXUtils
                    .toFXImage(img.getSubimage(deltaFace * faces + deltaBody * 2, 0, deltaFace, deltaFace), null);

            final int deadX = 202;
            final int deadY = 159;
            final int deadWidth = 42;
            final int deadHeight = 32;
            deadSprite = SwingFXUtils.toFXImage(img.getSubimage(deadX, deadY, deadWidth, deadHeight), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Base constructor, initilizes the indexes.
     * 
     * @param id the {@link UUID}
     */
    public IsaacView(final UUID id) {
        super(id);
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
    }

    private MovementEnum rightMove(final MovementEnum move) {
        if (move.equals(BasicMovementEnum.STATIONARY)) {
            this.countStationary++;
        } else {
            this.countStationary = 0;
        }
        if (move != BasicMovementEnum.STATIONARY) {
            lastMove = move;
        }
        if (this.countStationary >= 15) {
            return BasicMovementEnum.STATIONARY;
        } else {
            return lastMove;
        }
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
        final double bodyShift = 1.5 / 5;
        System.out.println("X = " + super.getX());
        System.out.println("Y = " + super.getY());
        gc.drawImage(body, super.getX(), super.getY() + (super.getHeight() * bodyShift), (super.getHeight() * heightScale), super.getWidth());
        gc.drawImage(face, super.getX(), super.getY(), super.getHeight() * heightScale, super.getWidth());
    }

    /**
     * Default animation for {@link IsaacView}.
     */
    @Override
    public void def(final MovementEnum move) {
        final MovementEnum rightMove = this.rightMove(move);
        this.face = faceSprites.get(rightMove).get(faceIndex.get(rightMove));
        this.faceIndex.compute(rightMove, (k, v) -> (v + 1) % faceSprites.get(rightMove).size());
        this.body = bodySprites.get(rightMove).get(bodyIndex.get(rightMove));
        this.bodyIndex.compute(rightMove, (k, v) -> (v + 1) % bodySprites.get(rightMove).size());
    }

    /**
     * Damaging animation for {@link IsaacView}.
     */
    @Override
    public void damaging(final MovementEnum move) {
        this.face = sufferSprite;
        this.body = bodySprites.get(move).get(bodyIndex.get(move));
        this.bodyIndex.compute(move, (k, v) -> (v + 1) % bodySprites.get(move).size());
    }

    /**
     * Dead animation for {@link IsaacView}.
     */
    @Override
    public void dead(final MovementEnum move) {
        this.setStatus(BasicStatusEnum.DEAD);
    }
}
