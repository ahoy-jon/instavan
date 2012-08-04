/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import us.companycamp.instavan.persist.SincerelyEntry;

/**
 *
 * @author waxzce
 */
@MessageDriven(mappedName = "jms/sclyq", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode",
    propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType",
    propertyValue = "javax.jms.Queue")})
public class UploaderManager implements MessageListener {

    @Inject
    private SincerelyEntryFacade sef;

    public void onMessage(Message message) {
        try {
            TextMessage tm = (TextMessage) message;
            SincerelyEntry se = sef.find(Long.parseLong(tm.getText()));

        } catch (JMSException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception occurred: {0}", e.toString());

        }
    }
}
