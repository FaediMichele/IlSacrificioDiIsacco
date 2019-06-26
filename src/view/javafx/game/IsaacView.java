package view.javafx.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
 * View and animations of Isaac.
 */

public class IsaacView extends AbstractAnimatedEntityView {

    private List<Image> faceSprite = new ArrayList<Image>();
    private final List<Image> movingUpSprite;
    private final List<Image> movingDownSprite;
    private final List<Image> movingRightSprite;
    private final List<Image> movingLeftSprite;
    private final List<Image> movingUpFaceSprite;
    private final List<Image> movingDownFaceSprite;
    private final List<Image> movingRightFaceSprite;
    private final List<Image> movingLeftFaceSprite;
    private final Image deadSprite;

    /**
     * Base constructor that extract the sprites from the sheet.
     * 
     * @throws IOException trying to get the resource image
     */
    public IsaacView() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/character_001_isaac.png"));
        List<BufferedImage> bodySprite = new ArrayList<>();
        final int deltaFace = 32;
        final int faces = 6;
        final int deltaBody = 32;
        final int bodies = 18;
        final int cols = 8;
        final int spritesEachMove = 10;
        final int spritesFaces = 2;
        bodySprite.add(img.getSubimage(deltaFace * faces, 0, deltaBody, deltaBody));
        bodySprite.add(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody));
        bodySprite.addAll((new SpritesExtractor(img, bodies, 3, cols, deltaBody, deltaBody, 0, deltaFace)).extract());

        movingDownSprite = super.toFXImageList(bodySprite.subList(0, spritesEachMove));
        movingUpSprite = new ArrayList<Image>();
        movingUpSprite.addAll(movingDownSprite);
        movingRightSprite = super.toFXImageList(bodySprite.subList(spritesEachMove, spritesEachMove * 2));
        movingLeftSprite = new ArrayList<Image>();
        movingLeftSprite.addAll(movingRightSprite);
        movingLeftSprite.forEach(l -> {
            BufferedImage tmp1 = SwingFXUtils.fromFXImage(l, null);
            BufferedImage mir = new BufferedImage(tmp1.getWidth(), tmp1.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics = (Graphics2D) mir.getGraphics();
            AffineTransform trans = new AffineTransform();
            trans.setToScale(-1, 1);
            trans.translate(-tmp1.getWidth(), 0);
            graphics.setTransform(trans);
            graphics.drawImage(tmp1, 0, 0, null);
        });

        final int deadX = 202;
        final int deadY = 159;
        final int deadWidth = 42;
        final int deadHeight = 32;
        deadSprite = SwingFXUtils.toFXImage(img.getSubimage(deadX, deadY, deadWidth, deadHeight), null);

        this.extractFaceSprite();
        this.movingDownFaceSprite = this.faceSprite.subList(0, spritesFaces);
        this.movingRightFaceSprite = this.faceSprite.subList(spritesFaces, spritesFaces * 2);
        this.movingUpFaceSprite = this.faceSprite.subList(spritesFaces * 2, spritesFaces * 3);
        this.movingLeftFaceSprite = new ArrayList<Image>();
        this.movingLeftFaceSprite.addAll(this.movingRightFaceSprite);
        this.movingLeftFaceSprite.forEach(l -> {
            BufferedImage tmp1 = SwingFXUtils.fromFXImage(l, null);
            BufferedImage mir = new BufferedImage(tmp1.getWidth(), tmp1.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics = (Graphics2D) mir.getGraphics();
            AffineTransform trans = new AffineTransform();
            trans.setToScale(-1, 1);
            trans.translate(-tmp1.getWidth(), 0);
            graphics.setTransform(trans);
            graphics.drawImage(tmp1, 0, 0, null);
        });
    }

    /**
     * Setter for the face.
     * 
     * @param faceSprite sprites to put in the face
     */
    public void setFaceSprite(final List<Image> faceSprite) {
        this.faceSprite = faceSprite;
    }

    /**
     * method to extract the faceSprites from the sheet.
     * 
     * @throws IOException trying to read the sheet with the sprites
     */
    protected void extractFaceSprite() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/character_001_isaac.png"));
        final int deltaFace = 32;
        final int faces = 6;
        faceSprite = super.toFXImageList((new SpritesExtractor(img, faces, 1, faces, deltaFace, deltaFace)).extract());
    }
}
