package view.javafx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
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
    private final List<EntityView> toAdd = new ArrayList<>();
    private final List<EntityView> toRemove = new ArrayList<>();
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
    @Override
    public void addEntity(final EntityView entity) {
        this.toAdd.add(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntity(final EntityView entity) {
        this.toRemove.add(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoomView(final RoomView room) {
        this.room = room;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatisticView> getStatistics() {
        return Collections.unmodifiableList(statistics);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStatistic(final StatisticView s) {
        Objects.requireNonNull(s);
        this.statistics.add(s);
        s.setIndex(statistics.indexOf(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeStatistic(final StatisticView s) {
        Objects.requireNonNull(s);
        this.statistics.remove(s);
        statistics.forEach(stat -> stat.setIndex(statistics.indexOf(stat)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInventoryStatistic(final StatisticView s, final double itemNumber) {
        Objects.requireNonNull(s);
        if (!this.statistics.contains(s)) {
            this.statistics.add(s);
        }
        s.setNumber(itemNumber);
    }

    /**
     * It draws all entities in the canvas.
     */
    @Override
    public void draw() {
        Platform.runLater(() -> {
            room.draw(cnv.getGraphicsContext2D());
            entities.stream().filter(e -> DoorView.class.isInstance(e)).forEach(e -> e.draw(cnv.getGraphicsContext2D()));
            entities.stream().filter(e -> !DoorView.class.isInstance(e)).forEach(e -> e.draw(cnv.getGraphicsContext2D()));
            //entities.stream().forEach(e -> System.out.println(e.getClass()));
            statistics.stream().forEach(s -> s.draw(cnv.getGraphicsContext2D()));
            System.out.println("/n");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeartsStatistic(final HeartStatisticView s, final List<Pair<HeartEnum, Double>> hearts) {
        Objects.requireNonNull(s);
        if (!this.statistics.contains(s)) {
            this.statistics.add(s);
        }
        s.setHearts(hearts);
    }

    /**
     * Clear the canvas and all the saved stuff.
     */
    @Override
    public void clear() {
        cnv.getGraphicsContext2D().fill();
        entities.clear();
    }

    @Override
    public final double getHeight() {
        return cnv.getHeight();
    }

    @Override
    public final double getWidth() {
        return cnv.getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEntity() {
        entities.addAll(toAdd);
        entities.removeAll(toRemove);
        toAdd.clear();
        toRemove.clear();
    }
}
