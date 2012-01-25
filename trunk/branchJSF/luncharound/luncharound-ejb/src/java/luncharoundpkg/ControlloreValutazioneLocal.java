/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Local
public interface ControlloreValutazioneLocal {
    
    /**
     * 
     * @param request
     * @return
     */
    String mostraValutazioni(HttpServletRequest request);

    /**
     * 
     * @param req
     */
    public void valutazioneDaReq(javax.servlet.http.HttpServletRequest req);

    /**
     * 
     * @param par
     * @param val
     * @param opt
     * @return
     */
    public String createRatingStars(java.lang.String par, long val, java.lang.String opt);

    /**
     * 
     * @param idlocale
     * @param week
     * @return
     */
    public List<Valutazione> valutazioniSettimana(int idlocale, int week);

    /**
     * 
     * @param userRate
     */
    public void saveValutazione(Valutazione userRate);

    /**
     * 
     * @param idUtente
     * @param idLocale
     * @return
     */
    public Valutazione findValutazioneUtente(long idUtente, int idLocale);

    /**
     * 
     * @param idLocale
     * @return
     */
    public List<Valutazione> findValutazioni(int idLocale);

    /**
     * 
     * @param ll
     * @return
     */
    public Valutazione mediaValutazioni(List<Valutazione> ll);

}
