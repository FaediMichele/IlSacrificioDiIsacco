package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import test.EntityView;
import util.enumeration.BasicEntityID;
import util.enumeration.BasicKeyMapStatusEnum;
import util.enumeration.BasicStatusEnum;
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
    private static final String ATTR1_ENTITY = "";
    private static final String ATTR2_ENTITY = "";
    private static final Map<ValuesMapStatusEnum, String> ENTITY_MAP = util.StaticMethodsUtils
            .xmlToMap(PATH_ENTITY, TAG_ENTITY, ATTR1_ENTITY, ATTR2_ENTITY);

    private static final String PATH_STATUS = "/xml/Status.xml";
    private static final String TAG_STATUS = "Status";
    private static final String ATTR1_STATUS = "";
    private static final String ATTR2_STATUS = "";
    private static final Map<ValuesMapStatusEnum, Method> STATUS_MAP = util.StaticMethodsUtils.xmlToMap(PATH_STATUS,
            TAG_STATUS, ATTR1_STATUS, ATTR2_STATUS);

    private static final String PATH_UPGRADE = "/xml/Upagrade.xml";
    private static final String TAG_UPGRADE = "Upgrade";
    private static final String ATTR1_UPGRADE = "";
    private static final String ATTR2_UPGRADE = "";
    private static final Map<ValuesMapStatusEnum, Method> UPGADE_MAP = util.StaticMethodsUtils.xmlToMap(PATH_UPGRADE,
            TAG_UPGRADE, ATTR1_UPGRADE, ATTR2_UPGRADE);

    private final String id;
    private final EntityView entityView;

    @SuppressWarnings("unchecked")
    EntityController(final Map<KeyMapStatusEnum, ValuesMapStatusEnum> status) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.id = ((BasicEntityID) status.get(BasicKeyMapStatusEnum.ID)).getValue();
        Class<EntityView> c = (Class<EntityView>) ClassLoader.getSystemClassLoader().loadClass(ENTITY_MAP.get(status.get(BasicKeyMapStatusEnum.ENTITY)));
        Constructor<EntityView> constructor = c.getConstructor(new Class[] { String.class });
        this.entityView = (EntityView) constructor.newInstance(new Object[] {id});

    }

    /**
     * 
     * @param status is the status for the status component of entity.
     */
    public void update(final Map<KeyMapStatusEnum, ValuesMapStatusEnum> status) {
        //update delle dimensioni
        //update dello stato
       try {
        STATUS_MAP.get(status.get(BasicKeyMapStatusEnum.STATUS)).invoke(this.entityView, status.get(BasicKeyMapStatusEnum.MOVEMENT));
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
    }
    }
}
