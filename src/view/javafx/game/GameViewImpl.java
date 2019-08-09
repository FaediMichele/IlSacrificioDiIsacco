package view.javafx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javafx.scene.canvas.Canvas;
import model.enumeration.HeartEnum;
import util.Pair;

/**
 * Class for the main view of the game, it contains a list of all entities to
 * draw.
 *
 */
public class GameViewImpl implements GameView {
    private final List<EntityView> entities = new ArrayList<>();
    private final List<StatisticView> statistics = new ArrayList<>();
    private RoomView room;
    private final Canvas cnv;

    /**
     * Create a new Game view with a canvas.
     * 
     * @param cnv the canvas to use
     */
    public GameViewImpl(final Canvas cnv) {
        this.cnv = cnv;
    }

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
     * {@inheritDoc}
     */
    public void setRoomView(final RoomView room) {
        this.room = room;
    }

    /**
     * {@inheritDoc}
     */
    public List<StatisticView> getStatistics() {
        return Collections.unmodifiableList(statistics);
    }

    /**
     * {@inheritDoc}
     */
    public void addStatistic(final StatisticView s) {
        Objects.requireNonNull(s);
        this.statistics.add(s);
        s.setIndex(statistics.indexOf(s));
    }

    /**
     * {@inheritDoc}
     */
    public void removeStatistic(final StatisticView s) {
        Objects.requireNonNull(s);
        this.statistics.remove(s);
        statistics.forEach(stat -> stat.setIndex(statistics.indexOf(stat)));
    }

    /**
     * {@inheritDoc}
     */
    public void setNumberStatistic(final StatisticView s, final double itemNumber) {
        Objects.requireNonNull(s);
        if (!this.statistics.contains(s)) {
            this.statistics.add(s);
        }
        s.setNumber(itemNumber);
    }

    /**
     * It draws all entities in the canvas.
     */
    public void draw() {
        room.draw(cnv.getGraphicsContext2D());
        entities.stream().filter(e -> e.getClass().isInstance(DoorView.class)).forEach(e -> e.draw(cnv.getGraphicsContext2D()));
        entities.stream().filter(e -> !e.getClass().isInstance(DoorView.class)).forEach(e -> e.draw(cnv.getGraphicsContext2D()));
        statistics.stream().forEach(s -> s.draw(cnv.getGraphicsContext2D()));
    }

    /**
     * {@inheritDoc}
     */
    public void setHeartsStatistic(final StatisticView s, final List<Pair<HeartEnum, Double>> hearts) {
        Objects.requireNonNull(s);
        if (!this.statistics.contains(s)) {
            this.statistics.add(s);
        }
        HeartStatisticView.class.cast(s).setHearts(hearts);
    }

    /**
     * Clear the canvas and all the saved stuff.
     */
    @Override
    public void clear() {
        cnv.getGraphicsContext2D().fill();
        entities.clear();
        statistics.clear();
    }

    @Override
    public final double getHeight() {
        return cnv.getHeight();
    }

    @Override
    public final double getWidth() {
        return cnv.getWidth();
    }
}
