package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.InputEvent;
import util.Command;
import util.EventListener;

/**
 * Component used for the input.
 *
 */
public class InputComponent extends AbstractComponent<InputComponent> {

    /**
     * Create a new inputComponent which control the entity.
     * @param entity the entity.
     */
    protected InputComponent(final Entity entity) {
        super(entity);
        this.registerListener((new EventListener<InputEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final InputEvent event) {
                handleInput(event.getCommand());
            }
        }));
    }

    private void handleInput(final Command c) {
        MoveComponent mc = getEntity().getComponent(MoveComponent.class).get();
        if (mc == null) {
            return;
        }
        switch (c) {
            case KEY_DOWN:
                break;
            case KEY_UP:
                break;
            case KEY_RIGHT:
                break;
            case KEY_LEFT:
                break;
            case ARROW_DOWN:
                break;
            case ARROW_UP:
                break;
            case ARROW_LEFT:
                break;
            case ARROW_RIGHT:
                break;
            case BOMB:
                break;
        default:
            break;
        }
    }

}
