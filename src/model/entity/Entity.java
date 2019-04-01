package model.entity;

import java.util.Optional;
import model.component.Component;

/**
 * The main interface for all the entities such as enemies, items and the player itself.
 */
public interface Entity {
  /**
   * Attaches a {@link Component} to the Entity to describe its behavior.
   * @param c {@link Component} to attach to the Entity
   */
  void attach(Component c);

  /**
   * Detaches a {@link Component} from the Entity.
   * @param c {@link Component} to detach from the Entity
   */
  void detach(Component c);

  /**
   * Register the listener on the EventBus for the event objects.
   * @param eventListener the listener
   */
  void register(Object eventListener);

  /**
   * Unregister the listener on the EventBus.
   * @param eventListener the listener
   */
  void unregister(Object eventListener);

  /**
   * Trigger the event.
   * @param event the event
   */
  void post(Object event);

  /**
   * Updates the Entity and all of its {@link Component}.
   * @param deltaTime time passed from last update
   */
  void update(double deltaTime);

  /**
   * Checks if the Entity has a certain kind of {@link Component}. 
   * @param c {@link Component} to search
   * @return true if the entity has the {@link Component} else false
   */
  boolean has(Class<? extends Component> c);

  /**
   * Gets the certain kind of {@link Component} from Entity. 
   * @param c {@link Component} to search
   * @return the {@link Component}
   */
  Optional<Component> get(Class<? extends Component> c);

  /**
   * Gets the current {@link PositionComponent} of the Entity.
   * @return the {@link PositionComponent} of the Entity
   */
  Component getPosition();

  /**
   * Gets the {@link CollisionComponent} of the Entity.
   * @return the {@link CollisionComponent}
   */
  Component getCollision();
}
