/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Local
public interface ControllorePiattiLocal {

    void addPersistency(List<Piatto> lista);

    public void addPiatto(luncharoundpkg.Piatto p);
    
}
