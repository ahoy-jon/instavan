/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.resource.ResourceException;
import us.companycamp.instavan.persist.PictureEntry;
import us.companycamp.instavan.persist.SincerelyEntry;

/**
 *
 * @author waxzce
 */
@Stateless
public class SincerelyEntryFacade extends AbstractFacade<SincerelyEntry> {

    @PersistenceContext(unitName = "us.companycamp_instavan_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Resource(lookup = "jms/cof")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/sclyq")
    private Queue queue;
    private Connection connection = null;
    private Session session = null;
    private MessageProducer messageProducer = null;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SincerelyEntryFacade() {
        super(SincerelyEntry.class);
    }

    @PostConstruct
    private void activateJMS() throws JMSException {
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(queue);
    }

    @PreDestroy
    private void deadJMS() throws JMSException {
        connection.close();

    }

    public void launchPrintProcedure(SincerelyEntry s) throws ResourceException {
        TextMessage message = null;

        try {
            message = session.createTextMessage();
            message.setText(s.getId().toString());
            messageProducer.send(message);

        } catch (JMSException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception occurred: {0}", e.toString());
        }
    }

    public SincerelyEntry getByUUID(String uuid) {
        CriteriaQuery<SincerelyEntry> cq = em.getCriteriaBuilder().createQuery(SincerelyEntry.class);
        Root<SincerelyEntry> r = cq.from(SincerelyEntry.class);

        cq.select(r).where(em.getCriteriaBuilder().equal(r.get("uuid"), uuid));

        return em.createQuery(cq).getSingleResult();
    }
}
