package view.javafx.game.menu;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import controller.menu.CharacterInfo;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.menuInterfaces.SubMenuRunView;
import view.node.CircleList;
import view.node.javafx.CircleListRandomJavafx;

/**
 * Implementation view of the SubMenu of the run part of the main menu.
 */
public class SubMenuRunViewImpl implements SubMenuRunView {
    private static final Image RANDOM_IMAGE = new Image("/menuImgs/randomSpritePreview.png");
    private Set<CharacterInfo> infos;
    private final Map<ImageView, CharacterInfo> mapInfos = new LinkedHashMap<>();
    private final ProgressBar prgLife;
    private final ProgressBar prgDamage;
    private final ProgressBar prgSpeed;
    private final ImageView imgName;
    private final ImageView heart;
    private final ImageView speed;
    private final ImageView damage;
    private final CircleList list;

    /**
     * Create a new SubMenuRunViewImpl.
     * @param width dimension of the circle list.
     * @param height dimension of the circle list.
     * @param scale dimension of the circle list.
     * @param time time of the animation.
     * @param x position of the circle list.
     * @param y position of the circle list.
     */
    public SubMenuRunViewImpl(final int width, final int height, final double scale, final long time, final int x, final int y) {
        final ImageView random = ViewGetter.getNodeByName("imgRandom", ImageView.class);
        list = new CircleListRandomJavafx(width, height,
                scale, Duration.millis(time), random);
        list.setMarginLeft(x);
        list.setMarginTop(y);
        ViewGetter.getNodeByName("pnRun", Pane.class).getChildren().add((Node) list);

        prgLife = ViewGetter.getNodeByName("prgLife", ProgressBar.class);
        prgDamage = ViewGetter.getNodeByName("prgDamage", ProgressBar.class);
        prgSpeed = ViewGetter.getNodeByName("prgSpeed", ProgressBar.class);
        imgName = ViewGetter.getNodeByName("imgName", ImageView.class);
        heart = ViewGetter.getNodeByName("imgHeart", ImageView.class);
        speed = ViewGetter.getNodeByName("imgSpeed", ImageView.class);
        damage = ViewGetter.getNodeByName("imgDamage", ImageView.class);

        random.setImage(RANDOM_IMAGE);
        list.rotateRight();
    }

    /**
     * Set the character to show.
     * @param set the list of the character.
     */
    public void setInfo(final Set<CharacterInfo> set) {
        this.infos = set;
        if (list.size() < 2) {
            set.forEach(i -> list.addElement((ImageView) i.getImage()));
            infos.forEach(i -> mapInfos.put((ImageView) i.getImage(), i));
        }

    }


    /**
     * Swipe to left.
     */
    public void left() {
        list.rotateLeft();
    }

    /**
     * Swipe to right.
     */
    public void right() {
        list.rotateRight();
    }

    /**
     * Get the selected info (and random the list if necessary).
     * @return the info of the selected character
     */
    public CharacterInfo getSelected() {
        return mapInfos.get(list.getElement());
    }

    /**
     * Get the selected info (don't random the list).
     * @return the info of the selected character
     */
    public CharacterInfo getInfoSelected() {
        return mapInfos.get(list.getElement(0));
    }

    /**
     * Update the info.
     * @param c the info.
     */
    public void update(final CharacterInfo c) {
        if (c != null) {
            if (infos.isEmpty()) {
                throw new IllegalStateException("No character inserted");
            }
            /*
            final PlayerMenuInfo info = cf.getInfo();
            prgLife.setProgress(info.getLife());
            prgDamage.setProgress(info.getDamage());
            prgSpeed.setProgress(info.getSpeed());
            */
            imgName.setImage((Image) c.getNameImage());
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

    /**
     * Reset the list.
     */
    public void reset() {
        list.reset();
    }

    @Override
    public final Object getMain() {
        return ViewGetter.getNodeByName("pnRun", Pane.class);
    }

}
