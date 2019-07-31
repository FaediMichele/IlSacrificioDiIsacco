package view.javafx;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import util.Command;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import view.CharacterInfo;
import view.ConfigurationManager;
//import view.enumeration.PlayerMenuEnum;

import org.w3c.dom.Node;
import java.io.File;

/**
 * The configuration manager for javafx.
 *
 */
public class ConfigurationManagerJavafx implements ConfigurationManager {
    private static final String KEYMAPTAG = "KeyMap";
    private static final String CHARACTERSTAG = "Characters";
    private static final String CHARACTERTAG = "Character";
    private Document doc;
    private Map<Object, Command> keyMap;
    private final List<CharacterInfo> characters = new ArrayList<>();

    /**
     * Create the configuration manager with the configuration file.
     * @param path the path of the configuration file.
     */
    public ConfigurationManagerJavafx(final String path) {
        try {
            final File fXmlFile = new File(this.getClass().getResource(path).getFile());
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(fXmlFile);
            this.doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Map between KeyCode -> Command.
     * @return the map of the key.
     */
    @Override
    public Map<Object, Command> getKeyMap() {
        if (keyMap != null) {
            return keyMap;
        }
        try {
            keyMap = new LinkedHashMap<>();
            final NodeList nList = doc.getElementsByTagName(KEYMAPTAG);
            final Node n = nList.item(0);
            final NodeList keyList = n.getChildNodes();
            for (int i = 1; i < keyList.getLength(); i += 2) {
                final Node key = keyList.item(i);
                keyMap.put(KeyCode.getKeyCode(key.getTextContent()), Command.valueOf(key.getNodeName()));
            }
            return keyMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the character info stored in the file.
     * The image is a {@link javafx.scene.image.Image}.
     */
    @Override
    public Set<CharacterInfo> getCharactes() {
        if (!characters.isEmpty()) {
            return new LinkedHashSet<CharacterInfo>(characters);
        }
        try {
            final NodeList nList = doc.getElementsByTagName(CHARACTERSTAG);
            final Node n = nList.item(0);
            final NodeList charList = n.getChildNodes();
            for (int i = 1; i < charList.getLength() - 1; i += 2) {
                if (!charList.item(i).getNodeName().equals(CHARACTERTAG)) {
                    throw new IllegalStateException("The name for the tag is " + CHARACTERTAG
                            + ". Name used:" + charList.item(i).getNodeName());
                }
                final NamedNodeMap attr = charList.item(i).getAttributes();

                characters.add(new CharacterInfo(new Image(attr.getNamedItem("Name").getNodeValue()),
                        new Image(attr.getNamedItem("ImagePath").getNodeValue()))); /*,
                        loadPlayerEnum(attr.getNamedItem("package").getNodeValue(),
                                (attr.getNamedItem("Type").getNodeValue()))));*/
            }
            return new LinkedHashSet<CharacterInfo>(characters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    private PlayerMenuInfo loadPlayerInfo(final String pack, final String typ) {
        // TODO .
        final PlayerMenuEnum = loadPlayerEnum(pack, typ);
        return null;
    }
    private PlayerMenuEnum loadPlayerEnum(final String pack, final String typ){
        return null
    }
    */
}
