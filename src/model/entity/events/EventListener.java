package model.entity.events;

/**
 * Base interface for all the listeners.
 * @param <E> the generic type for all the events
 */
public interface EventListener<E extends Event> {
  /**
   * Base function for all the events listener.
   * @param event the event
   */
  void listenEvent(E event);
}
