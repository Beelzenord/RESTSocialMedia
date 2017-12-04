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
import Frontend.Viewmodels.TMessages;
import Frontend.Viewmodels.TPersonalLog;
import Frontend.Viewmodels.TUsers;
import java.util.Date;
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
        UsersClient uc = new UsersClient();
        TUsers u1 = uc.find_XML(new GenericType<TUsers>(){}, "1");
        TUsers u2 = uc.find_XML(new GenericType<TUsers>(){}, "2");
        System.out.println(u1.toString());
        System.out.println(u2.toString());
        System.out.println("users lul");
        
//        Collection<TUsers> users = uc.findUsersByContains_XML(new GenericType<Collection<TUsers>>(){}, "efsdf");
//        for (TUsers u : users) 
//            System.out.println(u.toString());
        MessageClient mc = new MessageClient();
        
        Collection<TMessages> asd = mc.getMessagesFromAll_JSON(new GenericType<Collection<TMessages>>(){}, "1");
        System.out.println(asd.size());
        for (TMessages u : asd) {
            System.out.println(u.toString());
        }
        TMessages newmess = new TMessages();
        newmess.setIsRead(false);
        newmess.setIsDeleted(false);
        newmess.setMessageText("new message node rest from u1 to u2");
        newmess.setTimeSent(new Date());
        newmess.setReceiverid(u2);
        newmess.setSenderid(u1);
        PersonalLogClient pc = new PersonalLogClient();
        Collection<TPersonalLog> logs = pc.getPostsFromOneUsername_XML(new GenericType<Collection<TPersonalLog>>(){}, "u2");
        for (TPersonalLog p : logs) {
            System.out.println(p.toString());
        }
//        TMessages m = mc.find_JSON(new GenericType<TMessages>(){}, "5");
//        System.out.println("\n" + m.toString());
//        TMessages mess = mc.getMessagesFromAll_JSON(new GenericType<TMessages>(){}, "1");
//        System.out.println(mess.toString());

//        Collection<TMessages> col = mc.getMessagesFromAll_XML(new GenericType<Collection<TMessages>>(){}, "1");
//        for (TMessages m : col) {
//            System.out.println(m.toString());
//        }
        
        
        
        /*UsersClient c = new UsersClient();
        Response r = c.test();
        Collection<TUsers> list = r.readEntity(new GenericType<Collection<TUsers>>(){});
        for (TUsers u : list) {
            System.ou   t.println(u.toString());
        }
        
        System.out.println("done");
        TUsers u2 = c.login_XML(new GenericType<TUsers>(){}, "u1", "u1");
        System.out.println("Login: " + u2.toString());
        */
        
        /*TUsers u = c.login_XML(new GenericType<TUsers>(){}, "u1", "u1");
        System.out.println(u.toString());
        System.out.println("done2");
        TUsers u2 = c.login_JSON(new GenericType<TUsers>(){}, "u1", "u1");
        System.out.println(u2.toString());*/
       
        
        
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
}
