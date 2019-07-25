package model.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entity.Entity;
import util.Pair;
import util.enumeration.KeyMapStatusEnum;
import util.enumeration.ValuesMapStatusEnum;

/**
 * The component for the status of all entities.
 */
public class StatusComponent extends AbstractComponent<StatusComponent> {
    private final List<String> statusQueue;
    private Map<KeyMapStatusEnum, ValuesMapStatusEnum> status;

    /**
     * 
     * @param entity the {@link Entity}
     */
    public StatusComponent(final Entity entity) {
        super(entity);
        this.statusQueue = new ArrayList<String>();
        this.status = new HashMap<>();
    }

    /**
     * 
     * @return the string list
     */
    public Map<KeyMapStatusEnum, ValuesMapStatusEnum> getStatus() {
//        return Collections.unmodifiableList(new ArrayList<String>(
//                this.statusQueue.stream().flatMap(s -> Stream.of(s.getY())).collect(Collectors.toList())));
        //return Collections.unmodifiableList(statusQueue);
        Map<KeyMapStatusEnum, ValuesMapStatusEnum> aux = status;
        this.status = new HashMap<>();
        return aux;
    }

    /**
     * 
     * @param newStatus the new Status to add
     */
    public void setStatus(final Pair<Integer, String> newStatus) {
        this.statusQueue.add(newStatus.getY());
    }

    /**
     * 
     * @param key for map read of Controller:
     * @param value for map read of Controller.
     */
    public void setStatus(final KeyMapStatusEnum key, final ValuesMapStatusEnum value) {
        this.status.put(key, value);
    }
}
