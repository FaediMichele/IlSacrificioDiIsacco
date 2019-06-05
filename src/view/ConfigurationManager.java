package view;

import java.util.Map;
import java.util.Set;

/**
 * This interface is used to load and store the configuration in a external file.
 *
 */
public interface ConfigurationManager {

    /**
     * Get the map between a object (the input value) and the {@link Command}.
     * @return the map input -> command.
     */
    Map<Object, Command> getKeyMap();

    /**
     * Get the CharacterInfo stored in the configuration file.
     * @return the saved {@link CharacterInfo}.
     */
    Set<CharacterInfo> getCharactes();
}
