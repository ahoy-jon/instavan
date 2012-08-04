/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import us.companycamp.instavan.persist.PictureEntry;

/**
 *
 * @author waxzce
 */
@Stateless
public class PictureEntryFacade extends AbstractFacade<PictureEntry> {
    @PersistenceContext(unitName = "us.companycamp_instavan_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PictureEntryFacade() {
        super(PictureEntry.class);
    }
    
}
