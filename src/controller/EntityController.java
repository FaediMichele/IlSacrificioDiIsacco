package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import model.enumeration.EntityEnum;
import model.enumeration.MovementEnum;
import model.enumeration.StatusEnum;
import model.enumeration.UpgradeEnum;
import model.util.EntityInformation;
import view.javafx.game.EntityView;
import view.javafx.game.GameView;

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
    private static final String PATH_PLAYER = "/xml/Player.xml";
    private static final String TAG_PLAYER = "Player";
    private static final String ATTR1_PLAYER = "path-playerEnum";
    private static final String ATTR2_PLAYER = "path-entityView";
    private static final Map<EntityEnum, Class<? extends EntityView>> ENTITY_MAP = EntityController.initializeMap();

    private static final String PATH_STATUS = "/xml/Status.xml";
    private static final String TAG_STATUS = "Status";
    private static final String ATTR1_STATUS = "path-status-enum";
    private static final Map<StatusEnum, String> STATUS_MAP = util.StaticMethodsUtils.xmlToMapMethods(PATH_STATUS,
            TAG_STATUS, ATTR1_STATUS);

    private static final String PATH_UPGRADE = "/xml/Upgrade.xml";
    private static final String TAG_UPGRADE = "Upgrade";
    private static final String ATTR1_UPGRADE = "path-upgrade-enum";
    private static final Map<UpgradeEnum, String> UPGADE_MAP = util.StaticMethodsUtils.xmlToMapMethods(PATH_UPGRADE,
            TAG_UPGRADE, ATTR1_UPGRADE);

    private final UUID id;
    private final EntityView entityView;
    private final EntityEnum entityName;

    /**
     * 
     * @param info 
     * @param gameView the GameView to which this entityView belongs to
     * @throws ClassNotFoundException 
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InvocationTargetException 
     */
    public EntityController(final EntityInformation info, final GameView gameView) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.id = info.getId();
        this.entityName = info.getEntityName();
        final Class<? extends EntityView> classEntity = ENTITY_MAP.get(this.entityName);
        final Constructor<? extends EntityView> constructor = classEntity.getConstructor(new Class[] { UUID.class});
        this.entityView = (EntityView) constructor.newInstance(new Object[] {id});
        this.entityView.setGameView(gameView);
        this.entityView.appear(null);
    }

    /**
     * 
     * @param info is the status for the status component of entity.
     */
    public void update(final EntityInformation info) {
            this.entityView.setX(info.getPosition().getX())
                            .setY(info.getPosition().getY())
                            .setHeight(info.getHeight())
                            .setWidth(info.getWidth());
            try {
                final Method status = this.entityView.getClass().getMethod(STATUS_MAP.get(info.getStatus()), MovementEnum.class);
                System.out.println(this.entityName);
                System.out.println(status);
                status.invoke(this.entityView, info.getMove());
                for (final UpgradeEnum upgrade : info.getUpgrade().keySet()) {
                    Class<?>[] classArg = new Class<?>[info.getUpgrade().get(upgrade).size()];
                    classArg = info.getUpgrade().get(upgrade).stream().map(x -> x.getClass()).collect(Collectors.toList()).toArray(classArg);
                    final Method method = this.entityView.getClass().getMethod(UPGADE_MAP.get(upgrade), classArg);
                    method.invoke(this.entityView, info.getUpgrade().get(upgrade).toArray());
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
    }

    /** 
     * 
     * @return id entity
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * 
     * @return entity name 
     */
    public EntityEnum getEntityName() {
        return this.entityName;
    }

    /**
     * 
     * @return entity view 
     */
    public EntityView getEntityView() {
        return this.entityView;
    }

    /**
     * 
     * @param <K> 
     * @param <V> 
     * @return 
     */
    private static <K, V> Map<K, V> initializeMap() {
        final Map<K, V> map = new LinkedHashMap<K, V>();
        map.putAll(util.StaticMethodsUtils
            .xmlToMapClass(PATH_PLAYER, TAG_PLAYER, ATTR1_PLAYER, ATTR2_PLAYER));
        map.putAll(util.StaticMethodsUtils
                .xmlToMapClass(PATH_ENTITY, TAG_ENTITY, ATTR1_ENTITY, ATTR2_ENTITY));
        return map;
    }
}
