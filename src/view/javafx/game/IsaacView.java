package view.javafx.game;

import java.awt.image.BufferedImage;
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

    private List<Image> faceSprite;
    private final List<Image> movingUpSprite;
    private final List<Image> movingDownSprite;
    private final List<Image> movingRightSprite;
    private final List<Image> movingLeftSprite;
    private final Image deadSprite;
    /**
     * Base constructor that extract the sprites from the sheet.
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
        final int spritesEachMove = 5;
        bodySprite.add(img.getSubimage(deltaFace * faces, 0, deltaBody, deltaBody));
        bodySprite.add(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody));
        bodySprite.addAll((new SpritesExtractor(img, bodies, 3, cols, deltaBody, deltaBody, 0, deltaFace)).extract());

        movingUpSprite = super.toFXImageList(bodySprite.subList(0, spritesEachMove - 1));
        movingDownSprite = super.toFXImageList(bodySprite.subList(spritesEachMove, spritesEachMove * 2 - 1));
        movingRightSprite = super.toFXImageList(bodySprite.subList(spritesEachMove * 2, spritesEachMove * 3 - 1));
        movingLeftSprite = super.toFXImageList(bodySprite.subList(spritesEachMove * 3, spritesEachMove * 4 - 1));

        final int deadX = 202;
        final int deadY = 159;
        final int deadWidth = 42;
        final int deadHeight = 32;
        deadSprite = SwingFXUtils.toFXImage(img.getSubimage(deadX, deadY, deadWidth, deadHeight), null);

        this.extractFaceSprite();

        /*super.setEntityActualSprites(faceSprite, movingUpSprite);
        super.animate();*/
    }

    /**
    * Setter for the face.
    * @param faceSprite sprites to put in the face
    */
    public void setFaceSprite(final List<Image> faceSprite) {
        this.faceSprite = faceSprite;
    }

    /**
     * method to extract the faceSprites from the sheet.
     * @throws IOException trying to read the sheet with the sprites
     */
    protected void extractFaceSprite() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/character_001_isaac.png"));
        final int deltaFace = 32;
        final int faces = 6;
        faceSprite = super.toFXImageList((new SpritesExtractor(img, faces, 1, faces, deltaFace, deltaFace)).extract());
    }
}
