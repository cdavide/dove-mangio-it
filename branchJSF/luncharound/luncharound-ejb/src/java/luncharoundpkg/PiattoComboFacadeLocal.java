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
public interface PiattoComboFacadeLocal {

    void create(PiattoCombo piattoCombo);

    void edit(PiattoCombo piattoCombo);

    void remove(PiattoCombo piattoCombo);

    PiattoCombo find(Object id);

    List<PiattoCombo> findAll();

    List<PiattoCombo> findRange(int[] range);

    int count();
    
}
