package controller;

import java.util.Map;

import test.EntityView;
import test.EntityViewImplements;
import util.enumeration.BasicEntityID;
import util.enumeration.BasicKeyMapStatusEnum;
import util.enumeration.BasicMovementEnum;
import util.enumeration.KeyMapStatusEnum;
import util.enumeration.ValuesMapStatusEnum;

/**
 * 
 * loads the information from the various entities of the model and calls the
 * various functions of the view.
 *
 */
public class EntityController {
    private static final String PATH_ENTITY = "/xml/Entity.xml";
    private static final String TAG_ENTITY = "Entity";
    private static final Map<KeyMapStatusEnum, Class<? extends EntityView>> ENTITY_MAP = util.StaticMethodsUtils
            .xmlToMap(PATH_ENTITY, TAG_ENTITY);

    private static final String PATH_STATUS = "/xml/Status.xml";
    private static final String TAG_STATUS = "Status";
    private static final Map<KeyMapStatusEnum, String> STATUS_MAP = util.StaticMethodsUtils.xmlToMap(PATH_STATUS,
            TAG_STATUS);

    private static final String PATH_UPGRADE = "/xml/Upagrade.xml";
    private static final String TAG_UPGRADE = "Upgrade";
    private static final Map<KeyMapStatusEnum, String> UPGADE_MAP = util.StaticMethodsUtils.xmlToMap(PATH_UPGRADE,
            TAG_UPGRADE);

    private final String id;
    private final EntityView entityView;

    EntityController(final Map<KeyMapStatusEnum, ValuesMapStatusEnum> status) {
        this.id = ((BasicEntityID) status.get(BasicKeyMapStatusEnum.ID)).getValue();
        this.entityView = new EntityViewImplements(this.id);
    }

    void update(final Map<KeyMapStatusEnum, ValuesMapStatusEnum> status) {

    }
}
