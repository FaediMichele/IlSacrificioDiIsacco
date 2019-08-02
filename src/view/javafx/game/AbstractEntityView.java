package view.javafx.game;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;

/**
 * This class contains the method animate that can be used by all the entity
 * view after having extracted the sprites from the sheet set the right list in
 * entityActualSprites.
 */
public abstract class AbstractEntityView implements EntityView {
    private Optional<GameView> gameView;
    private final UUID id;
    private Optional<Double> x = Optional.empty();
    private Optional<Double> y = Optional.empty();
    private Optional<Double> height = Optional.empty();
    private Optional<Double> width = Optional.empty();
    private Optional<BasicStatusEnum> status = Optional.empty();

    AbstractEntityView(final UUID id, final GameViewImpl gv) {
        Objects.requireNonNull(gv);
        this.id = id;
        this.gameView = Optional.of(gv);
    }

    AbstractEntityView(final UUID id) {
        this.id = id;
        this.gameView = Optional.empty();
    }



    /**
     * @return the GameView to which the entityView is attached (if there is one);
     */
    public Optional<GameView> getGameView() {
        return gameView;
    }

    /**
     * {@inheritDoc}
     */
    public void setGameView(final GameView gameView) {
        this.gameView = Optional.of(gameView);
    }

    /**
     * {@inheritDoc}
     */
    public Image resize(final Image image, final double height, final double width) {
        final ImageView resizedImage = new ImageView(image);
        resizedImage.setFitHeight(height);
        resizedImage.setFitWidth(width);
        return resizedImage.snapshot(null, null);
    }

    /**
     * {@inheritDoc}
     */
    public abstract void draw(GraphicsContext gc);

    /**
     * {@inheritDoc}
     */
    public double getX() {
        if (x.isPresent()) {
            return x.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    public EntityView setX(final double x) {
        this.x = Optional.of(x);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public double getY() {
        if (y.isPresent()) {
            return y.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    public EntityView setY(final double y) {
        this.y = Optional.of(y);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public double getHeight() {
        if (height.isPresent()) {
            return height.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    public EntityView setHeight(final double height) {
        this.height = Optional.of(height);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public double getWidth() {
        if (width.isPresent()) {
            return width.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    public EntityView setWidth(final double width) {
        this.width = Optional.of(width);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public Optional<BasicStatusEnum> getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    public EntityView setStatus(final BasicStatusEnum status) {
        this.status = Optional.of(status);
        return this;
    }

    @Override
    public void def(final MovementEnum move) {
    }

    @Override
    public void damaging(final MovementEnum move) {
    }

    @Override
    public void dead(final MovementEnum move) {
    }

    @Override
    public void upgrade(final Object... upgrades) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appear(final MovementEnum move) {
        this.getGameView().get().addEntity(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disappear(final MovementEnum move) {
        this.getGameView().get().removeEntity(this);
    }

    @Override
    public void exploded(final MovementEnum move) {
    }

    @Override
    public void pickUp(final MovementEnum move) {
    }

    @Override
    public void triggered(final MovementEnum move) {
    }

    @Override
    public void full(final MovementEnum move) {
    }

    @Override
    public void half(final MovementEnum move) {
    }

    /**
     * Get the UUID.
     * 
     * @return id
     */
    public UUID getId() {
        return this.id;
    }
}
