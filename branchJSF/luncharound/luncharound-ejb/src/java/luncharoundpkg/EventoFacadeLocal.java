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
    
    /**
     * 
     * @param idLocale
     * @return
     */
    public List<Evento> findByLocale(int idLocale );
    
    /**
     * 
     * @param idLocali
     * @return
     */
    public List<Evento> findByLocali(List<Integer> idLocali );
    
    /**
     * 
     * @return
     */
    public List<Evento> findNext();
    
}
