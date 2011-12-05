/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** Provvisorio, forse controllore locale sta diventando troppo grasso
 * valutiamo se spezzare e fare un controllore piatto
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Stateless
public class ControllorePiatti implements ControllorePiattiLocal {
    @EJB
    private PiattoFacadeLocal piattoFacade;

    @Override
    public void addPersistency(List<Piatto> lista) {
        for (Piatto pp : lista){
            piattoFacade.create(pp);
        }
        
    }
    public void addPiatto(Piatto p){
        piattoFacade.create(p);
    }
    
}
