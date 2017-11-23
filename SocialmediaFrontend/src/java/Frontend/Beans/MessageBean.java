/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Beans;

import Frontend.Clients.MessageClient;
import Frontend.Clients.UsersClient;
import Frontend.Viewmodels.TMessages;
import Frontend.Viewmodels.TUsers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Niklas
 * The MessageBean is used to move information to and from sendMessages.xhtml.
 * MessageBean can use the MessageClient to create new Messages and send them to
 * the backend REST services. The UsersClient is used to find users to send 
 * messages to. 
 */
@ManagedBean
@SessionScoped
public class MessageBean {
    private Long id;
    private Long receiverID;
    private UsersBean sender;
    private UsersBean receiver;
    private String messageText;
    private String preview;
    private boolean isRead;
    private boolean isDeleted;
    private Date timeSent;
    private List<TUsers> usersSelectListBeans;
    @ManagedProperty(value="#{usersBean}")
    private UsersBean usersBean;
    private UsersClient usersClient;
    private MessageClient messageClient;

    private Collection<TMessages> messages;

    public MessageBean() {
    }

    public void resetInfo() {
        id = new Long(-1);
        receiverID = new Long(-1);
        sender = null;
        receiver = null;
        messageText = ""; 
        usersSelectListBeans = null;
    }
    
    public Long getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(Long receiverID) {
        this.receiverID = receiverID;
    }

    public List<TUsers> getUsersSelectListBeans() {
        return usersSelectListBeans;
    }

    public void setUsersSelectListBeans(List<TUsers> usersSelectListBeans) {
        this.usersSelectListBeans = usersSelectListBeans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersBean getSender() {
        return sender;
    }

    public void setSender(UsersBean sender) {
        this.sender = sender;
    }

    public UsersBean getReceiver() {
        return receiver;
    }

    public void setReceiver(UsersBean receiver) {
        this.receiver = receiver;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public Collection<TMessages> getMessages() {
        return messages;
    }

    public void setMessages(Collection<TMessages> messages) {
        this.messages = messages;
    }

    public UsersBean getUsersBean() {
        return usersBean;
    }

    public void setUsersBean(UsersBean usersBean) {
        this.usersBean = usersBean;
    }
    
    public void getMessagesFromAll(UsersBean b) {
        messageClient = new MessageClient();
        messages = messageClient.getMessagesFromAll_XML(new GenericType<Collection<TMessages>>(){}, b.getId().toString());
        messageClient.close();
    }
    
    public void addNewMessage() {
        TMessages m = new TMessages();
        m.setIsDeleted(false);
        m.setIsRead(false);
        m.setMessageText(messageText);
        m.setTimeSent(new Date());
        usersClient = new UsersClient();
        m.setSenderid(usersClient.find_XML(new GenericType<TUsers>(){}, usersBean.getId().toString()));
        m.setReceiverid(usersClient.find_XML(new GenericType<TUsers>(){}, receiverID.toString()));
        usersClient.close();
        messageClient = new MessageClient();
        messageClient.create_XML(m);
        messageClient.close();
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
    
    public void getUsersByContains() {
        if (usersBean.getSearchForUser().length() > 0) {
            usersClient = new UsersClient();
            Collection<TUsers> tmp = usersClient.findUsersByContains_XML(new GenericType<Collection<TUsers>>(){}, usersBean.getSearchForUser());
            usersClient.close();
            usersSelectListBeans = new ArrayList();
            for (TUsers b : tmp) {
                usersSelectListBeans.add(b);
            }
        }
    }
    
    public void sendMessageToSelectedUser() {
        addNewMessage();
        usersBean.setSearchForUser("");
        setMessageText("");
        usersSelectListBeans.clear();
    }
}
