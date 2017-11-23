/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fauzianordlund
 * This entity is a representation of a message sent from one user to another. 
 * A message has an id, a body message, the sender entity and the receiver entity.
 * A time stamp is taken when the message is sent and stored in the message entity.
 * The message and be read or not read as well as deleted or not delete, this
 * is marked with booleans. 
 */
@Entity
@Table(name = "T_Messages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMessages.findAll", query = "SELECT t FROM TMessages t")
    , @NamedQuery(name = "TMessages.findById", query = "SELECT t FROM TMessages t WHERE t.id = :id")
    , @NamedQuery(name = "TMessages.findByIsDeleted", query = "SELECT t FROM TMessages t WHERE t.isDeleted = :isDeleted")
    , @NamedQuery(name = "TMessages.findByIsRead", query = "SELECT t FROM TMessages t WHERE t.isRead = :isRead")
    , @NamedQuery(name = "TMessages.findByMessageText", query = "SELECT t FROM TMessages t WHERE t.messageText = :messageText")
    , @NamedQuery(name = "TMessages.findByTimeSent", query = "SELECT t FROM TMessages t WHERE t.timeSent = :timeSent")
    , @NamedQuery(name = "TMessages.findFromAll", query = "SELECT m FROM TMessages m WHERE m.receiverid.id = :Receiver_id")
    , @NamedQuery(name = "TMessages.findFromOneSender", query = "SELECT m FROM TMessages m WHERE m.receiverid.id = :Receiver_id AND m.senderid.id = :Sender_id")})

public class TMessages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isDeleted")
    private boolean isDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isRead")
    private boolean isRead;
    @Size(max = 255)
    @Column(name = "messageText")
    private String messageText;
    @Column(name = "timeSent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSent;
    @JoinColumn(name = "Receiver_id", referencedColumnName = "id")
    @ManyToOne
    @NotNull
    private TUsers receiverid;
    @JoinColumn(name = "Sender_id", referencedColumnName = "id")
    @ManyToOne
    @NotNull
    private TUsers senderid;

    public TMessages() {
    }

    public TMessages(Long id) {
        this.id = id;
    }

    public TMessages(Long id, boolean isDeleted, boolean isRead) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public TUsers getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(TUsers receiverid) {
        this.receiverid = receiverid;
    }

    public TUsers getSenderid() {
        return senderid;
    }

    public void setSenderid(TUsers senderid) {
        this.senderid = senderid;
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
        if (!(object instanceof TMessages)) {
            return false;
        }
        TMessages other = (TMessages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TMessages[ id=" + id + " ]";
    }
    
}
