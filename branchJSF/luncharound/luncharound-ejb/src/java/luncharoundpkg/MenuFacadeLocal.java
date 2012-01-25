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
public interface MenuFacadeLocal {

    /**
     * 
     * @param menu
     */
    void create(Menu menu);

    /**
     * 
     * @param menu
     */
    void edit(Menu menu);

    /**
     * 
     * @param menu
     */
    void remove(Menu menu);

    /**
     * 
     * @param id
     * @return
     */
    Menu find(Object id);

    /**
     * 
     * @return
     */
    List<Menu> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<Menu> findRange(int[] range);

    /**
     * 
     * @return
     */
    int count();

    /**
     * 
     * @param idLocale
     * @return
     */
    public Menu findByLocale(int idLocale);
    
}
