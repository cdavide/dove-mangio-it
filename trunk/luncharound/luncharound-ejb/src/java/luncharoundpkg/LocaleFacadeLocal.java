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
public interface LocaleFacadeLocal {

    void create(Locale locale);

    void edit(Locale locale);

    void remove(Locale locale);

    Locale find(Object id);

    List<Locale> findAll();

    List<Locale> findRange(int[] range);

    int count();
    
}
