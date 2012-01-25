/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Local
public interface ControllorePiattoComboLocal {
    /**
     * 
     * @param lista
     */
    public void addPersistency(List<PiattoCombo> lista);
    /**
     * 
     * @param lista
     */
    public void editListaPiatti(List<PiattoCombo> lista);
    /**
     * 
     * @param p
     */
    public void addPiatto(PiattoCombo p);
    /**
     * 
     * @param idLocale
     * @return
     */
    public PiattoCombo createTemplatePiatto(int idLocale );
    /**
     * 
     * @param p
     */
    public void removePiatto(PiattoCombo p);
    /**
     * 
     * @param idPiattoCombo
     * @return
     */
    public PiattoCombo findById(long idPiattoCombo);
}
