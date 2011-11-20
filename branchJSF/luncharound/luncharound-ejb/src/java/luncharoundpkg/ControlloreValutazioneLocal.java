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
    
    String mostraValutazioni(HttpServletRequest request);

    public void valutazioneDaReq(javax.servlet.http.HttpServletRequest req);

    public String createRatingStars(java.lang.String par, long val, java.lang.String opt);

    public List<Valutazione> valutazioniSettimana(int idlocale, int week);

    public void saveValutazione(Valutazione userRate);

    public Valutazione findValutazioneUtente(long idUtente, int idLocale);

    public List<Valutazione> findValutazioni(int idLocale);

    public Valutazione mediaValutazioni(List<Valutazione> ll);

}
