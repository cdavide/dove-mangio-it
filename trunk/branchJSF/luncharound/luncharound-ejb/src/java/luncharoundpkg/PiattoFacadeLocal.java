/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lorenzo Bovio, Bronzino Francesco, Concas Davide
 */
@Local
public interface PiattoFacadeLocal {

    /**
     * 
     * @param piatto
     */
    void create(Piatto piatto);

    /**
     * 
     * @param piatto
     */
    void edit(Piatto piatto);

    /**
     * 
     * @param piatto
     */
    void remove(Piatto piatto);

    /**
     * 
     * @param id
     * @return
     */
    Piatto find(Object id);

    /**
     * 
     * @return
     */
    List<Piatto> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<Piatto> findRange(int[] range);

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
    /**
     * 
     * @param idLocale
     * @param cat
     * @return
     */
    public java.util.List<luncharoundpkg.Piatto> getCategoriaLocale(int idLocale, luncharoundpkg.Categoria cat);
    
    
}
