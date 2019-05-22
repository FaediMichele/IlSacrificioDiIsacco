package test;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private static final float NUMBER_RECTANGLE = 10f;
    private static final double WIDTH_PROP = 0.1;
    private static final double HEIGHT_PROP = 0.1;
    private final CircleList list = new CircleListJavafx(160, 80, 0.7);
    private final CircleListJavafx list1 = new CircleListJavafx(16, 8, 0.5);
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
        final GridPane grid = new GridPane();
        g.getChildren().add(grid);
        g.getChildren().add((CircleListJavafx) list);
        list.setDuration(Duration.millis(1000));
        list1.setDuration(Duration.millis(1000));
        list.addElement(list1);
        list.setMarginTop(maxY * INITIAL_PROPORTION / 2);
        list.setMarginLeft(maxX * INITIAL_PROPORTION / 2);

        //list1.setLayoutY(maxY * INITIAL_PROPORTION / 2);
        //list1.setLayoutX(maxX * INITIAL_PROPORTION / 2);
        addRectangles();
        addCircle();

        s.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {
                list.rotateLeft();
                list1.rotateLeft();
            }
            if (e.getCode() == KeyCode.D) {
                list.rotateRight();
                list1.rotateRight();
            }
            if (e.getCode() == KeyCode.LEFT) {
                list.setMarginLeft(list.getMarginLeft() - 10 / 2);
                //list1.setMarginLeft(list1.getMarginLeft() - 10 / 2);
            }
            if (e.getCode() == KeyCode.RIGHT) {
                list.setMarginLeft(list.getMarginLeft() + 10 / 2);
                //list1.setMarginLeft(list1.getMarginLeft() + 10 / 2);
            }
            if (e.getCode() == KeyCode.UP) {
                list.setHeight(list.getHeight() + 10 / 2);
                list1.setHeight(list1.getHeight() + 10 / 2);
            }
            if (e.getCode() == KeyCode.DOWN) {
                list.setWidth(list.getWidth() + 10 / 2);
                list1.setWidth(list1.getWidth() + 10 / 2);
            }
            if (e.getCode() == KeyCode.F) {
                ((Rectangle) list.getElement()).setFill(Color.RED);
                ((Circle) list1.getElement()).setFill(Color.RED);
            }
        });
        stage.show();
    }

    private void addRectangles() {
        for (int i = 0; i < NUMBER_RECTANGLE; i++) {
            final Rectangle n = new Rectangle(maxX * INITIAL_PROPORTION * WIDTH_PROP, maxY * INITIAL_PROPORTION * HEIGHT_PROP);
            n.setFill(new Color(i / NUMBER_RECTANGLE, 1 - i / NUMBER_RECTANGLE, 1 - i / NUMBER_RECTANGLE, 1.0));
            list.addElement(n);
        }
    }

    private void addCircle() {
        for (int i = 0; i < NUMBER_RECTANGLE; i++) {
            final Circle n = new Circle(maxX * INITIAL_PROPORTION * WIDTH_PROP, maxY * INITIAL_PROPORTION * HEIGHT_PROP, 3);
            n.setFill(new Color(i / NUMBER_RECTANGLE, i / NUMBER_RECTANGLE, i / NUMBER_RECTANGLE, 1.0));
            list1.addElement(n);
        }
    }
}
