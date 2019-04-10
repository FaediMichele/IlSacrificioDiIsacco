package model.component;

import model.entity.Entity;
import model.entity.events.FireHittedListener;
import model.entity.events.FireOutEvent;
import model.entity.events.FireOutListener;

/**
 * Implements the data for the fires.
 */
public class FireComponent extends AbstractComponent {

    private static final int MAX_LIFE = 4;
    private int lifeLeft;
    private final FireType fireType;

    /**
     * Initialize the entity.
     * 
     * @param entity   the source entity
     * @param fireType the {@link FireType}
     */
    public FireComponent(final Entity entity, final FireType fireType) {
        super(entity);
        this.lifeLeft = MAX_LIFE;
        this.fireType = fireType;
        this.getEntity().registerListener(new FireHittedListener((event) -> {
            changeLife(1);

            if (this.lifeLeft == 0) {
                this.getEntity().postEvent(
                        new FireOutEvent(event.getSourceEntity(), event.getSourceComponent(), this.fireType));
            }
        }));

        this.getEntity().registerListener(new FireOutListener((event) -> {
            final FireType type = event.getFireType();
            if (type == FireType.RED) {
                System.out.println("RED");
            } else if (type == FireType.BLUE) {
                System.out.println("BLUE");
            } else {
                System.out.println("Other");
            }
        }));
    }

    /**
     * 
     * @return the life left
     */
    public Integer getLife() {
        return this.lifeLeft;
    }

    /**
     * @return the fireType
     */
    public FireType getFireType() {
        return fireType;
    }

    /**
     * Sets the remaining life of the fire after taking damage.
     * 
     * @param lifeLost the life lost
     */
    private void changeLife(final Integer lifeLost) {
        this.lifeLeft = (lifeLost > this.lifeLeft) ? 0 : this.lifeLeft - lifeLost;
    }

    @Override
    public final boolean equals(final Object component) {
        if (!(component instanceof FireComponent)) {
            return false;
        }

        boolean equals = super.equals(component);
        final FireComponent other = (FireComponent) component;
        equals = equals && other.getLife().equals(this.getLife());
        equals = equals && other.getFireType() == this.getFireType();
        return equals;
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }
}
