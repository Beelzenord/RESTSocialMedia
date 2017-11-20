/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Viewmodels;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Niklas
 */
@XmlRootElement
public class TPersonalLog {
    private Long id;
    private String text;
    private Date timePosted;
    private TUsers senderid;

    public TPersonalLog(String text, Date timePosted, TUsers senderid) {
        this.text = text;
        this.timePosted = timePosted;
        this.senderid = senderid;
    }

    public TPersonalLog() {
    }

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

    public TUsers getSenderid() {
        return senderid;
    }

    public void setSenderid(TUsers senderid) {
        this.senderid = senderid;
    }
    
    
}
