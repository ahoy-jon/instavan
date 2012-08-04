/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;

import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author waxzce
 */
@MessageDriven(mappedName = "jms/sclyq", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode",
    propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType",
    propertyValue = "javax.jms.Queue")}
)
public class UploaderManager implements MessageListener {

    public void onMessage(Message message) {
        Logger.getAnonymousLogger().info(message.toString());
    }
}
