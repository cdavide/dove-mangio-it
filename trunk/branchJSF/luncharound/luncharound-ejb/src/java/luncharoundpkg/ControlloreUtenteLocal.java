/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**Interfaccia del controllore utente
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
@Local
public interface ControlloreUtenteLocal {
    
    /**Aggiunge un utente contenuto in un oggetto HttpServletRequest
     * 
     * @param req La HttpServletRequest che contiene l'utente
     */
    public void addUtenteDaReq(HttpServletRequest req);
    /**Inserisce un utente appartente a Facebook o Twitter
     * 
     * @param username Lo username dell'utente
     * @param mail La mail dell'utente
     * @param home L'indirizzo di casa dell'utente
     * @param foto La foto dell'utente come URL
     * @param tipo Tipo di utente
     */
    public void addUtenteEsterno(String username,String mail,String home,String foto,int tipo);
    /**Aggiunge l'indirizzo di casa dell'utente
     * 
     * @param idUtente Id dell'utente
     * @param posizione Indirizzo da aggiungere
     */
    public void addPosizione(long idUtente,String posizione);
    /**Aggiunge una foto all'utente come URL
     * 
     * @param idUtente Id dell'utente
     * @param url URL
     */
    public void addFoto(long idUtente,String url);
    
    /**
     * @deprecated 
     * @param idUtente
     * @param idLocale
     * @param op_t
     */
    public void editPreferenze(long idUtente,int idLocale,boolean op_t);
    /**
     * @deprecated 
     * @param idUtente
     * @param opT
     */
    public void editEventi(long idUtente,boolean opT);
    /**
     * @deprecated 
     * @param idUtente
     * @param opT
     */
    public void editNews(long idUtente,boolean opT);
    /**Modifica la password di un utente
     * 
     * @param idUtente Id dell'utente
     * @param nuovaPwd Nuova password
     */
    public void editPassword(long idUtente,String nuovaPwd);
    /**Modifica la posizione di un utente
     * 
     * @param idUtente L'id dell'utente
     * @param home L'indirizzo di casa
     */
    public void editHome(long idUtente, String home);
    
    /**Verica la password associata ad una mail
     * 
     * @param mail La mail da controllare
     * @param password La password da controllare
     * @return L'utente se la password corrisponde all'utente identificato dalla mail, null altrimenti
     */
    public Utente verificaPassword(String mail, String password);

    /**Trova un utente a partire dalla sua email
     * 
     * @param mail La mail da usare
     * @return L'utente se la mail esiste, null altrimenti
     */
    public Utente trovaDaEmail(String mail);

    /**Aggiunge un utente al sistema
     * 
     * @param username Lo username
     * @param password La password
     * @param indirizzo L'indirizzo di casa
     * @param mail La mail
     * @param foto La foto in formato URL
     */
    public void addUtente(String username, String password, String indirizzo, String mail, String foto);

    /**
     * @deprecated 
     * @param idUtente
     * @return
     */
    public List<Evento> getEventi(Long idUtente);
    
    /**
     * @deprecated 
     * @param idUtente
     * @return
     */
    public List<News> getNews(Long idUtente);
}
