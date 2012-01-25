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
public interface PiattoComboFacadeLocal {

    /**
     * 
     * @param piattoCombo
     */
    void create(PiattoCombo piattoCombo);

    /**
     * 
     * @param piattoCombo
     */
    void edit(PiattoCombo piattoCombo);

    /**
     * 
     * @param piattoCombo
     */
    void remove(PiattoCombo piattoCombo);

    /**
     * 
     * @param id
     * @return
     */
    PiattoCombo find(Object id);

    /**
     * 
     * @return
     */
    List<PiattoCombo> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<PiattoCombo> findRange(int[] range);

    /**
     * 
     * @return
     */
    int count();
    
    /**Restituisce la lista delle combinazioni di un locale
     * 
     * @param idLocale l'id del locale di cui si vogliono conoscere le combinazioni
     * @return la lista delle combinazioni di un locale
     */
    public List<PiattoCombo> findByLocale(int idLocale);
    
}
