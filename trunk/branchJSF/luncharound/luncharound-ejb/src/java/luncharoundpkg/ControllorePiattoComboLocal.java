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
    public void addPersistency(List<PiattoCombo> lista);
    public void editListaPiatti(List<PiattoCombo> lista);
    public void addPiatto(PiattoCombo p);
    public PiattoCombo createTemplatePiatto(int idLocale );
    public void removePiatto(PiattoCombo p);
    public PiattoCombo findById(long idPiattoCombo);
}
