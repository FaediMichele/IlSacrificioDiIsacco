package model.component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.entity.Entity;

/**
 * Generic fields and methods needed by each component.
 *
 */

public abstract class AbstractComponent implements Component {
    private boolean active;
    private Entity entity;
    private final Optional<Component> componentReplaced;

    AbstractComponent(final Entity entity) {
        this.entity = entity;
        this.componentReplaced = Optional.empty();
    }

    /**
     * Check if the component is active, this has to be done before each update
     * call.
     * 
     * @return {@link Boolean}.
     */
    protected final boolean isActive() {
        return active;
    }

    /**
     * Enable or disable a component by setting a variable.
     * 
     * @param state {@link Boolean}.
     */
    protected final void setState(final boolean state) {
        active = state;
    }

    /**
     * Get the entity this component is attached to.
     * 
     * @return {@link Entity}.
     */
    protected final Entity getEntity() {
        return entity;
    }

    /**
     * Sets the entity this component has to be attached to.
     * 
     * @param e {@link Entity}
     */
    protected final void setEntity(final Entity e) {
        if (entity != null) {
            throw new IllegalStateException();
        }
        entity = e;
    }

    /**
     * Release all resources used by this component.
     */
    protected final void dispose() {
        active = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {

    }

    /**
     * Returns the hash code of the {@link Component}.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        final int second = 1231;
        final int third = 1237;
        int result = 1;
        result = prime * result + (active ? second : third);
        result = prime * result + (!(componentReplaced.isPresent()) ? 0 : componentReplaced.get().hashCode());
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        return result;
    }

    /**
     * Checks if two {@link Component} are equals.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractComponent other = (AbstractComponent) obj;

        if (active != other.active) {
            return false;
        }
        if (componentReplaced == null) {
            if (other.componentReplaced != null) {
                return false;
            }
        } else if (!componentReplaced.equals(other.componentReplaced)) {
            return false;
        }
        if (entity == null) {
            return other.entity == null;
        }

        // Gets the instance of the Component in the entity and the runtime classes for
        // both Components
        final Component entityComponent = this;
        final Class<? extends Object> cObj = obj.getClass();
        final Class<? extends Component> cEntityComponent = entityComponent.getClass();

        // By using Stream and Reflection it gets the list of private fields of the two
        // Components
        final List<Field> fieldsObj = Stream.of(cObj.getDeclaredFields())
                .filter(f -> Modifier.isPrivate(f.getModifiers())).collect(Collectors.toList());
        final List<Field> fieldsEntityComponent = Stream.of(cEntityComponent.getDeclaredFields())
                .filter(f -> Modifier.isPrivate(f.getModifiers())).collect(Collectors.toList());

        // Via Stream and Reflection it gets the list of the private field values
        final List<Object> fieldsObjValues = fieldsObj.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                Stream<Object> s = Stream.of(f.get(obj));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        final List<Object> fieldsEntityComponentValues = fieldsEntityComponent.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                Stream<Object> s = Stream.of(f.get(entityComponent));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        // True if they have the same Fields and the same values
        return fieldsObj.equals(fieldsEntityComponent) && fieldsObjValues.equals(fieldsEntityComponentValues);
    }
}
