package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *  Class which extracts the sprites from the sheets.
 */
public class SpritesExtractor {
    private final  List<BufferedImage> sprites;
    private final BufferedImage sheet;
    private final int count;
    private final int rows;
    private final int cols;
    private final int width;
    private final int height;
    private final int xstart;
    private final int ystart;

    /**
     * Basic constructor for extractor starting from the left upper angle of the sheet.
     * @param sheet from which the sprites are taken
     * @param count of the sprites
     * @param rows in the sheet
     * @param cols in the sheet
     * @param width of a sprite
     * @param height of a sprite
     */
    public SpritesExtractor(final BufferedImage sheet, final int count, final int rows, final int cols, final int width, final int height) {
        this (sheet, count, rows, cols, width, height, 0, 0);
    }

    /**
     * Basic constructor for the extractor.
     * @param sheet from which the sprites are taken
     * @param count of the sprites
     * @param rows in the sheet
     * @param cols in the sheet
     * @param width of a sprite
     * @param height of a spite
     * @param xstart start point for the x
     * @param ystart start point for the y
     * @param 
     */
    public SpritesExtractor(final BufferedImage sheet, final int count, final int rows, final int cols, final int width, final int height, final int xstart, final int ystart) {
        this.sprites = new ArrayList<>();
        this.sheet = sheet;
        this.count = count;
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.height = height;
        this.xstart = xstart;
        this.ystart = ystart;

    }

    /**
     * @return a list with all the sprites 
     */
    public List<Image> extract() {
        int x = xstart;
        int y = ystart;
        for (int index = 0; index < count; index++) {
            sprites.add(sheet.getSubimage(x, y, width, height));
            x = x + width;
            if (x >= width * cols) {
                x = xstart;
                y = y + height;
            }
            if (y > height * rows) {
                break;
            }
        }
        return this.toFXImageList(sprites);
    }

    /**
     * Convert a list of java.awt.image.BufferedImage to a list of javafx.scene.image.Image.
     * @param list the java.awt.image.BufferedImage list
     * @return the javafx.scene.image.Image list
     */
    private List<Image> toFXImageList(final List<BufferedImage> list) {
        final List<Image> imageList = new ArrayList<>();
        list.stream().forEach(i -> imageList.add(SwingFXUtils.toFXImage(i, null)));
        return imageList;
    }
}
