package view.javafx.game;
import java.util.UUID;
import model.enumeration.BasicHeartEnum;

/**
* View of the hearts.
*/
/**
* View of the hearts.
*/
public class BlackHeartView extends AbstractHeartView {

    /**
     * 
     * @param id the id. 
     */
    public BlackHeartView(final UUID id) {
        super(id, BasicHeartEnum.BLACK);
    }
}
