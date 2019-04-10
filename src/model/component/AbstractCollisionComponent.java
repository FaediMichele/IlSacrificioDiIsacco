package model.component;



import model.entity.Entity;
import model.entity.events.CollisionListener;

/**
 * This class manages the collision of this entity with the others.
 *
 */
public abstract class AbstractCollisionComponent extends AbstractComponent {

    AbstractCollisionComponent(final Entity entity) {
        super(entity);
        entity.registerListener(new CollisionListener((event) -> {
            //switch (event.getSourceEntity().getComponents().stream().filter(c->c IsInstanceOf (MentalityComponent))){
            //}
        }));
    }
}
