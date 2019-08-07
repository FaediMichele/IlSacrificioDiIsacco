package view.javafx.game.menu;

import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * Class used to get all the JavaFx object in the scene.
 */
public final class ViewGetter {
    private static Scene scene;
    private ViewGetter() {
    }

    /**
     * Set the Scene to use.
     * @param s the Scene.
     */
    public static void setScene(final Scene s) {
        ViewGetter.scene = s;
    }
    /**
     * Get the {@link Scene}.
     * @return the {@link Scene}.
     */
    public static Scene getScene() {
        return scene;
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
}
