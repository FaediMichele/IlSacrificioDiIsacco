package view.javafx.game;

import java.util.UUID;
import model.enumeration.BasicHeartEnum;

/**
* View of the hearts.
*/
public class RedHeartView extends AbstractHeartView {

    /**
     * 
     * @param id the id. 
     */
    public RedHeartView(final UUID id) {
        super(id, BasicHeartEnum.RED);
    }
}
