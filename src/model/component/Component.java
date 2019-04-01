package model.component;

import model.Entity;

/**
 * This class represent a behavior for an Entity.
 * 
 * <p>See also {@link Entity}
 * @author Michele Faedi
 *
 */
public interface Component {
  
  /**
   * A component disabled shouldn't use the update call.
   * @return {@link Boolean}.
   */
  boolean isActive();
  
  /**
   * Enable or disable a component. This does not prevent the component from the update.
   * @param b {@link Boolean}.
   */
  void setState(boolean b);
  
  /**
   * Update this component. Should be called each frame.
   * @param deltaTime time in milliseconds that has passed from the last call.
   */
  void update(Double deltaTime);
  
  
  /**
   * Get the entity from this component is attached.
   * @return {@link Entity}.
   */
  Entity getEntity();
  
  /**
   * Initialize this component after all components has been attached. 
   */
  void init();
  
  /**
   * Release all resources used for this component.
   */
  void dispose();
}