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
public interface ValutazioneFacadeLocal {

    /**
     * 
     * @param valutazione
     */
    void create(Valutazione valutazione);

    /**
     * 
     * @param valutazione
     */
    void edit(Valutazione valutazione);

    /**
     * 
     * @param valutazione
     */
    void remove(Valutazione valutazione);

    /**
     * 
     * @param id
     * @return
     */
    Valutazione find(Object id);

    /**
     * 
     * @return
     */
    List<Valutazione> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<Valutazione> findRange(int[] range);

    /**
     * 
     * @return
     */
    int count();

    /**
     * 
     * @param idUtente
     * @return
     */
    public List<Valutazione> findByUtente(long idUtente);

    /**
     * 
     * @param idLocale
     * @return
     */
    public List<Valutazione> findByLocale(long idLocale);

    /**
     * 
     * @param idLocale
     * @param idUtente
     * @return
     */
    public Valutazione findValutazioneLocFromUtente(long idLocale, long idUtente);

    /**
     * 
     * @param idLocale
     * @param week
     * @return
     */
    public List<Valutazione> findByLocaleWeek(long idLocale, int week);
    
}
