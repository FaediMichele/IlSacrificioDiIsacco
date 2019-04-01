package model.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import model.component.Component;

import com.google.common.eventbus.EventBus;

/**
 * The base class for all the entities.
 * See also {@link Entity}
 */
public abstract class AbstractEntity implements Entity {
  private final EventBus eventBus = new EventBus();
  private final Map<Class<? extends Component>, Component> componentsMap = new LinkedHashMap<>();
  private final Component entityPosition;
  private final Component entityCollision;

  /**
   * Initialize the {@link PositionComponent} and the {@link CollisionComponent}.
   * @param entityPosition {@link PositionComponent}
   * @param entityCollision {@link CollisionComponent}
   */
  public AbstractEntity(final Component entityPosition, final Component entityCollision) {
      super();
      this.entityPosition = entityPosition;
      this.entityCollision = entityCollision;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void attach(final Component c) {
      this.componentsMap.put(c.getClass(), c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void detach(final Component c) {
      this.componentsMap.remove(c.getClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register(final Object eventListener) {
      this.eventBus.register(eventListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unregister(final Object eventListener) {
      this.eventBus.unregister(eventListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void post(final Object event) {
      this.eventBus.post(event);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final double deltaTime) {
      this.componentsMap.forEach((k, v) -> v.update(deltaTime));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean has(final Class<? extends Component> c) {
      return this.componentsMap.containsKey(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Component> get(final Class<? extends Component> c) {
      if (has(c)) {
          return Optional.of(this.componentsMap.get(c));
      } else {
          return Optional.empty();
      }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Component getPosition() {
      return this.entityPosition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Component getCollision() {
      return this.entityCollision;
  }

}
