package util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.enumeration.BasicPlayerEnum;
import model.enumeration.PlayerEnum;
import model.util.DataPlayer;

/**
 * Class for all static methods.
 */
public final class StaticMethodsUtils {

    private static final double LIFE_ISAAC = 3.5;
    private static final double SPEED_ISAAC = 1.8;
    private static final double DAMAGE_ISAAC = 1.0;
    private static final double RATE_ISAAC = 1000;

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
        while (eq && !(classObj1.getSuperclass() == Object.class || classObj2.getSuperclass() == Object.class)) {
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
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(Object.class.getResourceAsStream(filePath));
            document.normalize();
            return document;
        } catch (ParserConfigurationException | SAXException | IOException e) {
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

    /**
     * .
     * 
     * @param <X>  .
     * @param <Y>  .
     * @param path .
     * @param tag  .
     * @param attr .
     * @return .
     */
    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    public static <X, Y> Map<X, Y> xmlToMapClass(final String path, final String tag, final String... attr) {
        final Map<X, Y> map = new HashMap<>();
        final NodeList nl = StaticMethodsUtils.getDocumentXML(path).getElementsByTagName(tag);
        final String path1 = (attr.length < 1 || nl.item(0).getAttributes().getNamedItem(attr[0]) == null) ? ""
                : nl.item(0).getAttributes().getNamedItem(attr[0]).getNodeValue();
        final String path2 = (attr.length < 2 || nl.item(0).getAttributes().getNamedItem(attr[1]) == null) ? ""
                : nl.item(0).getAttributes().getNamedItem(attr[1]).getNodeValue() + ".";
        final List<Node> node = StaticMethodsUtils.getNodesFromNodelList(nl);
        node.forEach(n -> {
            final NodeList tmp = n.getChildNodes();
            for (int i = 0; i < tmp.getLength(); i++) {
                if (tmp.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        map.put((X) Enum.valueOf((Class<Enum>) Class.forName(path1), tmp.item(i).getNodeName()),
                                (Y) Class.forName(path2 + tmp.item(i).getTextContent()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return (Map<X, Y>) map;
    }

    /**
     * .
     * 
     * @param <X>  .
     * @param <Y>  .
     * @param path .
     * @param tag  .
     * @param attr .
     * @return .
     */
    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    public static <X, Y> Map<X, Y> xmlToMapMethods(final String path, final String tag, final String... attr) {
        final Map<X, Y> map = new HashMap<>();
        final NodeList nl = StaticMethodsUtils.getDocumentXML(path).getElementsByTagName(tag);
        final String path1 = (attr.length < 1 || nl.item(0).getAttributes().getNamedItem(attr[0]) == null) ? ""
                : nl.item(0).getAttributes().getNamedItem(attr[0]).getNodeValue();
        final String path2 = (attr.length < 2 || nl.item(0).getAttributes().getNamedItem(attr[1]) == null) ? ""
                : nl.item(0).getAttributes().getNamedItem(attr[1]).getNodeValue() + ".";
        final List<Node> node = StaticMethodsUtils.getNodesFromNodelList(nl);
        node.forEach(n -> {
            final NodeList tmp = n.getChildNodes();
            for (int i = 0; i < tmp.getLength(); i++) {
                if (tmp.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        map.put((X) Enum.valueOf((Class<Enum>) Class.forName(path1), tmp.item(i).getNodeName()),
                                (Y) (path2 + tmp.item(i).getTextContent()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return (Map<X, Y>) map;
    }
    /**
     * Get the angle between two point in 2 dimensional space.
     * 
     * @param p1 the first point.
     * @param p2 the second point
     * @return the angle.
     */
    public static double getAngle(final Pair<Double, Double> p1, final Pair<Double, Double> p2) {
        Double base = Math.toDegrees(-Math.abs(Math.atan((p1.getY() - p2.getY()) / (p1.getX() - p2.getX()))));
        if (p1.getX() < p2.getX()) {
            if (p1.getY() < p2.getY()) {
                base += 270;
            } else {
                base += 180;
            }
        } else {
            if (p1.getY() > p2.getY()) {
                base += 90;
            }
        }
        return base;
    }

    /**
     * 
     * @param pathXml path of file XML where the player data is.
     * @return {@link DataPlayer} of player.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<PlayerEnum, DataPlayer> enumFromXmlToDataPlayer(final String pathXml) {
        final Document xml = StaticMethodsUtils.getDocumentXML(pathXml);
        final Node nodeRoot = xml.getElementsByTagName("Entity").item(0);
        final NodeList playersNode = nodeRoot.getChildNodes();
        final String pathEnum = nodeRoot.getAttributes().getNamedItem("path-enum").getTextContent();
        final Map<PlayerEnum, DataPlayer> mapReturn = new HashMap<PlayerEnum, DataPlayer>();
        for (int i = 1; i < playersNode.getLength(); i = i + 2) {
            Enum<? extends PlayerEnum> playerEnum = BasicPlayerEnum.ISAAC;
            PlayerEnum playerName = BasicPlayerEnum.ISAAC;
            try {
                playerEnum = Enum.valueOf((Class<Enum>) Class.forName(pathEnum), playersNode.item(i).getNodeName());
                playerName = (PlayerEnum) Enum.valueOf((Class<Enum>) Class.forName(pathEnum), playersNode.item(i).getNodeName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            final Node node = xml.getElementsByTagName(playerEnum.name()).item(0);
            final DataPlayer dataPlayer = new DataPlayer();
            if (playerEnum.equals(BasicPlayerEnum.ISAAC)) {
                dataPlayer.setName(playerName)
                            .setDamage(DAMAGE_ISAAC)
                            .setLife(LIFE_ISAAC)
                            .setSpeed(SPEED_ISAAC)
                            .setRate(RATE_ISAAC);
            } else {
                dataPlayer.setName(playerName)
                            .setDamage(Double.parseDouble(node.getAttributes().getNamedItem("damage").getTextContent()))
                            .setLife(Double.parseDouble(node.getAttributes().getNamedItem("life").getTextContent()))
                            .setSpeed(Double.parseDouble(node.getAttributes().getNamedItem("speed").getTextContent()))
                            .setRate(Double.parseDouble(node.getAttributes().getNamedItem("rate").getTextContent())); 
            }
            mapReturn.put(playerName, dataPlayer);
        }
        return mapReturn;
    }

    /**
     * 
     * @param img the image
     * @return the new rotated {@link BufferedImage}
     */
    public static BufferedImage rotateImageBy90Degrees(final BufferedImage img) {

        final int width = img.getWidth();
        final int height = img.getHeight();

        final BufferedImage dest = new BufferedImage(height, width, img.getType());

        final Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(img, null);

        return dest;
    }

    /**
     * Checks if a class is a subclass of another class.
     * @param myClass the sub class
     * @param superClass the super class
     * @return true if a class is a subclass of another class
     */
    public static boolean isTypeOf(final Class<?> myClass, final Class<?> superClass) {
        if (myClass.equals(Object.class)) {
            return false;
        }
        boolean isSubclassOf = false;
        Class<?> clazz = myClass;
        if (!clazz.equals(superClass)) {
            clazz = clazz.getSuperclass();
            isSubclassOf = isTypeOf(clazz, superClass);
        } else {
            isSubclassOf = true;
        }
        return isSubclassOf;
    }
}
