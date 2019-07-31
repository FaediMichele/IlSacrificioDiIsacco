package model.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entity.Entity;
import model.enumeration.MovementEnum;
import model.enumeration.StatusEnum;
import model.enumeration.UpgradeEnum;

/**
 * The component for the status of all entities.
 */
public class StatusComponent extends AbstractComponent<StatusComponent> {

    private StatusEnum status;
    private Map<UpgradeEnum, List<Object>> upgrade;
    private MovementEnum move;

    /**
     * 
     * @param entity is {@link Entity} of component.
     */
    public StatusComponent(final Entity entity) {
        super(entity);
        this.upgrade = new HashMap<UpgradeEnum, List<Object>>();
    }

    /**
     * 
     * @return status
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(final StatusEnum status) {
        this.status = status;
    }

    /**
     * 
     * @return  upgrade
     */
    public Map<UpgradeEnum, List<Object>> getUpgrade() {
        return Collections.unmodifiableMap(this.upgrade);
    }

    /**
     * 
     * @param upgrade 
     * @param args 
     */
    public void addUpgrade(final UpgradeEnum upgrade, final Object... args) {
        this.upgrade.put(upgrade, Arrays.asList(args));
    }

    /**
     * 
     * @param upgrade 
     */ 
    public void deleteUpgrade(final UpgradeEnum upgrade) {
        this.upgrade.remove(upgrade);
    }

    /**
     * 
     */ 
    public void deleteAllUpgrade() {
        this.upgrade = new HashMap<UpgradeEnum, List<Object>>();
    }

    /**
     * 
     * @return move
     */
    public MovementEnum getMove() {
        return move;
    }

    /**
     * 
     * @param move 
     */
    public void setMove(final MovementEnum move) {
        this.move = move;
    }
}
