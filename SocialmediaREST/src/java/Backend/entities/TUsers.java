/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fauzianordlund
 */
@Entity
@Table(name = "T_Users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TUsers.findAll", query = "SELECT t FROM TUsers t")
    , @NamedQuery(name = "TUsers.findById", query = "SELECT t FROM TUsers t WHERE t.id = :id")
    , @NamedQuery(name = "TUsers.findByOccupation", query = "SELECT t FROM TUsers t WHERE t.occupation = :occupation")
    , @NamedQuery(name = "TUsers.findByPass", query = "SELECT t FROM TUsers t WHERE t.pass = :pass")
    , @NamedQuery(name = "TUsers.findByUsername", query = "SELECT t FROM TUsers t WHERE t.username = :username")})
public class TUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "occupation")
    private String occupation;
    @Size(max = 255)
    @Column(name = "pass")
    private String pass;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @JoinTable(name = "T_Friends", joinColumns = {
        @JoinColumn(name = "user_id1", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id2", referencedColumnName = "id")})
    @ManyToMany
    private Collection<TUsers> tUsersCollection;
    @ManyToMany(mappedBy = "tUsersCollection")
    private Collection<TUsers> tUsersCollection1;
    @OneToMany(mappedBy = "senderid")
    private Collection<TPersonalLog> tPersonalLogCollection;
    @OneToMany(mappedBy = "receiverid")
    private Collection<TMessages> tMessagesCollection;
    @OneToMany(mappedBy = "senderid")
    private Collection<TMessages> tMessagesCollection1;

    public TUsers() {
    }

    public TUsers(String occupation, String pass, String username) {
        this.occupation = occupation;
        this.pass = pass;
        this.username = username;
    }
    
    public TUsers(Long id) {
        this.id = id;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public Collection<TUsers> getTUsersCollection() {
        return tUsersCollection;
    }

    public void setTUsersCollection(Collection<TUsers> tUsersCollection) {
        this.tUsersCollection = tUsersCollection;
    }

    @XmlTransient
    public Collection<TUsers> getTUsersCollection1() {
        return tUsersCollection1;
    }

    public void setTUsersCollection1(Collection<TUsers> tUsersCollection1) {
        this.tUsersCollection1 = tUsersCollection1;
    }

    @XmlTransient
    public Collection<TPersonalLog> getTPersonalLogCollection() {
        return tPersonalLogCollection;
    }

    public void setTPersonalLogCollection(Collection<TPersonalLog> tPersonalLogCollection) {
        this.tPersonalLogCollection = tPersonalLogCollection;
    }

    @XmlTransient
    public Collection<TMessages> getTMessagesCollection() {
        return tMessagesCollection;
    }

    public void setTMessagesCollection(Collection<TMessages> tMessagesCollection) {
        this.tMessagesCollection = tMessagesCollection;
    }

    @XmlTransient
    public Collection<TMessages> getTMessagesCollection1() {
        return tMessagesCollection1;
    }

    public void setTMessagesCollection1(Collection<TMessages> tMessagesCollection1) {
        this.tMessagesCollection1 = tMessagesCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TUsers)) {
            return false;
        }
        TUsers other = (TUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TUsers[ id=" + id + " ]";
    }
    
}
