package test;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.enumeration.EntityEnum;
import util.StaticMethodsUtils;
import view.javafx.game.EntityView;

/**
 * Tests for XML files.
 *
 */
@SuppressWarnings("all")
public class TestXML {
    /**
     * Test for status xml file.
     */
    @Test
    public void testStatusXML() {
        Document xml = StaticMethodsUtils.getDocumentXML("/xml/Status_1.xml");
        List<Node> ls = StaticMethodsUtils.getNodesFromNodelList(xml.getElementsByTagName("MoveComponent"));
        ls.forEach(n -> {
            System.out.println(n.getNodeName());
            if (n.hasChildNodes()) {
                NodeList tmp = n.getChildNodes();
                for (int i = 0; i < tmp.getLength(); i++) {
                    System.out.println(tmp.item(i).getTextContent());
                }
            }
        });
    }

    /**
     * Test for xml to map.
     */
    @Test
    public void testXmltoMap() {
        Map<EntityEnum, Class<? extends EntityView>> map = StaticMethodsUtils.xmlToMapClass("/xml/Entity.xml", "Entity", "path-entityEnum", "path-entityView");
        System.out.println(map.size());
        map.forEach((k, v) -> {
            System.out.println(k.getValue() + " " + v.getCanonicalName());
        });
        try {
            String s = "util.enumeration.BasicEntityEnum.WALL";
            System.out.println(Enum.valueOf((Class<Enum>) Class.forName(s.substring(0,  s.lastIndexOf("."))), s.substring(s.lastIndexOf(".") + 1)));
            //Class.forName("util.enumeration.BasicEntityEnum.WALL");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
