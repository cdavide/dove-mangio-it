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

    void create(Valutazione valutazione);

    void edit(Valutazione valutazione);

    void remove(Valutazione valutazione);

    Valutazione find(Object id);

    List<Valutazione> findAll();

    List<Valutazione> findRange(int[] range);

    int count();

    public List<Valutazione> findByUtente(long idUtente);

    public List<Valutazione> findByLocale(long idLocale);

    public Valutazione findValutazioneLocFromUtente(long idLocale, long idUtente);
    
}
