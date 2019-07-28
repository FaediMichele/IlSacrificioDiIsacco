package util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for all static methods.
 */
public final class StaticMethodsUtils {
    private StaticMethodsUtils() {
    }

    /**
     * Generic static method for equals.
     * 
     * @param obj1 first Object
     * @param obj2 second Object
     * @return true if all the fields needed to check of the two objects are equals
     */
    public static boolean equals(final Object obj1, final Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else if (obj1 == null || obj2 == null) {
            return false;
        }

        if (obj1.getClass() != obj2.getClass()) {
            return false;
        }

        if (obj1.getClass().getSuperclass() != obj2.getClass().getSuperclass()) {
            return false;
        }

        Class<?> classObj1 = obj1.getClass();
        Class<?> classObj2 = obj2.getClass();

        /*
         * It gets all the list of fields of the class of obj1 and obj2 that are needed
         * for equals(so those without the @NotEquals annotation)
         */
        final List<Field> fieldsListObj1 = Stream.of(classObj1.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(NotEquals.class)).collect(Collectors.toList());
        final List<Field> fieldsListObj2 = Stream.of(classObj2.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(NotEquals.class)).collect(Collectors.toList());
        /*
         * It gets all the values of the fields of obj1 and obj2
         */
        final List<Object> fieldsValueObj1 = fieldsListObj1.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                final Stream<Object> s = Stream.of(f.get(obj1));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        final List<Object> fieldsValueObj2 = fieldsListObj2.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                final Stream<Object> s = Stream.of(f.get(obj2));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        boolean eq = fieldsValueObj1.equals(fieldsValueObj2);

        /**
         * It continues checking on all super classes and takes the getter methods
         * (=those that begins with either get or is and have the @EqualsForGetters
         * annotation) then it takes their return values and checks if their are the
         * same for both obj1 and obj2
         */
        while (!(classObj1.getSuperclass() == Object.class || classObj2.getSuperclass() == Object.class)) {
            classObj1 = classObj1.getSuperclass();
            classObj2 = classObj2.getSuperclass();
            final List<Method> methodsListObj1 = Stream.of(classObj1.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(EqualsForGetters.class)
                            && (m.getName().substring(0, 3).equals("get") || m.getName().substring(0, 3).equals("is")))
                    .collect(Collectors.toList());
            final List<Method> methodsListObj2 = Stream.of(classObj2.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(EqualsForGetters.class)
                            && (m.getName().substring(0, 3).equals("get") || m.getName().substring(0, 3).equals("is")))
                    .collect(Collectors.toList());
            final List<Object> getterValuesObj1 = methodsListObj1.stream().flatMap(m -> {
                try {
                    return Stream.of(m.invoke(obj1));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            final List<Object> getterValuesObj2 = methodsListObj2.stream().flatMap(m -> {
                try {
                    return Stream.of(m.invoke(obj2));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            eq = eq && getterValuesObj1.equals(getterValuesObj2);
        }
        return eq;
    }

    /**
     * Generic static method for hashCode.
     * 
     * @param obj the Object
     * @return the hashCode
     */
    public static int hashCode(final Object obj) {
        final Class<?> classObj = obj.getClass();
        final List<Field> fieldsListObj = Stream.of(classObj.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(NotHashCode.class)).collect(Collectors.toList());
        final List<Object> fieldsValueObj = fieldsListObj.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                final Stream<Object> s = Stream.of(f.get(obj));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return Arrays.hashCode(fieldsValueObj.toArray());
    }

    /**
     * Returns the {@link Document} for xml file.
     * 
     * @param filePath the file path string
     * @return {@link Document}
     */
    public static Document getDocumentXML(final String filePath) {
        File file;
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;
        try {
            file =  new File(Object.class.getResource(filePath).toURI());
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
            return document;
        } catch (ParserConfigurationException | SAXException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the {{@link Node)} list of all the nodes.
     * 
     * @param nl {@link NodeList}
     * @return a list node
     */
    public static List<Node> getNodesFromNodelList(final NodeList nl) {
        final List<Node> lNode = new ArrayList<Node>();
        try {
            for (int i = 0; i < nl.getLength(); i++) {
                lNode.add(nl.item(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lNode;
    }

    /**
     * Returns the string list of all the nodes.
     * 
     * @param nl {@link NodeList}
     * @return a list string
     */
    public static List<String> getStringsFromNodelList(final NodeList nl) {
        final List<String> ls = new ArrayList<String>();
        try {
            for (int i = 0; i < nl.getLength(); i++) {
                ls.add(nl.item(i).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
