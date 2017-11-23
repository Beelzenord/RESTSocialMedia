/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectiontest;

import Frontend.Beans.MessageBean;
import Frontend.Beans.PersonalLogBean;
import Frontend.Beans.UsersBean;
import Frontend.Clients.MessageClient;
import Frontend.Viewmodels.TMessages;
import Frontend.Viewmodels.TUsers;
import com.sun.faces.util.CollectionsUtils;
import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
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
 * This class Tests the Frontends messages
 */
public class TestMessages {
    private MessageClient msgTest;
    private WebTarget webTarget;
    private MessageBean testMessageBean;
    
    public TestMessages() {
        msgTest = new MessageClient();
        testMessageBean = new MessageBean();
    }
    /**
     * Tests the sending of a message.
     */
    @Test
    public void sendMessage() {
        long receiverID = new Long(2);
        UsersBean testBean = new UsersBean();
        long senderID = new Long(1);
        testBean.setId(senderID);
        testMessageBean.setUsersBean(testBean);
        testMessageBean.setMessageText("test");
        testMessageBean.setReceiverID(receiverID);
        testMessageBean.addNewMessage();
    }
    /**
     * Tests the retrieval of a message from u3 to u1.
     */
    @Test
    public void testGetMessage(){
        Collection<TMessages> testMessages = msgTest.getMessagesFromOneSender_XML(new GenericType<Collection<TMessages>>(){}, "1", "3");
        ArrayList<TMessages> arrayTestMessages = new ArrayList<TMessages>();
        
        for (TMessages b : testMessages ){
           arrayTestMessages.add(b);
        }
        TMessages tmp = testMessages.iterator().next();
        Long sender = new Long(3);
        Long receiver = new Long(1);
        
        assertEquals("u1", tmp.getReceiverid().getUsername());
        
        assertEquals("u3", tmp.getSenderid().getUsername());
        
    }
   
    /*
       Test uri access
       Test get Message
       Test read Message
       Test 
    */
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
