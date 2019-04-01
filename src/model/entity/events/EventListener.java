package model.entity.events;

import com.google.common.eventbus.Subscribe;

/**
 * Base interface for all the listeners.
 */
public interface EventListener {
  /**
   * Base function for all the events.
   * @param event the event
   */
  @Subscribe
  void listenEvent(Event event);
}
