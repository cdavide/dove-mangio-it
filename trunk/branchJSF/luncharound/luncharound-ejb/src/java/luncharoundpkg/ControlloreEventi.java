/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** Session Bean Stateless che serve per manipolare gli eventi di un locale
 * 
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Stateless
public class ControlloreEventi implements ControlloreEventiLocal {

    @EJB
    private EventoFacadeLocal eventoFacade;
    
    /** Get Eventi
     * 
     * 
     * @return il prossimo evento nella lista
     */
    @Override
    public List<Evento> getEventi(){
        return eventoFacade.findNext();
    }
    
}
