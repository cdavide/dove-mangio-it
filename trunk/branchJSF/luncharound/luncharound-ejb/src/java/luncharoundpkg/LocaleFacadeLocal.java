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
public interface LocaleFacadeLocal {

    /**
     * 
     * @param locale
     */
    void create(Locale locale);

    /**
     * 
     * @param locale
     */
    void edit(Locale locale);

    /**
     * 
     * @param locale
     */
    void remove(Locale locale);

    /**
     * 
     * @param id
     * @return
     */
    Locale find(Object id);

    /**
     * 
     * @return
     */
    List<Locale> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<Locale> findRange(int[] range);

    /**
     * 
     * @return
     */
    int count();
    
    /**
     * 
     * @param idUtente
     * @return
     */
    public Locale findByUtente(long idUtente);
    

    
}
