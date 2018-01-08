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
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Niklas
 * The PersonalLogBean is used to create new posts to a users log. It can also
 * be used to find other users logs. Any users log can be found given you can find
 * the user when searching for the username. 
 */
@ManagedBean
@SessionScoped
public class PersonalLogBean {
    private String text;
    private Date timePosted;
    private Long id;
    private String otherLogUsername;
    private Collection<TPersonalLog> personalLogs;
    private Collection<TPersonalLog> otherUsersLogs;
    private PersonalLogClient personalLogClient;
    private UsersClient usersClient;
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
    
    public String getOtherLogUsername() {
        return otherLogUsername;
    }

    public void setOtherLogUsername(String otherLogUsername) {
        this.otherLogUsername = otherLogUsername;
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
        usersClient = new UsersClient();
        TUsers u = usersClient.find_JSON(new GenericType<TUsers>(){}, userBean.getId().toString());
        usersClient.close();
        personalLogClient = new PersonalLogClient();
        personalLogClient.create_JSON(new TPersonalLog(this.text, new Date(), u));
        personalLogClient.close();
        this.text = "";
    }
    
    public UsersBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UsersBean userBean) {
        this.userBean = userBean;
    }
             
    public  Collection<TPersonalLog> getAllLogs(){
        personalLogClient = new PersonalLogClient();
        this.personalLogs = personalLogClient.getPostsFromOneUsername_JSON(new GenericType<Collection<TPersonalLog>>(){}, userBean.getUsername());
        personalLogClient.close();
        return personalLogs;
    }
    
    public  Collection<TPersonalLog> getLogsOfOtherUser(){
        personalLogClient = new PersonalLogClient();
        this.otherUsersLogs = personalLogClient.getPostsFromOneUsername_JSON(new GenericType<Collection<TPersonalLog>>(){}, userBean.getUsername());
        personalLogClient.close();
        return otherUsersLogs;
    }

    
    public void findLogsForOtherUser() {
        personalLogClient = new PersonalLogClient();
        this.otherUsersLogs = personalLogClient.getPostsFromOneUsername_JSON(new GenericType<Collection<TPersonalLog>>(){}, otherLogUsername);
        personalLogClient.close();
    }
    
    public Collection<TPersonalLog> getOtherUsersLogs() {
        return otherUsersLogs;
    }

    public void setOtherUsersLogs(Collection<TPersonalLog> otherUsersLogs) {
        this.otherUsersLogs = otherUsersLogs;
    }

    public Collection<TPersonalLog> getPersonalLogs() {
        return personalLogs;
    }

    public void setPersonalLogs(Collection<TPersonalLog> personalLogs) {
        this.personalLogs = personalLogs;
    }
    
}
