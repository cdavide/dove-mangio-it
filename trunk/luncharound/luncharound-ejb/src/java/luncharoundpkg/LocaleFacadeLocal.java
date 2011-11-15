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

    void create(Locale locale);

    void edit(Locale locale);

    void remove(Locale locale);

    Locale find(Object id);

    List<Locale> findAll();

    List<Locale> findRange(int[] range);

    int count();
    
    public List<Locale> findByUtente(long idUtente);
    

    
}
