/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.JMSException;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@MessageDriven(mappedName = "jms/codaEventi", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "JMSEventi"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "JMSEventi")
})
public class JMSEventi implements MessageListener {
    
    /**
     * 
     */
    public JMSEventi() {
    }
    
    /**Ricevitore dei messaggi del topic rappersentato nel jms codaEventi
     * 
     * @param message Il messagio ricevuto
     */
    @Override
    public void onMessage(Message message) {
        String e = null;
        if (message instanceof ObjectMessage){
           ObjectMessage msg = (ObjectMessage) message;
           try{
                e = (String) msg.getObject();
                System.out.println("Raccolto l'oggetto evento: "+e);
           }
           catch(JMSException ex){
               System.err.println("Errore durante la ricezione del messaggio jms");
           }
           //Completare
        }
    }
}
