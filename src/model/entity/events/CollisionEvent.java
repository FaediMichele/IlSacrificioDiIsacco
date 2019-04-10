package model.entity.events;

import model.component.Component;
import model.entity.Entity;

public class CollisionEvent extends AbstractEvent{
    
    public CollisionEvent(Entity sourceEntity, Class<? extends Component> sourceComponent) {
        super(sourceEntity, sourceComponent);
        // TODO Auto-generated constructor stub
    }

}
