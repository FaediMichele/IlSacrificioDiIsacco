//package test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import util.StaticMethodsUtils;
//
///**
// * 
// * Test class for the decoder that translates the component status into mediums
// * to be called in entities of the view.
// *
// */
//public class TestDecoder {
//    /**
//     * Test for decoder.
//     * 
//     * @throws IllegalAccessException 
//     * @throws InstantiationException 
//     * @throws SecurityException 
//     * @throws NoSuchMethodException 
//     * @throws InvocationTargetException 
//     * @throws IllegalArgumentException 
//     * @throws ClassNotFoundException 
//     */
//    @Test
//    public void testDecoder() throws InstantiationException, IllegalAccessException, NoSuchMethodException,
//            SecurityException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
//        final Class<?> s = Class.forName("test.TestControllerEntityView");
//        final TestEntityController e = (TestEntityController) s.newInstance();
//        final Method m = e.getClass().getMethod("test", String.class);
//        assertEquals("call test test 1 1", m.invoke(e, "test 1"));
//        assertEquals("call test test 1 2", m.invoke(e, "test 1"));
//    }
//
//    /**
//     * Test decoder with XML file.
//     * 
//     * @throws InstantiationException 
//     * @throws IllegalAccessException 
//     * @throws NoSuchMethodException 
//     * @throws SecurityException 
//     * @throws IllegalArgumentException 
//     * @throws InvocationTargetException 
//     * @throws ClassNotFoundException 
//     */
//    @Test
//    public void testDecoderWithXML() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
//            NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
//        Map<String, List<String>> simpleModelStatus = new HashMap<>();
//        simpleModelStatus.put("Entity", new ArrayList<String>(Arrays.asList("SimpleEntityTest")));
//        simpleModelStatus.put("Status", new ArrayList<String>(Arrays.asList("Normal", "Up")));
//        simpleModelStatus.put("Upgrade", Collections.emptyList());
//
//        // XML loader
//        Map<String, String> entityMap = this.xmlToMap(StaticMethodsUtils.getNodesFromNodelList(
//                                                      StaticMethodsUtils.getDocumentXML("/xml/Entity.xml")
//                                                      .getElementsByTagName("Entity")));
//        Map<String, String> statusMap = this.xmlToMap(StaticMethodsUtils.getNodesFromNodelList(
//                                                      StaticMethodsUtils.getDocumentXML("/xml/Status.xml")
//                                                       .getElementsByTagName("Status")));
//
//    Class<?> entityClass = Class.forName(entityMap.get(simpleModelStatus.get("Entity").get(0)));
//    TestEntityController entity = (TestEntityController) entityClass.newInstance();
//    Method methodStatus = entity.getClass().getMethod(statusMap.get(simpleModelStatus.get("Status").get(0)), String.class);
//    methodStatus.invoke(entity, simpleModelStatus.get("Status").get(1));
//    assertEquals("TestControllerEntityView [j=0, status=normal, move=Up, body=null, head=null]", entity.toString());
//    }
//
//    private Map<String, String> xmlToMap(final List<Node> node) {
//        Map<String, String> map = new HashMap<>();
//        node.forEach(n -> {
//            NodeList tmp = n.getChildNodes();
//            for (int i = 1; i < tmp.getLength(); i = i + 2) {
//                map.put(tmp.item(i).getNodeName(), tmp.item(i).getTextContent());
//            }
//        });
//        return map;
//    }
//}
