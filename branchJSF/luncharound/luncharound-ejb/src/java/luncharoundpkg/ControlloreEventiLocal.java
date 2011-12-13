/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Local
public interface ControlloreEventiLocal {
    
    public List<Evento> getEventi();
    
}
