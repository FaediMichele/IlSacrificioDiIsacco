package controller.menu;

import java.util.Set;

import util.Command;

/**
 * Menu selection that use input.
 * @param <T> the type of child.
 */
public class InputMenu<T> extends MenuSelectionImpl<T> {
    /**
     * Pass the input in the sub menu that is inputMenu.
     * @param commands the active input.
     */
    public void input(final Set<Command> commands) {
        asStream().filter(InputMenu .class::isInstance)
        .map(c -> (InputMenu<?>) c)
        .peek(i -> i.input(commands));
    }
}
