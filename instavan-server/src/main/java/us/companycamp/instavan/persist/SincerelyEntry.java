/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persist;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

/**
 *
 * @author waxzce
 */
@Entity
public class SincerelyEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    @OneToOne(cascade= CascadeType.ALL)
    private PeopleAndAdress sender;
    @Expose
    @OneToMany(cascade= CascadeType.ALL)
    private List<PeopleAndAdress> recipients;
    @Basic
    @Expose
    @Lob
    private String message;
    @Basic
    @Expose
    private Integer frontPhotoId;
    @Lob
    private byte[] frontPhoto;
    @Basic
    @Expose
    private Integer profilePhotoId;
    @Basic
    @Lob
    private byte[] profilePhoto;
    @Basic
    @Expose
    private Boolean testMode;
    @Basic
    @Expose
    private String uuid;

    @PrePersist
    private void generateUUID() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof SincerelyEntry)) {
            return false;
        }
        SincerelyEntry other = (SincerelyEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "us.companycamp.instavan.persist.SincerelyEntry[ id=" + id + " ]";
    }

    public Integer getFrontPhotoId() {
        return frontPhotoId;
    }

    public void setFrontPhotoId(Integer frontPhotoId) {
        this.frontPhotoId = frontPhotoId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(Integer profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    public List<PeopleAndAdress> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<PeopleAndAdress> recipients) {
        this.recipients = recipients;
    }

    public PeopleAndAdress getSender() {
        return sender;
    }

    public void setSender(PeopleAndAdress sender) {
        this.sender = sender;
    }

    public Boolean getTestMode() {
        return testMode;
    }

    public void setTestMode(Boolean testMode) {
        this.testMode = testMode;
    }

    public byte[] getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(byte[] frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
}
