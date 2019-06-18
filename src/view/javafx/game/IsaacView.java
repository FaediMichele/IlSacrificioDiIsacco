package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import util.SpritesExtractor;

/**
 * View and animations of Isaac.
 */

public class IsaacView {

    private List<BufferedImage> faceSprite = new ArrayList<>();
    private List<BufferedImage> movingUpSprite = new ArrayList<>();
    private List<BufferedImage> movingDownSprite = new ArrayList<>();
    private List<BufferedImage> movingRightSprite = new ArrayList<>();
    private List<BufferedImage> movingLeftSprite = new ArrayList<>();
    private BufferedImage deadSprite;
    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public IsaacView() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/character_001_isaac.png"));

        final int deltaFace = 32;
        final int faces = 6;
        faceSprite = (new SpritesExtractor(img, faces, 1, faces, deltaFace, deltaFace)).extract();

        List<BufferedImage> bodySprite = new ArrayList<>();
        final int deltaBody = 32;
        final int bodies = 18;
        final int cols = 8;
        final int spritesEachMove = 5;
        bodySprite.add(img.getSubimage(deltaFace * faces, 0, deltaBody, deltaBody));
        bodySprite.add(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody));
        bodySprite.addAll((new SpritesExtractor(img, bodies, 3, cols, deltaBody, deltaBody, 0, deltaFace)).extract());

        movingUpSprite = bodySprite.subList(0, spritesEachMove - 1);
        movingDownSprite = bodySprite.subList(spritesEachMove, spritesEachMove * 2 - 1);
        movingRightSprite = bodySprite.subList(spritesEachMove * 2, spritesEachMove * 3 - 1);
        movingLeftSprite = bodySprite.subList(spritesEachMove * 3, spritesEachMove * 4 - 1);

        final int deadX = 202;
        final int deadY = 159;
        final int deadWidth = 42;
        final int deadHeight = 32;
        deadSprite = img.getSubimage(deadX, deadY, deadWidth, deadHeight);
    }
}
