package view.javafx.game;

import java.util.UUID;

/**
 * View and animations of a Isaac.
 *
 */
public class IsaacView extends AbstractPlayerView {

    /**
     * 
     * @param id Isaac ID
     */
    public IsaacView(final UUID id) {
        super(id, "/gameImgs/character_001_isaac.png");
    }
}
