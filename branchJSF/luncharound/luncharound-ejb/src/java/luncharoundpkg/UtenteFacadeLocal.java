/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/**Interfaccia della facade dell'oggetto utente
 *
 * @author lore0487
 */
@Local
public interface UtenteFacadeLocal {

    /**
     * 
     * @param utente
     */
    void create(Utente utente);

    /**
     * 
     * @param utente
     */
    void edit(Utente utente);

    /**
     * 
     * @param utente
     */
    void remove(Utente utente);

    /**
     * 
     * @param id
     * @return
     */
    Utente find(Object id);

    /**
     * 
     * @return
     */
    List<Utente> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<Utente> findRange(int[] range);
    
    /**Trova un utente a partire dall'email
     * 
     * @param mail la mail con cui effettuare la ricerca
     * @return L'utente se trovato, null altrimenti
     */
    Utente findByEmail(String mail);
    
    /**
     * 
     * @return
     */
    int count();
    
}
