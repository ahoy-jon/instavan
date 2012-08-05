/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import us.companycamp.instavan.persist.PeopleAndAdress;

/**
 *
 * @author waxzce
 */
@Stateless
public class PeopleAndAdressFacade extends AbstractFacade<PeopleAndAdress> {
    @PersistenceContext(unitName = "us.companycamp_instavan_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeopleAndAdressFacade() {
        super(PeopleAndAdress.class);
    }
    
}
