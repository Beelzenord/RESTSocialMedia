/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;

import client.NewJerseyClient;
import parser.XMLParsingUser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
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
@ManagedBean
@RequestScoped
public class UserBean {
    private String username;
    private String password;
    private String occupation;
    private NewJerseyClient njc;
    private Long id;
    private XMLParsingUser parser;

    
    public Long getId() {
        return id;
    }

    /**
     * Creates a new instance of UserBean
     */
    public void setId(Long id) {    
        this.id = id;
    }

    public UserBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String doLogin() throws SAXException, IOException, ParserConfigurationException{
      // njc = new NewJerseyClient();
      // return njc.login_XML(String.class, "u1", "u1");
      // it should excute the thing
       parser = new XMLParsingUser();
       UserBean tmp =parser.verifyLogingUser(this.username, this.password);
  
       if(tmp!=null){
         //  this.occupation = tmp.getOccupation();
           return "main";
       }
       else{
           return "failure";
       }
       
    }
   
    
}
