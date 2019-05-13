package model.component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.entity.Door;
import model.entity.Entity;

/**
 * 
 * This is the component that handles the keys.
 *
 */
public class KeychainComponent extends AbstractComponent<KeychainComponent> {

    private Set<Door> doors;

    KeychainComponent(final Entity entity) {
        super(entity);
        this.doors = new HashSet<Door>();

    }
 
    KeychainComponent(final Entity entity, final KeychainComponent component) {
        super(entity, component);
    }
    /**
     * 
     * @param door is the door that can open the key that wants to be added.
     * @return returns false if it cannot add the key returns true if it can add the key.
     */
    protected boolean addKey(final Door door) {
        this.doors.add(door);
        return true;
    }

    /**
     * @param door the door you want to query
     * @return true if it contains the key that opens the false door otherwise.
     */
    public boolean containsKey(final Door door) {
        return this.doors.contains(door);
    }

    /**
     * 
     * @return the doors he can open.
     */
    protected Set<Door> getKey() {
        return Collections.unmodifiableSet(this.doors);
    }

}
