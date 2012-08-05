/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persist;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author waxzce
 */
@Entity
public class CardEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Expose
    @Basic
    private String previewUrl;
   
    @Expose
    @Basic
    private String printId;
    
    @Basic
    @Lob
    private byte[] pdf_file;

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
        if (!(object instanceof CardEntry)) {
            return false;
        }
        CardEntry other = (CardEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "us.companycamp.instavan.persist.CardEntry[ id=" + id + " ]";
    }

    public byte[] getPdf_file() {
        return pdf_file;
    }

    public void setPdf_file(byte[] pdf_file) {
        this.pdf_file = pdf_file;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }
    
}
