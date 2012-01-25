/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import java.util.List;

/** Interfaccia controllore eventi
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Local
public interface ControlloreEventiLocal {
    
    /** Interfaccia get Eventi
     * 
     * @return una lista di tutti gli eventi presenti nel DB
     */
    public List<Evento> getEventi();
    
}
