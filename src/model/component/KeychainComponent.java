package model.component;

import model.entity.Entity;

/**
 * 
 * This is the component that handles the keys.
 *
 */
public class KeychainComponent extends AbstractComponent<KeychainComponent> {

    private int numKey;

    KeychainComponent(final Entity entity) {
        super(entity);
    }
 
    KeychainComponent(final Entity entity, final KeychainComponent component) {
        super(entity, component);
    }

    /**
     * Add a key to the inventory.
     */
    protected void addKey() {
        numKey++;
    }

    /**
     * Get the key possessed.
     * @return the number of the key
     */
    protected int getNumKey() {
        return numKey;
    }

    /**
     * Remove a key from the key's inventory.
     */
    protected void removeKey() {
        if (numKey == 0) {
            throw new IllegalStateException("to remove a key you must have a key");
        }
        numKey--;
    }

}
