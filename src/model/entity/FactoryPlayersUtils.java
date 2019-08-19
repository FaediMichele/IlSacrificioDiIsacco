package model.entity;

import java.util.Map;
import model.enumeration.PlayerEnum;
import model.util.DataPlayer;
import util.StaticMethodsUtils;

/**
 * 
 * This class make players.
 *
 */
public final class FactoryPlayersUtils {

    private static final String PATH_XML = "/xml/model/dataPlayer.xml";
    private static Map<PlayerEnum, DataPlayer> fromEunmToData = StaticMethodsUtils.enumFromXmlToDataPlayer(PATH_XML);

    private FactoryPlayersUtils() { }

    /**
     * 
     * @param player .
     * @return .
     * @throws ClassNotFoundException 
     */
    public static DataPlayer getDataPlayer(final PlayerEnum player) throws ClassNotFoundException {
        return fromEunmToData.get(player);
    }

    /**
     * 
     * @param player .
     * @return .
     * @throws ClassNotFoundException 
     */
    public static Player getPlayer(final PlayerEnum player) throws ClassNotFoundException {
        return new Player(getDataPlayer(player));
    }

    /**
     * This method allows you to add {@link DataPlayer} to the players map.
     * 
     * @param pathXml is path for new file XML
     */
    public static void addPlayers(final String pathXml) {
        fromEunmToData.putAll(StaticMethodsUtils.enumFromXmlToDataPlayer(pathXml));
    }
}
