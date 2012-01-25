/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**Interfaccia del controllore delle valutazioni
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Local
public interface ControlloreValutazioneLocal {
    
    /**Mostra valutazione locale
     * @param idLocale  L'identificativo unico del locale
     * @param idLocale  L'identificativo unico dell'utente
     * @return  Una stringa contenente le valutazioni
     */
    String mostraValutazioni(HttpServletRequest request);

    /**Aggiunge una valutazione contenuta in una HttpServletRequest
     * 
     * @param req La HttpServletRequest che contiene la valutazione
     */
    public void valutazioneDaReq(javax.servlet.http.HttpServletRequest req);

    /** Metodo di appoggio che crea il form per visualizzare il rating
     * @param par il parametro di cui si vuole ottenere il form del rating
     * @param val il valore medio del rating  del parametro di valutazione
     * @param opt opzioni varie disabled ecc, guardare help jquery rating plugin
     * @comment non è molto bello ma funziona :-p
     */
    public String createRatingStars(java.lang.String par, long val, java.lang.String opt);

    /** Ritorna le valutazione di una settimana per un locale
     * 
     * @param idlocale L'id del locale a cui aggiungere
     * @param week La settimana
     * @return La lista delle valutazione  per la settimana specificata
     */
    public List<Valutazione> valutazioniSettimana(int idlocale, int week);

    /**Salva la valutazione 
     * 
     * @param userRate La valutazione da usare
     */
    public void saveValutazione(Valutazione userRate);

    /**Cerca le valutazioni di un utente per un dato utente e un dato locale
     * 
     * @param idutente L'id dell'utente
     * @param idlocale L'id del locale
     * @return Le valutazioni
     */
    public Valutazione findValutazioneUtente(long idUtente, int idLocale);

    /**Ritorna le valutazioni di un locale
     * 
     * @param idlocale L'id del locale
     * @return Le valutazioni del locale
     */
    public List<Valutazione> findValutazioni(int idLocale);

    /**Calcola la media delle valutazioni a partire da una lista di valutazioni
     * 
     * @param vals La lista delle valutazioni
     * @return La media ottenuta
     */
    public Valutazione mediaValutazioni(List<Valutazione> ll);

}
