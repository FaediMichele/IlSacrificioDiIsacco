package model.component;

import java.util.HashMap;
import java.util.Map;
import model.entity.Entity;
import util.enumeration.BasicEntityID;
import util.enumeration.BasicKeyMapStatusEnum;
import util.enumeration.KeyMapStatusEnum;
import util.enumeration.ValuesMapStatusEnum;

/**
 * The component for the status of all entities.
 */
public class StatusComponent extends AbstractComponent<StatusComponent> {
    private Map<KeyMapStatusEnum, ValuesMapStatusEnum> status;

    /**
     * 
     * @param entity the {@link Entity}
     */
    public StatusComponent(final Entity entity) {
        super(entity);
        this.init();
    }

    /**
     * 
     * @return the string list
     */
    public Map<KeyMapStatusEnum, ValuesMapStatusEnum> getStatus() {
        Map<KeyMapStatusEnum, ValuesMapStatusEnum> aux = status;
        this.status.clear();
        return aux;
    }

    /**
     * 
     * @param key for map read of Controller:
     * @param value for map read of Controller, the value must be in an interface that has been extended by the key.
     */
     public void setStatus(final KeyMapStatusEnum key, final ValuesMapStatusEnum value) {
        this.status.put(key, value);
    }

     private void init() {
         this.status = new HashMap<>();
         this.status.put(BasicKeyMapStatusEnum.ID, new BasicEntityID(getEntity().getUUID()));
     }
}
