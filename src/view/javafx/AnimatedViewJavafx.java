package view.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.AnimatedView;

/**
 * A JavaFx implementation of {@link AnimatedView}.
 */
public class AnimatedViewJavafx implements AnimatedView {
    private ImageView img;
    private List<Image> frames;
    private int index;

    /**
     * Create a empty {@link AnimatedViewJavafx}.
     */
    public AnimatedViewJavafx() {
        super();
    }

    /**
     * Create a {@link AnimatedViewJavafx} with a {@link ImageView}.
     * @param img the {@link ImageView} to use.
     */
    public AnimatedViewJavafx(final ImageView img) {
        this.img = Objects.requireNonNull(img);
    }

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
            this.frames.add((Image) frames[i]);
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
        index = ++index % frames.size();
        img.setImage(frames.get(index));
    }
}
