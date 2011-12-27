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
public interface ControllorePiattiLocal {

    void addPersistency(List<Piatto> lista);

    public void addPiatto(luncharoundpkg.Piatto p);

    public java.util.List<luncharoundpkg.Piatto> getCategoriaLocale(int idLocale, luncharoundpkg.Categoria cat);

    public void removePiatto(luncharoundpkg.Piatto p);

    public luncharoundpkg.Piatto findById(long idPiatto);

    public void editListaPiatti(java.util.List<luncharoundpkg.Piatto> lista);

    public luncharoundpkg.Piatto createTemplatePiatto(luncharoundpkg.Categoria cat, int idLocale);
    
}
