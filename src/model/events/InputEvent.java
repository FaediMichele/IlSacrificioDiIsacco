package model.events;

import java.util.PriorityQueue;

import model.entity.Entity;
import util.Command;

/**
 * TODO .
 *
 */
public class InputEvent extends AbstractEvent {
    private final PriorityQueue<Command> c;

    /**
     * TODO.
     * @param sourceEntity TODO.
     * @param c2 TODO
     */
    public InputEvent(final Entity sourceEntity, final PriorityQueue<Command> c2) {
        super(sourceEntity);
        this.c = c2;
    }

    /**
     * Get the command.
     * @return the commands
     */
    public PriorityQueue<Command> getCommands() {
        return c;
    }


}
