package model.entity.events;

import model.component.Component;
import model.entity.Entity;

/**
 * Base interface for all the events.
 */
public interface Event {
  /**
   * Returns the {@link Entity} who triggered the event.
   * @return the entity
   */
  Entity getSourceEntity();

  /**
   * Returns the type of {@link Component} who triggered the event.
   * @return the type of {@link Component}
   */
  Class<? extends Component> getSourceComponent();
}
