package model.entity;

import util.enumeration.BasicPlayerEnum;
import util.enumeration.EntityEnum;

/**
 * TODO.
 *
 */
public class Isaac extends Player {
    private static final EntityEnum ENTITY_NAME = BasicPlayerEnum.ISAAC;

    /**
     * TODO.
     */
    public Isaac() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
