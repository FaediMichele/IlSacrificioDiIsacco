package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class which extracts the sprites from the sheets.
 */
public class SpritesExtractor {
    private List<BufferedImage> sprites = new ArrayList<>();
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
     * @param ystart start point fo the y
     */
    public SpritesExtractor(final BufferedImage sheet, final int count, final int rows, final int cols, final int width, final int height, final int xstart, final int ystart) {
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
    public List<BufferedImage> extract() {
        int x = xstart;
        int y = ystart;
        for (int index = 0; index < count; index++) {
            sprites.add(sheet.getSubimage(x, y, width, height));
            x = x + width;
            if (x >= width * cols) {
                x = xstart;
                y = y + height;
            }
            if (y >= height * rows) {
                break;
            }
        }
        return sprites;
    }

}
