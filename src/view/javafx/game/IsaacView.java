package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * View and animations of Isaac.
 */

public class IsaacView {

    /**
     * Base constructor that extracs the sprites frome the sheet.
     * @throws IOException tyring to get the resource image
     */
    public IsaacView() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/character_001_isaac.png"));
        final int deltaFace = 32;
        final int deltaBody = 32;
        final int faces = 6;
        final int bodies = 8;

        List<BufferedImage> faceSprite = new ArrayList<>();
        List<BufferedImage> bodySprite = new ArrayList<>();

        for (int i = 0; i < faces; i++) {
            faceSprite.add(img.getSubimage(i * deltaFace, 0, deltaFace, deltaFace));
        }

        bodySprite.add(img.getSubimage(deltaFace * faces, 0, deltaBody, deltaBody));
        bodySprite.add(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody));
    }
}
