package model.util;

import java.util.List;
import model.enumeration.HeartEnum;
import util.Pair;

/**
 * 
 *  .
 *
 */
public class StatisticsInformations {

    private int bombs;
    private int keys;
    private List<Pair<HeartEnum, Double>> hearts;

    /**
     * 
     * @return bombs.
     */
    public int getBombs() {
        return bombs;
    }

    /**
     *  .
     * @param bombs .
     * @return this .
     */
    public StatisticsInformations setBombs(final int bombs) {
        this.bombs = bombs;
        return this;
    }

    /**
     * 
     * @return keys.
     */
    public int getKeys() {
        return keys;
    }

    /**
     * 
     * @param keys .
     * @return this.
     */
    public StatisticsInformations setKeys(final int keys) {
        this.keys = keys;
        return this;
    }

    /**
     * 
     * @return hearts.
     */
    public List<Pair<HeartEnum, Double>> getHearts() {
        return hearts;
    }

    /**
     * 
     * @param hearts .
     * @return this.
     */
    public StatisticsInformations setHearts(final List<Pair<HeartEnum, Double>> hearts) {
        this.hearts = hearts;
        return this;
    }
}
