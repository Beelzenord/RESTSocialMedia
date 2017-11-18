/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import client.NewJerseyClient;
import BO.UserBean;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class XMLParsingUser {
    private   NewJerseyClient client;
    private   DocumentBuilderFactory factory;// = DocumentBuilderFactory.newInstance();
    private   DocumentBuilder builder; //factory.newDocumentBuilder();
    private   InputStream inputStream;// = new    ByteArrayInputStream(log.getBytes());
    private   org.w3c.dom.Document document;// = builder.parse(inputStream);
        

    public XMLParsingUser() {
        //initialize the client
        client = new NewJerseyClient();
    }
    public UserBean verifyLogingUser(String username, String password){
        String XMLDocument = client.login_XML(String.class, username, password);
        this.document = initializeDocument(XMLDocument);
      //  this.document.normalize();
        UserBean tmp = parseUserXML(this.document);
        return tmp;
    }

    private org.w3c.dom.Document initializeDocument(String XML) {
        this.factory = DocumentBuilderFactory.newInstance();
        try {
            this.builder = factory.newDocumentBuilder();
            this.inputStream = new  ByteArrayInputStream(XML.getBytes());
            this.document =builder.parse(this.inputStream);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParsingUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLParsingUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLParsingUser.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return this.document;
    }
    private UserBean parseUserXML(org.w3c.dom.Document doc){
        NodeList nList = doc.getElementsByTagName("tUsers"); 
        
         UserBean user = new UserBean();
          for (int temp = 0; temp < nList.getLength(); temp++) {
                 
		Node nNode = nList.item(temp);
                //nNode.get
              // System.out.println(nNode.getChildNodes().item(1).getTextContent());
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
               
		if (nNode.getNodeType() == Node.ELEMENT_NODE)  {
                  // doc.getElementsByTagName()
			Element eElement = (Element) nNode;
                      //  user.setId(eElement.getElementsByTagName("id").item(0).getTextContent());
			user.setOccupation(eElement.getElementsByTagName("occupation").item(0).getTextContent());
			user.setUsername(eElement.getElementsByTagName("username").item(0).getTextContent());
			user.setPassword(eElement.getElementsByTagName("pass").item(0).getTextContent());

		}
             
	}
        
        return user;
    }
}
