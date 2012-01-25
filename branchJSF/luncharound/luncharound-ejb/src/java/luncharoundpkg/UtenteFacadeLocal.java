/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/**
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
    
    /**
     * 
     * @param mail
     * @return
     */
    Utente findByEmail(String mail);
    
    /**
     * 
     * @return
     */
    int count();
    
}
