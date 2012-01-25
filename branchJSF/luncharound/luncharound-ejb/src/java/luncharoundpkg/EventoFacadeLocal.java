/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/**Interfaccia degli eventi
 *
 * @author lore0487
 */
@Local
public interface EventoFacadeLocal {

    /**
     * 
     * @param evento
     */
    void create(Evento evento);

    /**
     * 
     * @param evento
     */
    void edit(Evento evento);

    /**
     * 
     * @param evento
     */
    void remove(Evento evento);

    /**
     * 
     * @param id
     * @return
     */
    Evento find(Object id);

    /**
     * 
     * @return
     */
    List<Evento> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<Evento> findRange(int[] range);

    /**
     * 
     * @return
     */
    int count();
    
    /**
     * 
     */
    public void deleteOld();
    
    /**Ritorna gli eventi associati ad un locale
     * 
     * @param idLocale L'id del locale
     * @return la lista degli eventi
     */
    public List<Evento> findByLocale(int idLocale );
    
    /**
     * @deprecated 
     * @param idLocali
     * @return
     */
    public List<Evento> findByLocali(List<Integer> idLocali );
    
    /**Ritorna tutti gli eventi non scaduti del sistema
     * 
     * @return La lista degli eventi
     */
    public List<Evento> findNext();
    
}
