package model.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.entity.Entity;
import util.Pair;

/**
 * The component for the status of all entities.
 */
public class StatusComponent extends AbstractComponent<StatusComponent> {
    private final List<String> statusQueue;

    /**
     * 
     * @param entity the {@link Entity}
     */
    public StatusComponent(final Entity entity) {
        super(entity);
        this.statusQueue = new ArrayList<String>();
    }

    /**
     * 
     * @return the string list
     */
    public List<String> getStatus() {
//        return Collections.unmodifiableList(new ArrayList<String>(
//                this.statusQueue.stream().flatMap(s -> Stream.of(s.getY())).collect(Collectors.toList())));
        return Collections.unmodifiableList(statusQueue);
    }

    /**
     * 
     * @param newStatus the new Status to add
     */
    public void setStatus(final Pair<Integer, String> newStatus) {
        this.statusQueue.add(newStatus.getY());
    }
}
