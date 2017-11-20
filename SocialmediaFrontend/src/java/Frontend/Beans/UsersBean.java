/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Beans;

import Frontend.Clients.UsersClient;
import Frontend.Viewmodels.TUsers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author fauzianordlund
 */
@ManagedBean
@SessionScoped
public class UsersBean {
    private Long id;
    
    private String username;
    private String pass;
    private String occupation;
    private String searchForUser;
    private List<TUsers> usersSelectListBeans;
    private UsersClient client = new UsersClient();
    
    /**
     * Creates a new instance of UserBean
     */
    public UsersBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSearchForUser() {
        return searchForUser;
    }

    public void setSearchForUser(String searchForUser) {
        this.searchForUser = searchForUser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<TUsers> getUsersSelectListBeans() {
        return usersSelectListBeans;
    }

    public void setUsersSelectListBeans(List<TUsers> usersSelectListBeans) {
        this.usersSelectListBeans = usersSelectListBeans;
    }

    public void addUser(){
        TUsers tmp = new TUsers(this.username, this.pass, this.occupation);
        client.create_XML(tmp);
    }
    public String logUser() {
        TUsers Real = client.login_XML(new GenericType<TUsers>(){}, username, pass);

        if (Real != null) {
            this.id = Real.getId();
            this.occupation = Real.getOccupation();
            this.username = Real.getUsername();
            resetInfo();
            return "main";
        } else {
            return "failure";
        }
    }
    
    public void resetInfo() {
        searchForUser = "";
    }
        
    public void getUsersByContains() {
        if (searchForUser.length() > 0) {
            Collection<TUsers> tmp = client.findUsersByContains_XML(new GenericType<Collection<TUsers>>(){}, searchForUser);
            usersSelectListBeans = new ArrayList();
            for (TUsers b : tmp) {
                usersSelectListBeans.add(b);
            }
        }
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UsersBean)) {
            return false;
        }

        UsersBean other = (UsersBean) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }


    
    
    

}
