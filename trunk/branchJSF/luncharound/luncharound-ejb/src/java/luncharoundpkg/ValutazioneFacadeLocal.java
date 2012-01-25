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

    /** ricerca per id utenti
     * 
     * @param idUtente l'id dell'utente
     * @return la lista delle valutazioni
     */
    public List<Valutazione> findByUtente(long idUtente);

    /** ricerca per id locale
     * 
     * @param idLocale l'id del locale
     * @return lista delle valutazione
     */
    public List<Valutazione> findByLocale(long idLocale);

    /** ricerca valutazione di un utente per un dato locale
     * 
     * @param idLocale l'id del locale
     * @param idUtente l'utente che ha valutato
     * @return
     */
    public Valutazione findValutazioneLocFromUtente(long idLocale, long idUtente);

    /** restituisce la lista di valutazioni di una settimana dato un locale
     * 
     * @param idLocale l'id del locale
     * @param week numero della settimana
     * @return la lista delle valutazioni
     */
    public List<Valutazione> findByLocaleWeek(long idLocale, int week);
    
}
