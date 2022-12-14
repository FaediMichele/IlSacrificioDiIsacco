package controller.menu;

import java.util.Set;

import model.enumeration.BasicPlayerEnum;
import model.util.FactoryPlayersUtils;
import util.Command;
import view.interfaces.SubMenuRunView;
import view.interfaces.SubMenuView;
import view.javafx.menu.SubMenuRunViewImpl;

/**
 * This is the sub menu for the selection of the character.
 */
public class SubMenuRun extends SubMenu {

    //CircleList. Height is calculated to have the same height and width even with the resize of the window.
    private static final int CL_WIDTH = 100;
    private static final int CL_HEIGHT = 25;
    private static final double CL_SCALE = 0.5;
    private static final long CL_TIME = 300;
    private static final int CL_X = 140;
    private static final int CL_Y = 50;
    private final SubMenuRunView smrv;

    /**
     * Create the sub menu for the run. It contains the information for the implemented character.
     * @param selector the {@link SubMenuSelection}.
     */
    public SubMenuRun(final MenuSelection<SubMenu> selector) {
        super(selector);
        smrv = new SubMenuRunViewImpl(CL_WIDTH, CL_HEIGHT, CL_SCALE, CL_TIME, CL_X, CL_Y);
    }

    @Override
    public final void selectChild() {
        super.selectChild();
        smrv.setInfo(((Root) getFather().getFather().get()).getManager().getCharactes());
    }

    @Override
    public final void input(final Set<Command> c) {
        if (c.contains(Command.ARROW_LEFT)) {
            left();
        } else if (c.contains(Command.ARROW_RIGHT)) {
            right();
        } else if (c.contains(Command.ENTER)) {
            enter();
        } else if (c.contains(Command.EXIT)) {
            exit();
        }
    }

    private void left() {
        smrv.left();
        update(smrv.getInfoSelected());
    }

    private void right() {
        smrv.right();
        update(smrv.getInfoSelected());
    }

    private void enter() {
        update(smrv.getSelected());
        getFather().getFather().get().select(GameSelection.class, smrv.getSelected());
    }

    private void exit() {
        if (getFather().contains(SubMenuGameMenu.class)) {
            getFather().select(SubMenuGameMenu.class);
        }
    }
    private void update(final CharacterInfo c) {
        try {
            if (c != null) {
                smrv.update(FactoryPlayersUtils.getDataPlayer(c.getInfo()), c);
            } else {
                smrv.update(null, null);
            }
        } catch (ClassNotFoundException e) {
            try {
                smrv.update(FactoryPlayersUtils.getDataPlayer(BasicPlayerEnum.ISAAC), c);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public final void unselectChild() {
        smrv.reset();
        update(null);
    }

    @Override
    public final SubMenuView getSubMenuView() {
        return smrv;
    }

}
