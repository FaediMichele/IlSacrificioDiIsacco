package model.entity;

import model.component.Component;

/**
 * This is the interface for all entity (monster, player, rock, ...)
 * 
 * <p>See also {@link EntityImpl}
 * See also {@link Component}
 * 
 * @author Michele Faedi
 *
 */
public interface Entity {

  /**
   * Add a component to the entity. Adding behavior.
   * @param c the component to add.
   */
  void attach(Component c);

  /**
   * Remove a component from the entity. Remove behavior.
   * @param c the component type to remove.
   */
  void detach(Class<? extends Component> c);

  /**
   * Know if the entity have a component type already attached.
   * @param c the component type to search.
   * @return true if the entity have the component.
   */
  boolean has(Class<? extends Component> c);

  /**
   * Get the component from a component's type.
   * @param c the component to get.
   * @return return a {@link Component}.
   */
  Component get(Class<? extends Component> c);

  /**
   * Run all entity that needs to work frame to frame.
   * @param deltaTime difference in milliseconds from the last call.
   */
  void update(Double deltaTime);

  /**
   * Initialize all components currently attached.
   */
  void init();

  /**
   * Get the body of this entity.
   * @return the {@link BodyComponent}.
   */
  Component getBody();

  /**
   * Get the collision handler of this entity.
   * @return the {@link CollisionComponent}.
   */
  Component getCollision();
}
