package controller;

import view.javafx.MainView;

/**
 * Controller for the main menu.
 */
public final class Launcher {
    private Launcher() {
    }

    /**
     * Launch the main menu.
     * @param args not used.
     */
    public static void main(final String[] args) {
        new MainView().openEntryPoint(args);
    }
}
