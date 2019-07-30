package model.events;

import model.entity.Entity;
import view.Command;

/**
 * TODO .
 *
 */
public class InputEvent extends AbstractEvent {
    private final Command c;

    /**
     * TODO.
     * @param sourceEntity TODO.
     * @param c TODO
     */
    public InputEvent(final Entity sourceEntity, final Command c) {
        super(sourceEntity);
        this.c = c;
    }

    /**
     * Get the command.
     * @return the command
     */
    Command getCommand() {
        return c;
    }


}
