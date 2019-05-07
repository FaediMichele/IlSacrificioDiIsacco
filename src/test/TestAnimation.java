package test;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is used for develop learning of animation in javaFX.
 */
public class TestAnimation extends Application {
    private static final double INITIAL_PROPORTION = 0.4;
    private static final int NUMBER_RECTANGLE = 7;
    private static final double WIDTH_PROP = 0.1;
    private static final double HEIGHT_PROP = 0.1;
    private MyRectangle[] rect = new MyRectangle[NUMBER_RECTANGLE];
    private GridPane grid;
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
        addRectangles();
        stage.show();
        grid.setOnMouseClicked(e -> {
            for (int i = 0; i < rect.length; i++) {
                rect[i].changeAngle((rect[i].angle + 360 / rect.length) % 360);
            }
        });
    }

    private void addRectangles() {
        for (int i = 0; i < rect.length; i++) {
            rect[i] = new MyRectangle(new Rectangle(maxX * INITIAL_PROPORTION * WIDTH_PROP, maxX * INITIAL_PROPORTION * WIDTH_PROP), 
                    (i * 360 / rect.length),
                    maxX * INITIAL_PROPORTION * 0.5 - maxX * INITIAL_PROPORTION * WIDTH_PROP / 2,
                    maxY * INITIAL_PROPORTION * 0.5 - maxY * INITIAL_PROPORTION * HEIGHT_PROP / 2,
                    160, 80, 0.5);
        }
        for (int i = 0; i < rect.length; i++) {
            grid.getChildren().add(rect[i].rect);
        }
    }

    /**
     * This class is used to make a circle effect. Rectangle have position and scale based on their angle in the circle.
     * This is for develop and learning purpose.
     */
    private static class MyRectangle {
        private static final int ANIMATION_TIME = 1000;
        private final double y0;
        private final double x0;
        private final double dy;
        private final double dx;
        private final double dScale;

        private float angle;
        private final Rectangle rect;
        private final ScaleTransition st;
        private final TranslateTransition tt;

        /**
         * Create a new rectangle with defined angle.
         * @param r
         * @param angle the angle on the circle
         */
        MyRectangle(final Rectangle r, final float angle,
                final double x0, final double y0, final double dx, final double dy, final double dScale) {
            this.rect = r;
            this.y0 = y0;
            this.x0 = x0;
            this.dx = dx;
            this.dy = dy;
            this.dScale = dScale;
            this.angle = angle;
            this.rect.setScaleX(getScale());
            this.rect.setScaleY(getScale());
            st = new ScaleTransition(Duration.millis(ANIMATION_TIME), rect);
            tt = new TranslateTransition(Duration.millis(ANIMATION_TIME), rect);
        }

        public final int getX() {
            return (int) Math.round(x0 + Math.cos(Math.toRadians(angle)) * dx);
        }
        public final int getY() {
            return (int) Math.round(y0 - Math.sin(Math.toRadians(angle)) * dy);
        }
        public final double getScale() {
            return 1 - Math.sin(Math.toRadians(angle)) * dScale;
        }
        public void changeAngle(final float angle) {
            st.stop();
            tt.stop();
            tt.setFromX(getX());
            tt.setFromY(getY());
            st.setFromX(getScale());
            st.setFromY(getScale());
            this.angle = angle;
            tt.setToX(getX());
            tt.setToY(getY());
            st.setToX(getScale());
            st.setToY(getScale());
            st.play();
            tt.play();
        }
    }

}
