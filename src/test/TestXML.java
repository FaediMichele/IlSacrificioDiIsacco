package test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.component.Component;
import model.component.StatusComponent;
import model.entity.Bomb;
import model.entity.Entity;
import model.entity.GaperEnemy;
import model.enumeration.EntityEnum;
import model.events.Event;
import model.events.UseThingEvent;
import model.game.Room;
import util.EventListener;
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


    /**
     * Test for istanceof to map.
     */
    @Test
    public void testIstanceof() {
        Entity bomb = new Bomb();
        System.out.println(bomb instanceof  Entity);
        System.out.println(bomb.getClass().isInstance(Entity.class));
        System.out.println(Entity.class.isInstance(bomb));
        UseThingEvent usTh = new UseThingEvent(new GaperEnemy(), Bomb.class);
        System.out.println(usTh.getReleasedEntityClass().isInstance(bomb));
        //System.out.println(Bomb.class.isInstance(event.getReleasedEntityClass()));
        //System.out.println("uso oggetto " + thingToRelease);
    }
}
