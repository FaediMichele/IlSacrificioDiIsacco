package view.javafx;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.Pair;
import view.CharacterInfo;
import view.Command;
import view.SubMenu;
import view.SubMenuSelection;
import view.node.CircleList;

/**
 * This is the sub menu for the selection of the character.
 */
public class SubMenuRun extends SubMenu {
    private static final Image RANDOM_IMAGE = new Image("/menuImgs/randomSpritePreview.png");

    //CircleList. Height is calculated to have the same height and width even with the resize of the window.
    private static final int CL_WIDTH = 150;
    private static final int CL_HEIGHT = 65;
    private static final double CL_SCALE = 0.5;
    private static final long CL_TIME = 300;
    private static final int CL_X = 140;
    private static final int CL_Y = 50;

    private final Map<ImageView, CharacterInfo> infos = new LinkedHashMap<>();
    private final ProgressBar prgLife;
    private final ProgressBar prgDamage;
    private final ProgressBar prgSpeed;
    private final ImageView imgName;
    private final ImageView heart;
    private final ImageView speed;
    private final ImageView damage;
    private final CircleList list;

    /**
     * Create the sub menu for the run. It contains the information for the implemented character.
     * @param selector the {@link SubMenuSelection}.
     * @param pnMain the {@link Pane} that contains the other @param.
     * @param prgLife the progress bar for the life.
     * @param prgDamage the progress bar of the output damage.
     * @param prgSpeed the progress bar of the speed.
     * @param imgName the images with the name of the character.
     * @param random the random image for the circle list.
     * @param heart the image of the heart.
     * @param speed the image of the speed.
     * @param damage the image of the damage.
     * @param info the info for each character implemented.
     */
    public SubMenuRun(final SubMenuSelection selector, final Pane pnMain, final ProgressBar prgLife,
            final ProgressBar prgDamage, final ProgressBar prgSpeed, final ImageView imgName, 
            final ImageView random, final ImageView heart, final ImageView speed, final ImageView damage,
            final List<Pair<ImageView, CharacterInfo>> info) {
        super(selector, pnMain);
        this.prgLife = prgLife;
        this.prgDamage = prgDamage;
        this.prgSpeed = prgSpeed;
        this.imgName = imgName;
        this.heart = heart;
        this.speed = speed;
        this.damage = damage;
        list = new CircleListRandomJavafx(CL_WIDTH, CL_HEIGHT,
                CL_SCALE, Duration.millis(CL_TIME), random);
         list.setMarginLeft(CL_X);
         list.setMarginTop(CL_Y);
         pnMain.getChildren().add((Node) list);

        random.setImage(RANDOM_IMAGE);
        list.addAll(info.stream().map(p -> p.getX()).toArray());
        for (int i = 0; i < info.size(); i++) {
            this.infos.put(info.get(i).getX(), info.get(i).getY());
        }
        list.rotateRight();
    }

    @Override
    public final void select() {
        super.select();
        update(null);
    }

    @Override
    public final void input(final Command c) {
        switch (c) {
        case ARROW_LEFT:
            left();
            break;
        case ARROW_RIGHT:
            right();
            break;
        case ENTER:
            enter();
            break;
        case EXIT:
            exit();
            break;
        default:
        }
    }

    private void left() {
        list.rotateLeft();
        update(null);
    }

    private void right() {
        list.rotateRight();
        update(null);
    }

    private void enter() {
        update(infos.get(list.getElement()));
    }

    private void exit() {
        if (getSelector().contains(SubMenuGameMenu.class)) {
            getSelector().selectSubMenu(SubMenuGameMenu.class);
        }
    }
    private void update(final CharacterInfo c) {
        final CharacterInfo cf = c == null ? infos.get(list.getElement(0)) : c;
        if (cf != null) {
            prgLife.setProgress(cf.getLife());
            prgDamage.setProgress(cf.getDamage());
            prgSpeed.setProgress(cf.getSpeed());
            imgName.setImage((Image) cf.getNameImage());
            prgLife.setVisible(true);
            prgDamage.setVisible(true);
            prgSpeed.setVisible(true);
            heart.setVisible(true);
            speed.setVisible(true);
            damage.setVisible(true);
        } else {
            prgLife.setVisible(false);
            prgDamage.setVisible(false);
            prgSpeed.setVisible(false);
            heart.setVisible(false);
            speed.setVisible(false);
            damage.setVisible(false);
            imgName.setImage((Image) RANDOM_IMAGE);
        }
    }

    @Override
    public final void reset() {
        list.reset();
        update(null);
    }

}
