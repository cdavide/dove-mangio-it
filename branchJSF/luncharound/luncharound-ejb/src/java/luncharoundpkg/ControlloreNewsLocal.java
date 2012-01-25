/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import java.util.List;

/**Interfaccia controllore delle News
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Local
public interface ControlloreNewsLocal {
    
    /**Restituisce una lista con tutte e news nel DB
     * 
     * @return la lista di tutte le news nel DB
     */
    public List<News> getNews();
            
}
