/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import us.companycamp.instavan.persist.SincerelyEntry;
import us.companycamp.instavan.persist.UploadedSincerelyEntry;

/**
 *
 * @author waxzce
 */
@Stateless
public class UploadedSincerelyEntryFacade extends AbstractFacade<UploadedSincerelyEntry> {

    @PersistenceContext(unitName = "us.companycamp_instavan_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Inject
    SincerelyEntryFacade sef;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UploadedSincerelyEntryFacade() {
        super(UploadedSincerelyEntry.class);
    }

    public UploadedSincerelyEntry getBySincerelyEntryUUID(String uuid) {
        SincerelyEntry se = sef.getByUUID(uuid);
        CriteriaQuery<UploadedSincerelyEntry> cq = em.getCriteriaBuilder().createQuery(UploadedSincerelyEntry.class);
        Root<UploadedSincerelyEntry> r = cq.from(UploadedSincerelyEntry.class);

        cq.select(r).where(em.getCriteriaBuilder().equal(r.get("se"), se));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
