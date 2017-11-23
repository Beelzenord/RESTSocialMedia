/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectiontest;

import Frontend.Beans.PersonalLogBean;
import Frontend.Beans.UsersBean;
import Frontend.Clients.PersonalLogClient;
import Frontend.Viewmodels.TPersonalLog;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import javax.ws.rs.core.GenericType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fauzianordlund
 * This class Tests the Personal logs
 * from the frontend
 */

public class TestLog {
    private PersonalLogClient pc;
    private UsersBean tmpBean;
  
    /**
     * Constructor for the testlog
     */
    public TestLog() {
      PersonalLogClient pc = new PersonalLogClient();
      tmpBean = new UsersBean();   
      tmpBean.setUsername("u1");
      tmpBean.setPass("u1");
      tmpBean.logUser();
    }
    /**
     * Connects to the url of the backend.
     */
    @Test
    public void verifyConnectionToBackendSerivec(){
        try {
         URL url = new URL("http://localhost:8080/SocialmediaBackendREST/webresources/");
         HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
         urlConn.connect();
   
      } catch (IOException e) {
        System.err.println("Error creating HTTP connection");
        e.printStackTrace();
        
      }
    }
    /**
     *  Tests the posting by a user. 
     */
    @Test
    public void TestPostLog() {
    //first log the user and assume it's that user posting the log
      PersonalLogBean testLog = new PersonalLogBean();
      testLog.setUserBean(tmpBean);
      testLog.setSendersID(new Long(1));
      testLog.setText("test REST");
      testLog.addPost();
    }
    /**
     * Tests the retrieval of the log to an
     * assumed user.
     */
    @Test
    public void testGetLogs(){
      
      PersonalLogBean testLog = new PersonalLogBean();
      testLog.setUserBean(tmpBean);
      testLog.setSendersID(new Long(1));
      Collection<TPersonalLog> otherUsersLogs = testLog.getLogsOfOtherUser();
      
    }
   
}
