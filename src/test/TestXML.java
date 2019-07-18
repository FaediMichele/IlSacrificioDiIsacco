package test;

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import util.StaticMethodsUtils;

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
        Document xml = StaticMethodsUtils.getDocumentXML("/xml/Status.xml");
        List<Node> ls = StaticMethodsUtils.getNodesFromNodelList(xml.getElementsByTagName("MoveComponent"));
        ls.forEach(n -> System.out.println(n.getLocalName()));
    }

}
