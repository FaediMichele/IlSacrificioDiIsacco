package util;

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
import javafx.scene.image.Image;
import model.enumeration.BasicMovementEnum;
import model.enumeration.MovementEnum;

/**
 *  Class which extracts the sprites from the player sheet.
 */
public class PlayerSpritesExtractor {
    private static final int DELTABODY = 32;
    private static final int DELTAFACE = 32;
    private static final int SPRITEFACES = 2;
    private static final int FACES = 6;

    private final BufferedImage img;

    /**
     * This constructor initializes the image.
     * @param path the path to follow to find the sheet
     */
    public PlayerSpritesExtractor(final String path) {
        BufferedImage imgTmp = null;
        try {
            imgTmp = ImageIO.read(PlayerSpritesExtractor.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = imgTmp;
    }

    /**
     * @return the bodies.
     */
    public Map<MovementEnum, List<Image>> getBodySprites() {
        final Map<MovementEnum, List<Image>> bodySprites = new HashMap<>();
        List<Image> movingUpSprite;
        List<Image> movingDownSprite;
        List<Image> movingRightSprite;
        List<Image> movingLeftSprite;

        final List<Image> isaacBody = new ArrayList<>();
        final int bodies = 18;
        final int cols = 8;
        final int spritesEachMove = 10;
        isaacBody.add(SwingFXUtils.toFXImage(img.getSubimage(DELTAFACE * FACES, 0, DELTABODY, DELTABODY), null));
        isaacBody.add(SwingFXUtils
                .toFXImage(img.getSubimage(DELTAFACE * FACES + DELTABODY, 0, DELTABODY, DELTABODY), null));
        isaacBody
                .addAll((new SpritesExtractor(img, bodies, 3, cols, DELTABODY, DELTABODY, 0, DELTAFACE)).extract());

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

        return bodySprites;
    }

    /**
     * @return the FACES.
     */
    public Map<MovementEnum, List<Image>> getFaceSprites() {
        final Map<MovementEnum, List<Image>> faceSprites = new HashMap<>();
        List<Image> movingUpFaceSprite;
        List<Image> movingDownFaceSprite;
        List<Image> movingRightFaceSprite;
        List<Image> movingLeftFaceSprite;

        final List<Image> isaacFace = (new SpritesExtractor(img, FACES, 1, FACES, DELTAFACE, DELTAFACE)).extract();
        movingDownFaceSprite = isaacFace.subList(0, SPRITEFACES);
        movingRightFaceSprite = isaacFace.subList(SPRITEFACES, SPRITEFACES * 2);
        movingUpFaceSprite = isaacFace.subList(SPRITEFACES * 2, SPRITEFACES * 3);
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

        return faceSprites;
    }

    /**
     * @return the suffering face.
     */
    public Image getSufferSprite() {
        return SwingFXUtils
                .toFXImage(img.getSubimage(DELTAFACE * FACES + DELTABODY * 2, 0, DELTAFACE, DELTAFACE), null);
    }

    /**
     * @return the dead player.
     */
    public Image getDeadSprite() {
        final int deadX = 202;
        final int deadY = 159;
        final int deadWidth = 42;
        final int deadHeight = 32;
        return SwingFXUtils.toFXImage(img.getSubimage(deadX, deadY, deadWidth, deadHeight), null);
    }
}
