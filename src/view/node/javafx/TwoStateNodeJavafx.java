package view.node.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.node.TwoStateNode;

/**
 * Implementation of {@link TwoStateNode}.
 */
public class TwoStateNodeJavafx implements TwoStateNode {
    private final ImageView img;
    private final Image imgOn;
    private final Image imgOff;
    private boolean active;

    /**
     * Create a new two state node.
     * @param img the image view.
     * @param imgOn the image when is on.
     * @param imgOff the image when is off.
     * @param initialState the initial state.
     */
    public TwoStateNodeJavafx(final ImageView img, final Image imgOn, final Image imgOff, final boolean initialState) {
        this.img = img;
        this.imgOn = imgOn;
        this.imgOff = imgOff;
        img.setImage(initialState ? imgOn : imgOff);
        this.active = initialState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void on() {
        if (!active) {
            change();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void off() {
        if (active) {
            change();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void change() {
        active = !active;
        img.setImage(active ? imgOn : imgOff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean state() {
        return active;
    }

}
