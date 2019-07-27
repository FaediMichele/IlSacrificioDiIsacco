package model.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.entity.Entity;
import util.enumeration.BasicStatusEnum;
import util.enumeration.MovementEnum;
import util.enumeration.StatusEnum;
import util.enumeration.UpgradeEnum;

/**
 * The component for the status of all entities.
 */
public class StatusComponent extends AbstractComponent<StatusComponent> {

    private StatusEnum status;
    private List<UpgradeEnum> upgrade;
    private MovementEnum move;

    /**
     * 
     * @param entity is {@link Entity} of component.
     */
    public StatusComponent(final Entity entity) {
        super(entity);
        this.upgrade = new ArrayList<UpgradeEnum>();
        this.reset();
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
    public List<UpgradeEnum> getUpgrade() {
        return Collections.unmodifiableList(upgrade);
    }

    /**
     * 
     * @param upgrade 
     */
    public void addUpgrade(final UpgradeEnum upgrade) {
        this.upgrade.add(upgrade);
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
    /**
     *  is reset.
     */
    public void reset() {
        this.status = BasicStatusEnum.DEFAULT;
    }
}
