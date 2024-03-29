/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Stateless;
import java.util.List;
import javax.ejb.EJB;

/**Classe controllore News
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Stateless
public class ControlloreNews implements ControlloreNewsLocal {

    @EJB
    private NewsFacadeLocal newsFacade;
    
    /**Restituisce una lista con tutte e news nel DB
     * 
     * @return la lista di tutte le news nel DB
     */
    @Override
    public List<News> getNews(){
        return newsFacade.findNext();
    }
    
}
