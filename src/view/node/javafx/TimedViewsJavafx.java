package view.node.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import view.AnimatedView;
import view.TimedViews;

/**
 * A JavaFx implementation of {@link TimedViews}.
 */
public final class TimedViewsJavafx implements TimedViews {
    private List<AnimatedView> vs;
    private Timeline timer;

    @Override
    public void add(final AnimatedView... vs) {
        if (this.vs == null) {
            this.vs = new ArrayList<>(vs.length);
        }
        for (int i = 0; i < vs.length; i++) {
            this.vs.add(vs[i]);
        }
    }

    @Override
    public boolean contains(final AnimatedView v) {
        return vs.contains(v);
    }

    @Override
    public void setMilliseconds(final long ms) {
        if (timer == null) {
            timer =  new Timeline(new KeyFrame(
                    Duration.millis(ms),
                    ae -> vs.forEach(v -> v.next())));
            timer.setCycleCount(Animation.INDEFINITE);
        } else {
        timer.setDelay(Duration.millis(ms));
        }
    }

    @Override
    public void start() {
        if (timer == null) {
            throw new IllegalStateException("Duration must be set");
        }
        timer.playFromStart();
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

}
