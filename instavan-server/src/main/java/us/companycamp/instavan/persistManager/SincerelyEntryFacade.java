/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.resource.ResourceException;
import us.companycamp.instavan.persist.SincerelyEntry;

/**
 *
 * @author waxzce
 */
@Stateless
public class SincerelyEntryFacade extends AbstractFacade<SincerelyEntry> {

    @PersistenceContext(unitName = "us.companycamp_instavan_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SincerelyEntryFacade() {
        super(SincerelyEntry.class);
    }
    @Resource(lookup = "jms/cof")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/sclyq")
    private Queue queue;

    public void test(String s) throws ResourceException {
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;
        TextMessage message = null;
        final int NUM_MSGS = 3;

        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            messageProducer = session.createProducer(queue);
            message = session.createTextMessage();

            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Sending message: " + message.getText());
                messageProducer.send(message);
            }

            System.out.println("To see if the bean received the messages,");
            System.out.println(
                    " check <install_dir>/domains/domain1/logs/server.log.");
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
    }
}
