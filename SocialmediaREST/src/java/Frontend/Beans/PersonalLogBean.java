/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Beans;

import Frontend.Clients.PersonalLogClient;
import Frontend.Clients.UsersClient;
import Frontend.Viewmodels.TPersonalLog;
import Frontend.Viewmodels.TUsers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.core.GenericType;
//import static org.slf4j.helpers.Util.report;

/**
 *
 * @author Niklas
 */
@ManagedBean
@SessionScoped
public class PersonalLogBean {
    private String text;
    private Date timePosted;
    private Long id;
    private Collection<TPersonalLog> personalLogs;
    private Collection<TPersonalLog> otherUsersLogs;
    private PersonalLogClient personalLogClient = new PersonalLogClient();
    private UsersClient usersClient = new UsersClient();
    
    @ManagedProperty(value="#{usersBean}")
    private UsersBean userBean;
    
    private Long sendersID;
    
    
    Collection<TPersonalLog> usersBeanCollection = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Date timePosted) {
        this.timePosted = timePosted;
    }

    public Long getSendersID() {
        return sendersID;
    }

    public void setSendersID(Long sendersID) {
        this.sendersID = sendersID;
    }

    public Collection<TPersonalLog> getUsersBean() {
        return usersBeanCollection;
    }

    public void setUsersBean(Collection<TPersonalLog> usersBean) {
        this.usersBeanCollection = usersBean;
    
    }
    
    public void addPost() {
        TUsers u = usersClient.find_XML(new GenericType<TUsers>(){}, sendersID.toString());
        personalLogClient.create_XML(new TPersonalLog(this.text, new Date(), u));
    //   PersonalLogHandler.addPostToLog(this);
    }
    
    public UsersBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UsersBean userBean) {
        this.userBean = userBean;
    }
             
//    public  Collection<TPersonalLog> getAllLogs(){
//        client.
//        this.personalLogs = PersonalLogHandler.getPostsFromOneUser(username);
//        return personalLogs;
//    }
//    
//    public  Collection<PersonalLogBean> getLogsOfOtherUser(){
//       this.otherUsersLogs = PersonalLogHandler.getPostsFromOneUser(username);
//       return otherUsersLogs;
//    }
//
//    
//    public void findLogsForOtherUser() {
//        this.otherUsersLogs = PersonalLogHandler.getPostsFromOneUser(otherLogUsername);
//    
//    }
//    
//        public Collection<PersonalLogBean> getOtherUsersLogs() {
//        return otherUsersLogs;
//    }
//
//    public void setOtherUsersLogs(Collection<PersonalLogBean> otherUsersLogs) {
//        this.otherUsersLogs = otherUsersLogs;
//    }
//
//    public Collection<PersonalLogBean> getPersonalLogs() {
//        return personalLogs;
//    }
//
//    public void setPersonalLogs(Collection<PersonalLogBean> personalLogs) {
//        this.personalLogs = personalLogs;
//    }
    
}
