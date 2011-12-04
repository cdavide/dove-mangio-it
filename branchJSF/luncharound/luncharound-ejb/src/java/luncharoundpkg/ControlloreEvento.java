/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Stateless
public class ControlloreEvento implements ControlloreEventoLocal {

    @EJB
    private EventoFacadeLocal eventoFacade;

    @Override
    public void addEvento() {

        
    }
    
}
