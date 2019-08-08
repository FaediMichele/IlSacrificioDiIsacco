package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Bomb;
import model.entity.Entity;
import model.events.InputEvent;
import model.events.TearShotEvent;
import model.events.UseThingEvent;
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
    public InputComponent(final Entity entity) {
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
        //System.out.println(c + "entity");
        final MoveComponent mc = getEntity().getComponent(MoveComponent.class).get();
        if (mc == null) {
            return;
        }
        switch (c) {
            case KEY_DOWN:
                mc.move(270);
                break;
            case KEY_UP:
                mc.move(90);
                break;
            case KEY_RIGHT:
                mc.move(0);
                break;
            case KEY_LEFT:
                mc.move(180);
                break;
            case ARROW_DOWN:
                getEntity().postEvent(new TearShotEvent(getEntity(), 270));
            case ARROW_UP:
                getEntity().postEvent(new TearShotEvent(getEntity(), 90));
            case ARROW_LEFT:
                getEntity().postEvent(new TearShotEvent(getEntity(), 180));
            case ARROW_RIGHT:
                getEntity().postEvent(new TearShotEvent(getEntity(), 0));
            case BOMB:
                getEntity().postEvent(new UseThingEvent(getEntity(), Bomb.class));
                break;
        default:
            break;
        }
    }

}
