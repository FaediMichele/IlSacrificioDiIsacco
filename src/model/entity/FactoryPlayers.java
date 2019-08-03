package model.entity;

import model.enumeration.PlayerEnum;
import model.util.DataPlayer;
import util.StaticMethodsUtils;

/**
 * 
 * This class make players.
 *
 */
public final class FactoryPlayers {

    private static String pathXml = "/xml/model/dataPlayer.xml";

    private FactoryPlayers() { }

    /**
     * 
     * @param player .
     * @return .
     */
    public static DataPlayer getDataPlayer(final PlayerEnum player) {
        return StaticMethodsUtils.enumFromXmlToDataPlayer(player, pathXml);
    }

    /**
     * 
     * @param player .
     * @return .
     */
    public static Player getPlayer(final PlayerEnum player) {
        return new Player(getDataPlayer(player));
    }
}
