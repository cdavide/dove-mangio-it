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
public interface PiattoFacadeLocal {

    void create(Piatto piatto);

    void edit(Piatto piatto);

    void remove(Piatto piatto);

    Piatto find(Object id);

    List<Piatto> findAll();

    List<Piatto> findRange(int[] range);

    int count();
    
}
