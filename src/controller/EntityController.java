package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import test.EntityView;
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
    private static final String PATH_ENTITY = "/xml/Entity.xml";
    private static final String TAG_ENTITY = "Entity";
    private static final Map<ValuesMapStatusEnum, String> ENTITY_MAP = util.StaticMethodsUtils
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

    @SuppressWarnings("unchecked")
    EntityController(final Map<KeyMapStatusEnum, ValuesMapStatusEnum> status) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.id = ((BasicEntityID) status.get(BasicKeyMapStatusEnum.ID)).getValue();
        Class<EntityView> c = (Class<EntityView>) ClassLoader.getSystemClassLoader().loadClass(ENTITY_MAP.get(status.get(BasicKeyMapStatusEnum.ENTITY)));
        Constructor<EntityView> constructor = c.getConstructor(new Class[] { String.class });
        this.entityView = (EntityView) constructor.newInstance(new Object[] {id});

    }

    void update(final Map<KeyMapStatusEnum, ValuesMapStatusEnum> status) {

    }
}
