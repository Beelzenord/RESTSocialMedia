/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maintest;

import Frontend.Beans.PersonalLogBean;
import Frontend.Beans.UsersBean;
import Frontend.Clients.MessageClient;
import Frontend.Clients.PersonalLogClient;
import Frontend.Clients.UsersClient;
import Frontend.Viewmodels.TPersonalLog;
import Frontend.Viewmodels.TUsers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Niklas
 */
public class Maintest {
    public static void main(String argv[]) {

    try {
        UsersClient c = new UsersClient();
        Frontend.Viewmodels.TUsers t = new Frontend.Viewmodels.TUsers();
        t.setUsername("t5");
        t.setPass("t5");
        t.setOccupation("viewmodel");
        //c.create_XML(t);
        System.out.println("hej");
        Frontend.Viewmodels.TUsers u = c.login_XML(new GenericType<Frontend.Viewmodels.TUsers>(){}, "u1", "u1");
        System.out.println("Login\n" + u.toString() + "\n/login");
        PersonalLogBean b = new PersonalLogBean();
        b.setSendersID(new Long(1));
        b.setText("test2 REST");
        //b.addPost();
        PersonalLogClient pc = new PersonalLogClient();
        System.out.println("post sent");
        Collection<TPersonalLog> logs = pc.getPostsFromOneUsername_XML(new GenericType<Collection<TPersonalLog>>(){}, "u1");
        for (TPersonalLog z: logs) {
            System.out.println(z.toString());
        }
        System.out.println("done");
        MessageClient mc = new MessageClient();
        Collection<TUsers> tmp = c.findUsersByContains_XML(new GenericType<Collection<TUsers>>(){}, "u");
        for (TUsers o : tmp) {
            System.out.println(o.toString());
        }
        System.out.println("done2");
       
        //ClientResponse clientResponse = new ClientResponse();
        /*
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(XMLConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        */
        //ClientResponse  cr = c.findRange_XML(ClientResponse.class , "2", "6");
        GenericType<List<TUsers>> gType = new GenericType<List<TUsers>>(){}; 
        
        //List<TUsers> users = new ArrayList<TUsers>();
        List<TUsers> users = c.findRange_XML(gType, "2", "6");
        //users = (List<TUsers>) (cr.getEntity(gType));
        //users=(List<TUsers>)(cr.getEntity(gType));
        //users = (List<TUsers>)cr.getEntity(gType);
        for (TUsers a : users) {
            //System.out.println(a.toString());
        }
        
//TUsers users = c.findRange_XML(TUsers.class , "2", "6");
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
}
