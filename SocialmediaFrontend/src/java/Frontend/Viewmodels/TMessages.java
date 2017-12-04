/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Viewmodels;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Niklas
 * This viewmodel is a representation of a message sent from one user to another. 
 * A message has an id, a body message, the sender viewmodel and the receiver viewmodel.
 * A time stamp is taken when the message is sent and stored in the message viewmodel.
 * The message and be read or not read as well as deleted or not delete, this
 * is marked with booleans. 
 */
@XmlRootElement
public class TMessages {
    
    private Long id;
    private boolean isDeleted;
    private boolean isRead;
    private String messageText;
    private Date timeSent;
    private TUsers receiverid;
    private TUsers senderid;
    private String preview;

    public TMessages() {
    }

    public TMessages(Long id, boolean isDeleted, boolean isRead, String messageText, Date timeSent, TUsers receiverid, TUsers senderid) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.isRead = isRead;
        this.messageText = messageText;
        this.timeSent = timeSent;
        this.receiverid = receiverid;
        this.senderid = senderid;
        this.preview = StringUtils.abbreviate(messageText, 40);
    }
    
    public TMessages(boolean isDeleted, boolean isRead, String messageText, Date timeSent, TUsers receiverid, TUsers senderid) {
        this.isDeleted = isDeleted;
        this.isRead = isRead;
        this.messageText = messageText;
        this.timeSent = timeSent;
        this.receiverid = receiverid;
        this.senderid = senderid;
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

    public String getPreview() {
        return StringUtils.abbreviate(messageText, 40);
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    @Override
    public String toString() {
        return "TMessages{" + "id=" + id + ", isDeleted=" + isDeleted + ", isRead=" + isRead + ", messageText=" + messageText + ", timeSent=" + timeSent + ", receiverid=" + receiverid + ", senderid=" + senderid + '}';
    }
    
    
    
}
