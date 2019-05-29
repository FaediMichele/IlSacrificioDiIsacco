package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A JavaFx implementation of {@link AnimatedView}.
 */
public class AnimatedViewJavafx implements AnimatedView {
    private ImageView img;
    private List<Image> frames;
    private int index;

    /**
     * Set a ImageView to animate.
     * @param n the ImageView
     */
    @Override
    public void setNode(final Object n) {
        if (!(n instanceof ImageView)) {
            throw new IllegalArgumentException("Parameter must be a javafx ImageView");
        }
        img = (ImageView) n;
        index = 0;
        if (frames != null) {
            img.setImage(frames.get(0));
        }
    }

    /**
     * Set the frame for the {@link ImageView}.
     * @param frames javafx.scene.image.Image elements to pass through.
     */
    @Override
    public void setFrames(final Object... frames) {
        for (int i = 0; i < frames.length; i++) {
            if (!(frames[i] instanceof Image)) {
                throw new IllegalArgumentException("All parameter must be a javafx Image");
            }
        }
        if (this.frames == null) {
            this.frames = new ArrayList<Image>();
        }
        for (int i = 0; i < frames.length; i++) {
            this.frames.set(i, (Image) frames[i]);
        }
        if (img.getImage() == null) {
            img.setImage(this.frames.get(0));
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void next() {
        index = (index + 1) % frames.size() != 0 ? index + 1 : 0;
        img.setImage(frames.get(index));
    }
}
