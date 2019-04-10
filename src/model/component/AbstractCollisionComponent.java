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
//            Mentality myMentality = Mentality.NEUTRAL;
//            Mentality oposedMentality = Mentality.NEUTRAL;
//            if (event.getSourceEntity().hasComponent(MentalityComponent.class)) {
//                oposedMentality = ((MentalityComponent) this.getEntity().getComponent(MentalityComponent.class).get()).getMentality();
//            }
//            if (event.getSourceEntity().hasComponent(MentalityComponent.class)) {
//                oposedMentality = ((MentalityComponent) event.getSourceEntity().getComponent(MentalityComponent.class).get()).getMentality();
//            }
//
//            if ((myMentality.equals(Mentality.EVIL) && oposedMentality.equals(Mentality.GOOD)) 
//                || (myMentality.equals(Mentality.GOOD) && oposedMentality.equals(Mentality.EVIL)) 
//                || oposedMentality.equals(Mentality.PSYCHO)) {
//
//            }
       }));
     }
}
