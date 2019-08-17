package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.DeadEvent;
import model.util.Position;
import util.EventListener;

/**
 * Component that release an entity when it dies.
 */
public class ReleaseOnDeathComponent extends AbstractComponent {

    /**
     * The entity to release when this entity dies.
     * @param me this entity.
     * @param e the entity to add.
     */
    public ReleaseOnDeathComponent(final Entity me, final Entity e) {
        super(me);
        registerListener(new EventListener<DeadEvent>() {
            @Subscribe
            @Override
            public void listenEvent(final DeadEvent event) {
                final Position pos = getBody().getPosition().getClone();
                pos.add(new Position(getBody().getWidth() / 2, getBody().getHeight() / 2, -getBody().getPosition().getZ()));
                pos.add(new Position(-getBody(e).getWidth() / 2, -getBody(e).getHeight() / 2, getBody(e).getPosition().getZ()));
                System.out.println(pos.getX() + " " + pos.getY() + " " + pos.getZ());
                e.getComponent(BodyComponent.class).get().setPosition(pos);
                addToRoom(e);
            }
        });
    }

    private BodyComponent getBody() {
        return getBody(this.getEntity());
    }
    private BodyComponent getBody(final Entity e) {
        return e.getComponent(BodyComponent.class).get();
    }

    private void addToRoom(final Entity e) {
        this.getEntity().getRoom().insertEntity(e);
    }
}
