package model.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import model.enumeration.BasicStatusEnum;
import model.enumeration.EntityEnum;
import model.enumeration.MovementEnum;
import model.enumeration.StatusEnum;
import model.enumeration.UpgradeEnum;

/**
 *  Object for communication from model to controller. 
 */
public class EntityInformation {
    private UUID id;
    private EntityEnum entityName;
    private StatusEnum status;
    private Position position;
    private double height;
    private double width;
    private MovementEnum move;
    private Map<UpgradeEnum, List<Object>> upgrade;


    /**
     *  .
     */
    public EntityInformation() {
        super();
        this.status = BasicStatusEnum.DEFAULT;
        this.upgrade = new HashMap<UpgradeEnum, List<Object>>();
    }

    /**
     * 
     * @return id
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * 
     * @return entity
     */
    public EntityEnum getEntityName() {
        return this.entityName;
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
     * @param id 
     * @return this
     */
    public EntityInformation setId(final UUID id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @param entityName 
     * @return this
     */
    public EntityInformation setEntity(final EntityEnum entityName) {
        this.entityName = entityName;
        return this;
    }

    /**
     * 
     * @param status 
     * @return this
     */
    public EntityInformation setStatus(final StatusEnum status) {
        this.status = status;
        return this;
    }

    /**
     * 
     * @param position is positions.
     * @return this
     */
    public EntityInformation setPosition(final Position position) {
        this.position = position;
        return this;
    }

    /**
     * 
     * @param height is height
     * @return this
     */
    public EntityInformation setHeight(final double height) {
        this.height = height;
        return this;
    }

    /**
     * 
     * @param width 
     * @return this
     */ 
    public EntityInformation setWidth(final double width) {
        this.width = width;
        return this;
    }


    /**
     * 
     * @param move 
     * @return this
     */
    public EntityInformation setMove(final MovementEnum move) {
        this.move = move;
        return this;
    }

    /**
     * 
     * @param upgrade 
     * @return this
     */
    public EntityInformation setUpgrade(final Map<UpgradeEnum, List<Object>> upgrade) {
        this.upgrade = upgrade;
        return this;
    }
}
