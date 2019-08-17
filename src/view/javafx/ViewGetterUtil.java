package view.javafx;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Lambda;

/**
 * Class used to get all the JavaFx object in the scene.
 */
public final class ViewGetterUtil {
    private static boolean fullScreen;
    private static final List<Lambda> FULLSCREENLISTENER = new ArrayList<>();
    private static Stage stage;
    private static Scene scene;
    private ViewGetterUtil() {
    }

    /**
     * Set the Scene to use.
     * @param s the Scene.
     */
    public static void setScene(final Scene s) {
        ViewGetterUtil.scene = s;
    }
    /**
     * Get the {@link Scene}.
     * @return the {@link Scene}.
     */
    public static Scene getScene() {
        return ViewGetterUtil.scene;
    }

    /**
     * Set the Scene to use.
     * @param s the Scene.
     */
    public static void setStage(final Stage s) {
        ViewGetterUtil.stage = s;
    }
    /**
     * Get the {@link Scene}.
     * @return the {@link Scene}.
     */
    public static Stage getStage() {
        return ViewGetterUtil.stage;
    }

    /**
     * Get a node by his name.
     * T is the type of the node.
     * @param name the name of the node
     * @param type the type object in the scene.
     * @param <T> the type of the object.
     * @return the T object in the scene.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Node> T getNodeByName(final String name, final Class<T> type) {
        try {
            return (T) scene.lookup("#" + name);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Switch full screen or windowed.
     */
    public static void switchFullScreen() {
        ViewGetterUtil.stage.setFullScreen(fullScreen);
        ViewGetterUtil.fullScreen = !ViewGetterUtil.fullScreen;
        FULLSCREENLISTENER.forEach(Lambda::use);
    }

    /**
     * Get true if the screen is in full screen mode.
     * @return Get true if the screen is in full screen mode.
     */
    public static boolean isFullScreen() {
        return ViewGetterUtil.fullScreen;
    }

    /**
     * Set a listener for the full screen change event.
     * @param l a lambda function.
     */
    public static void addListenerFullScreen(final Lambda l) {
        FULLSCREENLISTENER.add(l);
    }
}
