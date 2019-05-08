package test;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.node.CircleList;
import view.node.CircleListJavafx;

/**
 * This class is used for develop learning of animation in javaFX.
 */
public class TestAnimation extends Application {
    private static final double INITIAL_PROPORTION = 0.4;
    private static final int NUMBER_RECTANGLE = 7;
    private static final double WIDTH_PROP = 0.1;
    private static final double HEIGHT_PROP = 0.1;
    private GridPane grid;
    private final CircleListJavafx list = new CircleListJavafx(160, 80, 0.5);
    private double maxX;
    private double maxY;

    /**
     * Test for the interface.
     * @param args not used.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        final Group g = new Group();
        maxX = primaryScreenBounds.getMaxX() / 2;
        maxY = primaryScreenBounds.getMaxY() / 2;

        final Scene s = new Scene(g, maxX * INITIAL_PROPORTION * 2, maxY * INITIAL_PROPORTION * 2);
        stage.setScene(s);
        this.grid = new GridPane();
        g.getChildren().add(grid);
        g.getChildren().add(list);
        list.setDuration(Duration.millis(1000));
        list.setLayoutY(maxY * INITIAL_PROPORTION / 2);
        list.setLayoutX(maxX * INITIAL_PROPORTION / 2 );
        addRectangles();

        grid.setOnMouseClicked(e -> {
            System.out.println("sono qui");
            list.rotateLeft();
            if (e.isPrimaryButtonDown()) {
                list.rotateLeft();
            } else if (e.isSecondaryButtonDown()) {
                list.rotateRight();
            }
        });
        stage.show();
    }

    private void addRectangles() {
        for (int i = 0; i < NUMBER_RECTANGLE; i++) {
            final Rectangle n = new Rectangle(maxX * INITIAL_PROPORTION * WIDTH_PROP, maxY * INITIAL_PROPORTION * HEIGHT_PROP);
            n.setFill(new Color(i * 0.15, i * 0.15, i * 0.15, 1.0));
            list.addElement(n);
            grid.getChildren().add(n);
        }
    }
}
