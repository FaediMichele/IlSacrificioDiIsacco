package controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import model.enumeration.EntityEnum;
import model.enumeration.MovementEnum;
import model.enumeration.RootEnum;
import model.enumeration.StatusEnum;
import model.enumeration.UpgradeEnum;
import model.util.EntityInformation;
import util.StaticMethodsUtils;
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
    private static final String TAG_ENTITY = "BasicEntities";
    private static final String ATTR1_ENTITY = "path-entityEnum";
    private static final String ATTR2_ENTITY = "path-entityView";
    private static final String PATH_PLAYER = "/xml/Player.xml";
    private static final String TAG_PLAYER = "BasicPlayers";
    private static final String ATTR1_PLAYER = "path-playerEnum";
    private static final String ATTR2_PLAYER = "path-playerView";
    private static final String PATH_HEART = "/xml/Heart.xml";
    private static final String TAG_HEART = "BasicHearts";
    private static final String ATTR1_HEART = "path-heartEnum";
    private static final String ATTR2_HEART = "path-heartView";
    private static final String PATH_TEAR = "/xml/Tear.xml";
    private static final String TAG_TEAR = "BasicTears";
    private static final String ATTR1_TEAR = "path-tearEnum";
    private static final String ATTR2_TEAR = "path-tearView";
    private static final Map<EntityEnum, Class<? extends EntityView>> ENTITY_MAP = EntityController.initializeMap();

    private static final String PATH_STATUS = "/xml/Status.xml";
    private static final String TAG_STATUS = "BasicStatuses";
    private static final String ATTR1_STATUS = "path-status-enum";
    private static final Map<StatusEnum, String> STATUS_MAP = StaticMethodsUtils.xmlToMapMethods(PATH_STATUS,
            TAG_STATUS, ATTR1_STATUS);

    private static final String PATH_UPGRADE = "/xml/Upgrade.xml";
    private static final String TAG_UPGRADE = "BasicUpgrades";
    private static final String ATTR1_UPGRADE = "path-upgrade-enum";
    private static final Map<UpgradeEnum, String> UPGADE_MAP = StaticMethodsUtils.xmlToMapMethods(PATH_UPGRADE,
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
    public EntityController(final EntityInformation info, final GameView gameView)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.id = info.getId();
        this.entityName = info.getEntityName();
        final Class<? extends EntityView> classEntity = ENTITY_MAP.get(this.entityName);
        this.entityView = (EntityView) classEntity.newInstance();
        this.entityView.setGameView(gameView);
        this.entityView.appear(null);
    }

    /**
     * 
     * @param info is the status for the status component of entity.
     */
    public void update(final EntityInformation info) {
        if (info.getPosition() != null) {
            this.entityView.setHeight(info.getHeight())
                           .setWidth(info.getWidth())
                           .setX(info.getPosition().getX())
                           .setY(info.getPosition().getY());
        }
        try {
            final Method status = this.entityView.getClass().getMethod(STATUS_MAP.get(info.getStatus()),
                    MovementEnum.class);
            status.invoke(this.entityView, info.getMove());
            for (final UpgradeEnum upgrade : info.getUpgrade().keySet()) {
                Class<?>[] classArg = new Class<?>[info.getUpgrade().get(upgrade).size()];
                classArg = info.getUpgrade().get(upgrade).stream().map(x -> x.getClass()).collect(Collectors.toList())
                        .toArray(classArg);
                final Method method = this.entityView.getClass().getMethod(UPGADE_MAP.get(upgrade), classArg);
                method.invoke(this.entityView, info.getUpgrade().get(upgrade).toArray());
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
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
        map.putAll(StaticMethodsUtils.xmlToMapClass(PATH_PLAYER, TAG_PLAYER, ATTR1_PLAYER, ATTR2_PLAYER));
        map.putAll(StaticMethodsUtils.xmlToMapClass(PATH_ENTITY, TAG_ENTITY, ATTR1_ENTITY, ATTR2_ENTITY));
        map.putAll(StaticMethodsUtils.xmlToMapClass(PATH_HEART, TAG_HEART, ATTR1_HEART, ATTR2_HEART));
        map.putAll(StaticMethodsUtils.xmlToMapClass(PATH_TEAR, TAG_TEAR, ATTR1_TEAR, ATTR2_TEAR));
        return map;
    }

    /**
     * 
     * @param chosenInterface the chosen interface that corresponds with the
     *                        relative map
     * @param path            the xml path
     * @param tag             the xml tag
     * @param attr            the attributes needed for the xml tag
     */
    public static void addElementsInMap(final Class<? extends RootEnum> chosenInterface, final String path,
            final String tag, final String... attr) {
        if (chosenInterface.isAssignableFrom(EntityEnum.class) && attr.length == 2) {
            ENTITY_MAP.putAll(StaticMethodsUtils.xmlToMapClass(path, tag, attr[0], attr[1]));
        } else if (chosenInterface.isAssignableFrom(StatusEnum.class) && attr.length == 1) {
            STATUS_MAP.putAll(StaticMethodsUtils.xmlToMapMethods(path, tag, attr[0]));
        } else if (chosenInterface.isAssignableFrom(UpgradeEnum.class) && attr.length == 1) {
            UPGADE_MAP.putAll(StaticMethodsUtils.xmlToMapMethods(path, tag, attr[0]));
        }
    }
}
