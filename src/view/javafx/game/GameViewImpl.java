package view.javafx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.canvas.Canvas;

/**
 * Class for the main view of the game, it contains a list of all entities to
 * draw.
 *
 */
public class GameViewImpl implements GameView {
    private final List<EntityView> entities = new ArrayList<>();
    private final List<StatisticView> statistics = new ArrayList<>();
    private Optional<Canvas> cv = Optional.empty();

    /**
     * {@inheritDoc}
     */
    public void addEntity(final EntityView entity) {
        this.entities.add(entity);
    }

    /**
     * {@inheritDoc}
     */
    public void removeEntity(final EntityView entity) {
        this.entities.remove(entity);
    }

    /**
     * @return a Copy of the statistics list
     */
    public List<StatisticView> getStatistics() {
        return Collections.unmodifiableList(statistics);
    }

    /**
     * {@inheritDoc}
     */
    public void addStatistic(final StatisticView s) {
        this.statistics.add(s);
        s.setIndex(statistics.indexOf(s));
    }

    /**
     * {@inheritDoc}
     */
    public void removeStatistic(final StatisticView s) {
        this.statistics.remove(s);
        statistics.forEach(stat -> stat.setIndex(statistics.indexOf(stat)));
    }

    /**
     * {@inheritDoc}
     */
    public void setStatisticNumber(final StatisticView s, final double itemNumber) {
        Objects.requireNonNull(s);
        if (!this.statistics.contains(s)) {
            this.statistics.add(s);
        } 
        s.setNumber(itemNumber);
    }

    /**
     * {@inheritDoc}
     */
    public void setCanvas(final Canvas cv) {
        Objects.requireNonNull(cv);
        this.cv = Optional.of(cv);
    }

    /**
     * It draws all entities in the canvas.
     */
    public void draw() {
        if (cv.isPresent()) {
            entities.stream().forEach(e -> e.draw(cv.get().getGraphicsContext2D()));
            statistics.stream().forEach(s -> s.draw(cv.get().getGraphicsContext2D()));
        } else {
            throw new IllegalStateException();
        }
    }
}
