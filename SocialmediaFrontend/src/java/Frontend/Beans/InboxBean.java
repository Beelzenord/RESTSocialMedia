/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Beans;

import Frontend.Clients.MessageClient;
import Frontend.Viewmodels.TMessages;
import Frontend.Viewmodels.TUsers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Niklas
 */
@ManagedBean
@SessionScoped
public class InboxBean {
    private Long senderID;
    private DataModel<TMessages> messageTable;
    @ManagedProperty(value="#{usersBean}")
    private UsersBean usersBean;
    @ManagedProperty(value="#{messageBean}")
    private MessageBean messageBean;
    private List<TUsers> usersWhoSentMessagesToThisUser;
    private String singleMessage;
    private MessageClient messageClient = new MessageClient();
    
    public void getMessagesFromOneSender() {
        Collection<TMessages> tmp = messageClient.getMessagesFromOneSender_XML(new GenericType<Collection<TMessages>>(){}, usersBean.getId().toString(), senderID.toString());
        messageTable = new ListDataModel<TMessages>();
        if (tmp.size() > 0) {
            messageTable.setWrappedData(tmp);
        }
    }
    
    public void presentSingleMessage() {
        TMessages b = (TMessages)messageTable.getRowData();
        singleMessage = b.getMessageText();
        if (!b.getIsRead()) {
            messageClient.setMessageToIsRead_XML(b);
            b.setIsRead(true);
        }
    }

    public void getSendersToThisUser() {
        Collection<TMessages> tmp = messageClient.getMessagesFromAll_XML(new GenericType<Collection<TMessages>>(){}, usersBean.getId().toString());
        usersWhoSentMessagesToThisUser = new ArrayList();
        for (TMessages b : tmp) {
            for (TUsers u : usersWhoSentMessagesToThisUser) {
                if (u.getId().equals(b.getSenderid().getId()))
                    usersWhoSentMessagesToThisUser.remove(u);
            }
            //if (!usersWhoSentMessagesToThisUser.contains(b.getSenderid()))
                usersWhoSentMessagesToThisUser.add(b.getSenderid());

        }
    }

    public UsersBean getUsersBean() {
        return usersBean;
    }

    public void setUsersBean(UsersBean usersBean) {
        this.usersBean = usersBean;
    }

    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }

    public Long getSenderID() {
        return senderID;
    }

    public void setSenderID(Long senderID) {
        this.senderID = senderID;
    }

    public List<TUsers> getUsersWhoSentMessagesToThisUser() {
        return usersWhoSentMessagesToThisUser;
    }

    public void setUsersWhoSentMessagesToThisUser(List<TUsers> usersWhoSentMessagesToThisUser) {
        this.usersWhoSentMessagesToThisUser = usersWhoSentMessagesToThisUser;
    }

    public DataModel<TMessages> getMessageTable() {
        return messageTable;
    }

    public void setMessageTable(DataModel<TMessages> messageTable) {
        this.messageTable = messageTable;
    }

    public String getSingleMessage() {
        return singleMessage;
    }

    public void setSingleMessage(String singleMessage) {
        this.singleMessage = singleMessage;
    }

}