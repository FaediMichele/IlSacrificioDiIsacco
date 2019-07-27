package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

import util.BasicEntityInformation;
import util.enumeration.ValuesMapStatusEnum;
import view.javafx.game.EntityView;

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
    private static final Map<ValuesMapStatusEnum, String> STATUS_MAP = util.StaticMethodsUtils.xmlToMap(PATH_STATUS,
            TAG_STATUS, ATTR1_STATUS, ATTR2_STATUS);

    private static final String PATH_UPGRADE = "/xml/Upagrade.xml";
    private static final String TAG_UPGRADE = "Upgrade";
    private static final String ATTR1_UPGRADE = "";
    private static final String ATTR2_UPGRADE = "";
    private static final Map<ValuesMapStatusEnum, String> UPGADE_MAP = util.StaticMethodsUtils.xmlToMap(PATH_UPGRADE,
            TAG_UPGRADE, ATTR1_UPGRADE, ATTR2_UPGRADE);

    private final UUID id;
    private final EntityView entityView;

    @SuppressWarnings("unchecked")
    EntityController(final BasicEntityInformation info) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.id = info.getUUID();
        Class<EntityView> classEntity = (Class<EntityView>) ClassLoader.getSystemClassLoader().loadClass(ENTITY_MAP.get(info.getEntity()));
        Constructor<EntityView> constructor = classEntity.getConstructor(new Class[] { UUID.class });
        this.entityView = (EntityView) constructor.newInstance(new Object[] {id});
    }

    /**
     * 
     * @param info is the status for the status component of entity.
     */
    public void update(final BasicEntityInformation info) {

            this.entityView.setX(info.getPosition().getV1())
                            .setY(info.getPosition().getV2())
                            .setHeight(info.getHeight())
                            .setWidth(info.getWidth());
//        //update dello stato
//       try {
//        STATUS_MAP.get(status.).invoke(this.entityView, status.get(BasicKeyMapStatusEnum.MOVEMENT));
//    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//        e.printStackTrace();
//    }
    }
}
