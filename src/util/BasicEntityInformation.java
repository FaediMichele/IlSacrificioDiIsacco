package util;

import java.util.List;
import java.util.UUID;
import util.enumeration.BasicUpgradeEnum;
import util.enumeration.EntityEnum;
import util.enumeration.MovementEnum;
import util.enumeration.StatusEnum;

/**
 *  Object for communication from model to controller. 
 */
public class BasicEntityInformation {
    private final UUID uuid;
    private final EntityEnum entity;
    private StatusEnum status;
    private Triplet<Double, Double, Double> position;
    private double height;
    private double width;
    private List<BasicUpgradeEnum> upgrade;
    private MovementEnum move;



    /**
     *  .
     * @param uuid 
     * @param entity 
     */
    public BasicEntityInformation(final UUID uuid, final EntityEnum entity) {
        super();
        this.uuid = uuid;
        this.entity = entity;
    }

    /**
     * 
     * @return entity
     */
    public EntityEnum getEntity() {
        return entity;
    }

    /**
     * 
     * @return id
     */
    public UUID getUUID() {
        return this.uuid;
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
     * @return position
     */
    public Triplet<Double, Double, Double> getPosition() {
        return position;
    }

    /**
     * 
     * @param position 
     */
    public void setPosition(final Triplet<Double, Double, Double> position) {
        this.position = position;
    }

    /**
     * 
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * 
     * @param height 
     */
    public void setHeight(final double height) {
        this.height = height;
    }

    /**
     * 
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * 
     * @param width 
     */ 
    public void setWidth(final double width) {
        this.width = width;
    }
    /**
     * 
     * @return upgrade
     */
    public List<BasicUpgradeEnum> getUpgrade() {
        return upgrade;
    }

    /**
     * 
     * @param upgrade 
     */
    public void setUpgrade(final List<BasicUpgradeEnum> upgrade) {
        this.upgrade = upgrade;
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
