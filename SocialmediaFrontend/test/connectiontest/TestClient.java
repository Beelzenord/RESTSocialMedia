/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectiontest;

import Frontend.Beans.UsersBean;
import Frontend.Clients.UsersClient;
import Frontend.Viewmodels.TUsers;
import com.sun.faces.action.RequestMapping;
import java.io.IOException;
import static java.lang.Compiler.enable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.apache.catalina.WebResource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fauzianordlund
 * This class tests user operations on the FRONTENDS
 * 
 * side.
 */
public class TestClient{
    private UsersClient client;
   
    private Client tmpClient;
    private String uri =  "http://localhost:8080/SocialmediaBackendREST/webresources/";
    
    public TestClient() {
        client = new UsersClient();
    }
    /**
     * Tests the url connection to the backend.
     */
    @Test
    public void verifyConnectionToBackendSerivec(){
        try {
        URL url = new URL(uri);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.connect();
     //   assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
    } catch (IOException e) {
        System.err.println("Error creating HTTP connection");
        e.printStackTrace();
        
      }
    }
    /**
     * Tests the login of a specific user.
     */
    @Test
    public void TestLogin(){
      TUsers JunitClient = client.login_XML(new GenericType<TUsers>(){}, "u1", "u1");
       assertEquals("u1", JunitClient.getUsername());
       assertEquals("u1", JunitClient.getPass());
    }
    /**
     * Tests the creation of a user.
     */
    @Test
    public void TestCreate(){
        
      // test add users, by invoking the method in from the bean object
      UsersBean userBean = new UsersBean();
      userBean.setUsername("testUser");
      userBean.setUsername("testPassword");
      userBean.setOccupation("testOccupation");
      userBean.addUser();

    }
    /**
     * Tests the removal of the user.
     */
    @Test
    public void TestRemove(){
        client.remove("2");
    }
}
