package controller;

import java.util.Map;

import util.enumeration.BasicEntityID;
import util.enumeration.BasicKeyMapStatusEnum;
import util.enumeration.KeyMapStatusEnum;
import util.enumeration.ValuesMapStatusEnum;

/**
 * 
 * loads the information from the various entities of the model and calls the
 * various functions of the view.
 *
 */
public class EntityController {
    //private Map<KeyMapStatusEnum,Class> ENTITY_MAP ;
    private final BasicEntityID id;

    EntityController(final Map<KeyMapStatusEnum, ValuesMapStatusEnum> status) {
        this.id = (BasicEntityID) status.get(BasicKeyMapStatusEnum.ID);
    }
}
