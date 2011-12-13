/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Stateless
public class ControlloreEventi implements ControlloreEventiLocal {

    @EJB
    private EventoFacadeLocal eventoFacade;
    
    @Override
    public List<Evento> getEventi(){
        return eventoFacade.findNext();
    }
    
}
