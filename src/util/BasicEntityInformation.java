package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.text.Position;

import util.enumeration.UpgradeEnum;
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
    private Position position;
    private double height;
    private double width;
    private MovementEnum move;
    private Map<UpgradeEnum, List<Object>> upgrade;


    /**
     *  .
     * @param uuid 
     * @param entity 
     */
    public BasicEntityInformation(final UUID uuid, final EntityEnum entity) {
        super();
        this.uuid = uuid;
        this.entity = entity;
        this.upgrade = new HashMap<UpgradeEnum, List<Object>>();
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
     * @return entity
     */
    public EntityEnum getEntity() {
        return entity;
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
     * @return {@link Position} 
     */
    public Position getPosition() {
        return this.position;
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
     * @return width
     */
    public double getWidth() {
        return width;
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
     * @return upgrade 
     */
    public Map<UpgradeEnum, List<Object>> getUpgrade() {
        return Collections.unmodifiableMap(this.upgrade);
    }


//-------setters
    /**
     * 
     * @param status 
     * @return this
     */
    public BasicEntityInformation setStatus(final StatusEnum status) {
        this.status = status;
        return this;
    }


    /**
     * 
     * @param position is positions.
     * @return this
     */
    public BasicEntityInformation setPosition(final Position position) {
        this.position = position;
        return this;
    }

    /**
     * 
     * @param height is height
     * @return this
     */
    public BasicEntityInformation setHeight(final double height) {
        this.height = height;
        return this;
    }

    /**
     * 
     * @param width 
     * @return this
     */ 
    public BasicEntityInformation setWidth(final double width) {
        this.width = width;
        return this;
    }


    /**
     * 
     * @param move 
     * @return this
     */
    public BasicEntityInformation setMove(final MovementEnum move) {
        this.move = move;
        return this;
    }

    /**
     * 
     * @param upgrade 
     * @return this
     */
    public BasicEntityInformation setUpgrade(final Map<UpgradeEnum, List<Object>> upgrade) {
        this.upgrade = upgrade;
        return this;
    }
}
