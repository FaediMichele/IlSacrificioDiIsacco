package view.javafx.game;

import java.util.UUID;
import model.enumeration.BasicHeartEnum;

/**
* View of the hearts.
*/
public class SimpleHeartView extends AbstractHeartView {

    /**
     * 
     * @param id the id. 
     */
    public SimpleHeartView(final UUID id) {
        super(id, BasicHeartEnum.RED);
    }
}
