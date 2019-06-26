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
    private List<Image> movingUpSprite;
    private List<Image> movingDownSprite;
    private List<Image> movingRightSprite;
    private List<Image> movingLeftSprite;
    private Image deadSprite;

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
        bodySprite.add(img.getSubimage(deltaFace * faces, 0, deltaBody, deltaBody));
        bodySprite.add(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody));
        bodySprite.addAll((new SpritesExtractor(img, bodies, 3, cols, deltaBody, deltaBody, 0, deltaFace)).extract());

        bodySprite.forEach(f -> {
            try {
                ImageIO.write(f, "jpg", new File(bodySprite.indexOf(f) + ".jpg"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        movingDownSprite = super.toFXImageList(bodySprite.subList(0, spritesEachMove));
        movingUpSprite = new ArrayList<Image>();
        movingUpSprite.addAll(movingDownSprite);
        movingUpSprite.forEach(l -> {
            BufferedImage tmp1 = SwingFXUtils.fromFXImage(l, null);
            BufferedImage mir = new BufferedImage(tmp1.getWidth(), tmp1.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics = (Graphics2D) mir.getGraphics();
            AffineTransform trans = new AffineTransform();
            trans.setToScale(1, -1);
            trans.translate(0, -tmp1.getHeight());
            graphics.setTransform(trans);
            graphics.drawImage(tmp1, 0, 0, null);
            try {
                ImageIO.write(mir, "jpg", new File("test-mirror" + movingUpSprite.indexOf(l) + ".jpg"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
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
        /*
         * faceSprite.forEach(f -> { try { ImageIO.write(SwingFXUtils.fromFXImage(f,
         * null), "jpg", new File(faceSprite.indexOf(f) + "f.jpg")); } catch
         * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
         * });
         */
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
