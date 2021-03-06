/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Viewmodels;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Niklas
 * This viewmodel represents Users in the database. 
 * A user can send messages to other users and create posts to their log.
 * The user entity has an id, an occupation, a username and a password.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TUsers implements Serializable{
    private Long id;
    private String username;
    private String pass;
    private String occupation;

    public TUsers() {
        
    }

    public TUsers(Long id) {
        this.id = id;
    }

    public TUsers(Long id, String username, String pass, String occupation) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.occupation = occupation;
    }

    public TUsers(String username, String pass, String occupation) {
        this.username = username;
        this.pass = pass;
        this.occupation = occupation;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "UserViewModel{" + "id=" + id + ", username=" + username + ", pass=" + pass + ", occupation=" + occupation + '}';
    }
    
    
}
