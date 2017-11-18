/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 *
 * @author fauzianordlund
 */

public class myMain {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        NewJerseyClient client = new NewJerseyClient();
        String log = client.login_XML(String.class, "u1", "u1");
        System.out.println(client.login_XML(String.class, "u1", "u1"));
        
     //   System.out.println(String.valueOf(client.login_XML(String.class, "u1", "u1")));
        
        
        
       
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream inputStream = new    ByteArrayInputStream(log.getBytes());
        org.w3c.dom.Document document = builder.parse(inputStream);
        document.normalize();
        System.out.println("Root element :" + document.getDocumentElement().getNodeName());
        
        NodeList nList = document.getElementsByTagName("tUsers"); 
        
       UserBean u = new UserBean();
          for (int temp = 0; temp < nList.getLength(); temp++) {
                 
		Node nNode = nList.item(temp);
                //nNode.get
              // System.out.println(nNode.getChildNodes().item(1).getTextContent());
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
                
		if (nNode.getNodeType() == Node.ELEMENT_NODE)  {
                       // doc.getElementsByTagName()
			Element eElement = (Element) nNode;
                       
                        //("Id: " + eElement.getElementsByTagName("id").item(0).getTextContent());
                        // airport.setStationId(first.getElementsByTagName("station_id").item(0).getTextContent());
                        
			System.out.println(eElement.getElementsByTagName("occupation").item(0).getTextContent());
			u.setUsername(eElement.getElementsByTagName("username").item(0).getTextContent());
	//		u.setPassword(eElement.getElementsByTagName("pass").item(0).getTextContent());

		}
             
	}
          
    }
}
       /* NodeList nList = document.getElementsByTagName("tUsers"); 
        
        
          for (int temp = 0; temp < nList.getLength(); temp++) {
                 
		Node nNode = nList.item(temp);
                //nNode.get
              // System.out.println(nNode.getChildNodes().item(1).getTextContent());
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
               
		if (nNode.getNodeType() == Node.ELEMENT_NODE)  {
                  // doc.getElementsByTagName()
			Element eElement = (Element) nNode;
                        System.out.println("Id: " + eElement.getElementsByTagName("id").item(0).getTextContent());
			System.out.println("Occupation : " + eElement.getElementsByTagName("occupation").item(0).getTextContent());
			System.out.println("username : " + eElement.getElementsByTagName("username").item(0).getTextContent());
			System.out.println("pass : " + eElement.getElementsByTagName("pass").item(0).getTextContent());

		}
             
	}
          inputStream.close();*/
    