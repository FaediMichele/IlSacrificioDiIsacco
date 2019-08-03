//package model.test;
//
//import org.junit.Test;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import model.enumeration.BasicPlayerEnum;
//import model.enumeration.PlayerEnum;
//import model.util.DataPlayer;
//import util.StaticMethodsUtils;
//import view.enumeration.PlayerMenuEnum;
//
///**
//* 
//* Class for the testing model.
//*
//*/
//public class TestXmlforInfoEtity {
//
//   private static final String ATTRIBUTE_SPEED = "speed";
//   private static final String ATTRIBUTE_DAMAGE = "damage";
//   private static final String ATTRIBUTE_LIFE = "life";
//   private static final String ATTRIBUTE_ENTITY = "Entity";
//   private static final String PATH = "path-enum";
//
//   /**
//    * Test for status xml file.
//    * 
//    * @throws ClassNotFoundException 
//    */
//   @Test
//   public void testStatusXML() throws ClassNotFoundException {
//       PlayerEnum plEnu = BasicPlayerEnum.CAIN;
//       Document xml = StaticMethodsUtils.getDocumentXML("/xml/model/dataPlayer.xml");
//       Node nodeRoot = xml.getElementsByTagName("Entity").item(0);
//       String str = nodeRoot.getAttributes().getNamedItem(PATH).getTextContent();
//       String tagEntity = plEnu.getValue().substring(str.length() + 1);
//       Node node = xml.getElementsByTagName(tagEntity).item(0);
//       DataPlayer dp = new DataPlayer().setDamage(Double.parseDouble(node.getAttributes().getNamedItem(ATTRIBUTE_DAMAGE).getTextContent()))
//                                       .setLife(Double.parseDouble(node.getAttributes().getNamedItem(ATTRIBUTE_LIFE).getTextContent()))
//                                       .setSpeed(Double.parseDouble(node.getAttributes().getNamedItem(ATTRIBUTE_SPEED).getTextContent()));
//
//   }
//
//   /**
//    * Test for status xml file.
//    * 
//    * @throws ClassNotFoundException 
//    */
//   @SuppressWarnings("unchecked")
//   @Test
//   public void enumFromViewToModel(final PlayerMenuEnum plEnumMenu, final String pathXml) throws ClassNotFoundException {
//       PlayerEnum plEnu = BasicPlayerEnum.CAIN;
//       Document xml = StaticMethodsUtils.getDocumentXML("/xml/controller/formEnumViewToEnumModel.xml");
//
//       Node nodeRoot = xml.getElementsByTagName("Entity").item(0);
//       String pathEnumModel = nodeRoot.getAttributes().getNamedItem("path-enum-model").getTextContent();
//       String pathEnumView = nodeRoot.getAttributes().getNamedItem("path-enum-view").getTextContent();
//       String enumValue = plEnumMenu.getValue().substring(pathEnumView.length() + 1);
//       Node node = xml.getElementsByTagName(enumValue).item(0);
//       System.out.println(pathEnumModel + " " + node.getTextContent());
//       System.out.println(Enum.valueOf((Class<Enum>) Class.forName(pathEnumModel), "CAIN"));
//       PlayerEnum plEnum = (PlayerEnum) Enum.valueOf((Class<Enum>) Class.forName(pathEnumModel), node.getNodeValue());
//   }
//
//}
