package view.javafx;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import util.Pair;
import view.SubMenu;
import view.SubMenuSelection;
import view.character.CharacterInfo;
import view.node.CircleList;

/**
 * This is the sub menu for the selection of the character.
 */
public class SubMenuRun extends SubMenu {
    private final Map<ImageView, CharacterInfo> infos = new LinkedHashMap<>();
    private final ProgressBar prgLife;
    private final ProgressBar prgDamage;
    private final ProgressBar prgSpeed;
    private final CircleList list;

    /**
     * Create the sub menu for the run. It contains the information for the implemented character.
     * @param selector the {@link SubMenuSelection}.
     * @param pnMain the {@link Pane} that contains the other @param.
     * @param prgLife the progress bar for the life.
     * @param prgDamage the progress bar of the output damage.
     * @param prgSpeed the progress bar of the speed.
     * @param list an empty {@link CircleList}.
     * @param info the info for each character implemented.
     */
    @SafeVarargs
    public SubMenuRun(final SubMenuSelection selector, final Pane pnMain, final ProgressBar prgLife, final ProgressBar prgDamage, final ProgressBar prgSpeed,
            final CircleList list, final Pair<ImageView, CharacterInfo>... info) {
        super(selector, pnMain);
        this.prgLife = prgLife;
        this.prgDamage = prgDamage;
        this.prgSpeed = prgSpeed;
        this.list = list;
        for (int i = 0; i < info.length; i++) {
            this.infos.put(info[i].getX(), info[i].getY());
        }
    }

    @Override
    public final void left() {
        list.rotateLeft();
        updateProgressBar();
    }

    @Override
    public final void right() {
        list.rotateRight();
        updateProgressBar();
    }

    @Override
    public final void enter() {
        list.getElement();
    }
    private void updateProgressBar() {
        final CharacterInfo cf = infos.get(list.getElement());
        prgLife.setProgress(cf.getLife());
        prgDamage.setProgress(cf.getDamage());
        prgSpeed.setProgress(cf.getSpeed());
    }

    @Override
    public void reset() {
    }

}
