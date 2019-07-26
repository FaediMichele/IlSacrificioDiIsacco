package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the fire. 
* With this class you can create a red, blue or purple fire based on the path of which sheet you pass in the constructor.
*/
public class FireView extends AbstractEntityView {
    private static Map<FireColour, List<Image>> fireSprites = new HashMap<>();
    private static Map<FireColour, List<Image>> dyingFireSprites = new HashMap<>();
    private static Map<FireColour, List<Image>> fireGridSprites = new HashMap<>();

    static {
        try {
            fireSprites.put(FireColour.RED, FireView.getFires("effect_005_fire.png"));
            fireSprites.put(FireColour.PURPLE, FireView.getFires("effect_005_fire_purple.png"));
            fireSprites.put(FireColour.BLUE, FireView.getFires("effect_005_fire_blue.png"));

            dyingFireSprites.put(FireColour.RED, FireView.getDyingFires("effect_005_fire.png"));
            dyingFireSprites.put(FireColour.PURPLE, FireView.getDyingFires("effect_005_fire_purple.png"));
            dyingFireSprites.put(FireColour.BLUE, FireView.getDyingFires("effect_005_fire_blue.png"));

            fireGridSprites.put(FireColour.RED, FireView.getFireGrids("grid_fireplace.png"));
            fireGridSprites.put(FireColour.PURPLE, FireView.getFireGrids("grid_fireplace_purple.png"));
            fireGridSprites.put(FireColour.BLUE, FireView.getFireGrids("grid_fireplace_blue.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final List<Image> fireSprite;
    private final List<Image> dyingFireSprite;
    private final List<Image> fireGridSprite;

    private int fireIndex;
    private int dyingIndex;
    private int gridIndex;

    private Image fire;

    private static List<Image> getFires(final String firePath) throws IOException {
        final BufferedImage fireSheet = ImageIO.read(FireView.class.getResource(firePath));
        final int delta = 48;
        final int fires = 6;
        return (new SpritesExtractor(fireSheet, fires, 1, fires, delta, delta)).extract();
    }

    private static List<Image> getDyingFires(final String firePath) throws IOException {
        final BufferedImage fireSheet = ImageIO.read(FireView.class.getResource(firePath));
        final int delta = 48;
        final int fires = 6;
        return (new SpritesExtractor(fireSheet, fires + 1, 1, fires + 1, delta, delta, 0, delta)).extract();
    }

    private static List<Image> getFireGrids(final String fireGridPath) throws IOException {
        final BufferedImage fireGridSheet = ImageIO.read(FireView.class.getResource(fireGridPath));
        final int deltaGrid = 32;
        final int grids = 4;
        return new SpritesExtractor(fireGridSheet, grids, 2, 2, deltaGrid, deltaGrid).extract();
    }

    /**
     * Base constructor, initilizes the indexes and sets the list to use based on the fire colour.
     * @param colour the colour of this FireView
     * @param gv The gameView to which this entityView is added
     */
    public FireView(final GameViewImpl gv, final FireColour colour) {
        super(gv);
        this.fireSprite = fireSprites.get(colour);
        this.dyingFireSprite = dyingFireSprites.get(colour);
        this.fireGridSprite = fireGridSprites.get(colour);
        this.dyingIndex = 0;
        this.fireIndex = 0;
        this.gridIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().equals("dying")) {
            this.fire = dyingFireSprite.get(dyingIndex);
            dyingIndex += 1;
            if (dyingIndex > dyingFireSprite.size() && super.getGameView().isPresent()) {
                super.getGameView().get().removeEntity(this);
            }
        } else {
            this.fire = fireSprite.get(fireIndex);
            fireIndex = (fireIndex + 1) % fireSprites.size();
        }

        final Image grid = fireGridSprite.get(gridIndex);
        gridIndex = (gridIndex + 1) % fireGridSprites.size();

        final int gridShiftX = 6;
        final double fireScale = 8 / 11;
        final double gridScale = 4 / 11;
        final double gridShiftY = 7 / 11;
        gc.drawImage(super.resize(fire, (int) (super.getHeight() * fireScale), super.getWidth()), 
                        super.getX(), super.getY());
        gc.drawImage(super.resize(grid, (int) (super.getHeight() * gridScale), (super.getWidth() * 2) / 3), 
                        super.getX() + (super.getWidth() / gridShiftX), super.getY() + (super.getHeight() * gridShiftY));
    }
}
