package model.component;

import model.entity.Entity;

/**
 * Base AIComponent for the entities that needs to have their own behavior. 
 * This needs to be extended for each of these entities to implement their specific behavior.
 */
public class AbstractAIComponent extends AbstractComponent<AbstractAIComponent> {

    AbstractAIComponent(final Entity entity) {
        super(entity);
    }

}
