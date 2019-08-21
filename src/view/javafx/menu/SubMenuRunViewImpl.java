package view.javafx.menu;


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
import model.util.DataPlayer;
import view.interfaces.SubMenuRunView;
import view.javafx.ViewGetterUtil;
import view.node.CircleList;
import view.node.javafx.CircleListRandomJavafx;

/**
 * Implementation view of the SubMenu of the run part of the main menu.
 */
public class SubMenuRunViewImpl implements SubMenuRunView {
    private static final double DEFAULTLIFE = 3.5;
    private static final double DEFAULTSPEED = 1.8;
    private static final double DEFAULTDAMAGE = 1;
    private static final String BACKGROUND = "/menuImgs/characterMenuBackground.png";
    private final Image randomImage = new Image("/menuImgs/randomSpritePreview.png");
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
        final ImageView random = ViewGetterUtil.getNodeByName("imgRandom", ImageView.class);
        list = new CircleListRandomJavafx(width, height,
                scale, Duration.millis(time), random);
        list.setMarginLeft(x);
        list.setMarginTop(y);
        ViewGetterUtil.getNodeByName("pnRun", Pane.class).getChildren().add((Node) list);

        prgLife = ViewGetterUtil.getNodeByName("prgLife", ProgressBar.class);
        prgDamage = ViewGetterUtil.getNodeByName("prgDamage", ProgressBar.class);
        prgSpeed = ViewGetterUtil.getNodeByName("prgSpeed", ProgressBar.class);
        imgName = ViewGetterUtil.getNodeByName("imgName", ImageView.class);
        heart = ViewGetterUtil.getNodeByName("imgHeart", ImageView.class);
        speed = ViewGetterUtil.getNodeByName("imgSpeed", ImageView.class);
        damage = ViewGetterUtil.getNodeByName("imgDamage", ImageView.class);
        ViewGetterUtil.getNodeByName("imgRunBackground", ImageView.class).setImage(new Image(BACKGROUND));

        random.setImage(randomImage);
        list.rotateRight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInfo(final Set<CharacterInfo> set) {
        this.infos = set;
        if (list.size() < 2) {
            set.forEach(i -> list.addElement((ImageView) i.getImage()));
            infos.forEach(i -> mapInfos.put((ImageView) i.getImage(), i));
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void left() {
        list.rotateLeft();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void right() {
        list.rotateRight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterInfo getSelected() {
        return mapInfos.get(list.getElement());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterInfo getInfoSelected() {
        return mapInfos.get(list.getElement(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final DataPlayer dp, final CharacterInfo c) {
        if (dp != null && c != null) {
            if (infos.isEmpty()) {
                throw new IllegalStateException("No character inserted");
            }
            prgLife.setProgress(dp.getLife() / (DEFAULTLIFE * 2));
            prgDamage.setProgress(dp.getDamage() / (DEFAULTDAMAGE * 2));
            prgSpeed.setProgress(dp.getSpeed() / (DEFAULTSPEED * 2));
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
            imgName.setImage((Image) randomImage);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        list.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getUIMaster() {
        return ViewGetterUtil.getNodeByName("pnRun", Pane.class);
    }

}
