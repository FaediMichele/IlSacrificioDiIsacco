package controller.menu;

/**
 * The root of all menuSelection that contain the configuration manager.
 */
public class Root extends MenuSelectionImpl<InputMenu<SubMenu>> {
    private final ConfigurationManager manager;

    /**
     * Set the {@link ConfigurationManager}.
     * @param manager the ConfigurationManager.
     */
    public Root(final ConfigurationManager manager) {
        super();
        this.manager = manager;
    }

    /**
     * Get the {@link ConfigurationManager}.
     * @return the {@link ConfigurationManager}.
     */
    public ConfigurationManager getManager() {
        return manager;
    }
}
