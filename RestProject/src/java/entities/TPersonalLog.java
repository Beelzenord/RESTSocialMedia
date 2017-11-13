/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fauzianordlund
 */
@Entity
@Table(name = "T_PersonalLog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPersonalLog.findAll", query = "SELECT t FROM TPersonalLog t")
    , @NamedQuery(name = "TPersonalLog.findById", query = "SELECT t FROM TPersonalLog t WHERE t.id = :id")
    , @NamedQuery(name = "TPersonalLog.findByText", query = "SELECT t FROM TPersonalLog t WHERE t.text = :text")
    , @NamedQuery(name = "TPersonalLog.findByTimePosted", query = "SELECT t FROM TPersonalLog t WHERE t.timePosted = :timePosted")})
public class TPersonalLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "text")
    private String text;
    @Column(name = "timePosted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timePosted;
    @JoinColumn(name = "Sender_id", referencedColumnName = "id")
    @ManyToOne
    private TUsers senderid;

    public TPersonalLog() {
    }

    public TPersonalLog(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPersonalLog)) {
            return false;
        }
        TPersonalLog other = (TPersonalLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TPersonalLog[ id=" + id + " ]";
    }
    
}
