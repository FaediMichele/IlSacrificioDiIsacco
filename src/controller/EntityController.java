package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import model.util.EntityInformation;
import util.enumeration.EntityEnum;
import util.enumeration.MovementEnum;
import util.enumeration.UpgradeEnum;
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
    private static final String ATTR1_ENTITY = "path-entityEnum";
    private static final String ATTR2_ENTITY = "path-entityView";
    private static final String PATH_PLAYER = "/xml/Entity.xml";
    private static final String TAG_PLAYER = "Player";
    private static final String ATTR1_PLAYER = "path-playerEnum";
    private static final String ATTR2_PLAYER = "path-entityView";
    private static final Map<ValuesMapStatusEnum, String> ENTITY_MAP = EntityController.initializeMap();

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

//    private final UUID id;
//    private final EntityView entityView;
//    private final EntityEnum entityName;
//
////    /**
////     * 
////     * @param info 
////     * @throws ClassNotFoundException 
////     * @throws NoSuchMethodException 
////     * @throws SecurityException 
////     * @throws InstantiationException 
////     * @throws IllegalAccessException 
////     * @throws IllegalArgumentException 
////     * @throws InvocationTargetException 
////     */
////    @SuppressWarnings("unchecked")
////    public EntityController(final EntityInformation info) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
////        this.id = info.getId();
////        this.entityName = info.getEntityName();
////        final Class<EntityView> classEntity = (Class<EntityView>) ClassLoader.getSystemClassLoader().loadClass(ENTITY_MAP.get(this.entityName));
////        final Constructor<EntityView> constructor = classEntity.getConstructor(new Class[] { UUID.class });
////        this.entityView = (EntityView) constructor.newInstance(new Object[] {id});
////    }
//
//    /**
//     * 
//     * @param info is the status for the status component of entity.
//     */
//    public void update(final EntityInformation info) {
//
//            this.entityView.setX(info.getPosition().getX())
//                            .setY(info.getPosition().getY())
//                            .setHeight(info.getHeight())
//                            .setWidth(info.getWidth());
//            try {
//                final Method status = this.entityView.getClass().getMethod(STATUS_MAP.get(info.getStatus()), MovementEnum.class);
//                status.invoke(this.entityView, info.getMove());
//                for (final UpgradeEnum upgrade : info.getUpgrade().keySet()) {
//                    Class<?>[] classArg = new Class<?>[info.getUpgrade().get(upgrade).size()];
//                    classArg = info.getUpgrade().get(upgrade).stream().map(x -> x.getClass()).collect(Collectors.toList()).toArray(classArg);
//                    final Method method = this.entityView.getClass().getMethod(UPGADE_MAP.get(upgrade), classArg);
//                    method.invoke(this.entityView, info.getUpgrade().get(upgrade));
//                }
//            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//    }
//
//    /** 
//     * 
//     * @return id entity
//     */
//    public UUID getId() {
//        return this.id;
//    }
//
//    /**
//     * 
//     * @return entity name 
//     */
//    public EntityEnum getEntityName() {
//        return this.entityName;
//    }

    /**
     * 
     * @param <K> 
     * @param <V> 
     * @return 
     */
    private static <K, V> Map<K, V> initializeMap() {
        final Map<K, V> map = new LinkedHashMap<K, V>();
        map.putAll(util.StaticMethodsUtils
            .xmlToMap(PATH_PLAYER, TAG_PLAYER, ATTR1_PLAYER, ATTR2_PLAYER));
        map.putAll(util.StaticMethodsUtils
                .xmlToMap(PATH_ENTITY, TAG_ENTITY, ATTR1_ENTITY, ATTR2_ENTITY));
        return map;
    }
}
