package view.javafx.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
 * View and animations of Isaac.
 */

public class IsaacView extends AbstractStatusEntityView {



    private static Map<Direction, List<Image>> bodySprites = new HashMap<>();
    private static Map<Direction, List<Image>> faceSprites = new HashMap<>();
    private static Image sufferSprite;
    private static Image deadSprite;

    private final Map<Direction, Integer> bodyIndex = new HashMap<>();
    private final Map<Direction, Integer> faceIndex = new HashMap<>();

    private Image face;
    private Image body;

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
            isaacBody.add(SwingFXUtils.toFXImage(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody), null));
            isaacBody.addAll((new SpritesExtractor(img, bodies, 3, cols, deltaBody, deltaBody, 0, deltaFace)).extract());

            movingDownSprite = isaacBody.subList(0, spritesEachMove);
            movingUpSprite = new ArrayList<Image>();
            movingUpSprite.addAll(movingDownSprite);
            movingRightSprite = isaacBody.subList(spritesEachMove, spritesEachMove * 2);
            movingLeftSprite = new ArrayList<Image>();
            movingLeftSprite.addAll(movingRightSprite);
            movingLeftSprite.forEach(l -> {
                final BufferedImage tmp1 = SwingFXUtils.fromFXImage(l, null);
                final BufferedImage mir = new BufferedImage(tmp1.getWidth(), tmp1.getHeight(), BufferedImage.TYPE_INT_ARGB);

                final Graphics2D graphics = (Graphics2D) mir.getGraphics();
                final AffineTransform trans = new AffineTransform();
                trans.setToScale(-1, 1);
                trans.translate(-tmp1.getWidth(), 0);
                graphics.setTransform(trans);
                graphics.drawImage(tmp1, 0, 0, null);
            });

            bodySprites.put(Direction.UP, movingUpSprite);
            bodySprites.put(Direction.DOWN, movingDownSprite);
            bodySprites.put(Direction.RIGHT, movingRightSprite);
            bodySprites.put(Direction.LEFT, movingLeftSprite);

            final List<Image> isaacFace = (new SpritesExtractor(img, faces, 1, faces, deltaFace, deltaFace)).extract();
            movingDownFaceSprite = isaacFace.subList(0, spritesFaces);
            movingRightFaceSprite = isaacFace.subList(spritesFaces, spritesFaces * 2);
            movingUpFaceSprite = isaacFace.subList(spritesFaces * 2, spritesFaces * 3);
            movingLeftFaceSprite = new ArrayList<Image>();
            movingLeftFaceSprite.addAll(movingRightFaceSprite);
            movingLeftFaceSprite.forEach(l -> {
                final BufferedImage tmp1 = SwingFXUtils.fromFXImage(l, null);
                final BufferedImage mir = new BufferedImage(tmp1.getWidth(), tmp1.getHeight(), BufferedImage.TYPE_INT_ARGB);

                final Graphics2D graphics = (Graphics2D) mir.getGraphics();
                final AffineTransform trans = new AffineTransform();
                trans.setToScale(-1, 1);
                trans.translate(-tmp1.getWidth(), 0);
                graphics.setTransform(trans);
                graphics.drawImage(tmp1, 0, 0, null);
            });

            faceSprites.put(Direction.UP, movingUpFaceSprite);
            faceSprites.put(Direction.DOWN, movingDownFaceSprite);
            faceSprites.put(Direction.RIGHT, movingRightFaceSprite);
            faceSprites.put(Direction.LEFT, movingLeftFaceSprite);

            sufferSprite = SwingFXUtils.toFXImage(img.getSubimage(deltaFace * faces + deltaBody * 2, 0, deltaFace, deltaFace), null);

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
     */
    public IsaacView() {
        super();
        bodyIndex.put(Direction.UP, 0);
        bodyIndex.put(Direction.DOWN, 0);
        bodyIndex.put(Direction.RIGHT, 0);
        bodyIndex.put(Direction.LEFT, 0);

        faceIndex.put(Direction.UP, 0);
        faceIndex.put(Direction.DOWN, 0);
        faceIndex.put(Direction.RIGHT, 0);
        faceIndex.put(Direction.LEFT, 0);
    }

    private boolean checkSuffer(final String status) {
        if (status.contentEquals("damaged")) {
            face = sufferSprite;
            return true;
        }
        return false;
    }

    private void setSprites(final Direction direction, final String status) {
        if (!checkSuffer(status)) {
            this.face = faceSprites.get(direction).get(faceIndex.get(direction));
            this.faceIndex.compute(direction, (k, v) -> (v + 1) % faceSprites.get(direction).size());
        }
        this.body = bodySprites.get(direction).get(bodyIndex.get(direction));
        this.bodyIndex.compute(direction, (k, v) -> (v + 1) % bodySprites.get(direction).size());
    }

    /**
     * {@inheritDoc}
     */
    public void draw(final GraphicsContext gc, final String status, final int x, final int y, final int height, final int width) {
        if (status.equals("dead")) {
            gc.drawImage(deadSprite, x, y);
            return;
        }

        if (status.equals("moving up")) {
            this.setSprites(Direction.UP, status);
        }

        if (status.equals("moving down")) {
            this.setSprites(Direction.DOWN, status);
        }

        if (status.equals("moving right")) {
            this.setSprites(Direction.RIGHT, status);
        }

        if (status.equals("moving left")) {
            this.setSprites(Direction.LEFT, status);
        }

        final double heightScale = 3 / 5;
        final double bodyShift = 2 / 5;
        gc.drawImage(super.resize(face, (int) (height * heightScale), width), x, y);
        gc.drawImage(super.resize(body, (int) (height * heightScale), width), x, y + (height * bodyShift));
    }

    /**
     * @return the faceSprites
     */
    public static Map<Direction, List<Image>> getFaceSprites() {
        return faceSprites;
    }

}
