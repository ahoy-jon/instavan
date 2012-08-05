/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persist;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author waxzce
 */
@Entity
public class UploadedSincerelyEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @Expose
    private SincerelyEntry se;
    
    @OneToMany(cascade= CascadeType.ALL)
    @Expose
    private List<CardEntry> cards;
    
    public void addCard(CardEntry ce){
        if(cards==null){
            cards = new ArrayList<CardEntry>();
        }
        cards.add(ce);
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
        if (!(object instanceof UploadedSincerelyEntry)) {
            return false;
        }
        UploadedSincerelyEntry other = (UploadedSincerelyEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "us.companycamp.instavan.persist.UploadedSincerelyEntry[ id=" + id + " ]";
    }

    public List<CardEntry> getCards() {
        return cards;
    }

    public void setCards(List<CardEntry> cards) {
        this.cards = cards;
    }

    public SincerelyEntry getSe() {
        return se;
    }

    public void setSe(SincerelyEntry se) {
        this.se = se;
    }
    
    
}
