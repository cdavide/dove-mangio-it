/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
@Local
public interface ControlloreUtenteLocal {
    
    /**
     * 
     * @param req
     */
    public void addUtenteDaReq(HttpServletRequest req);
    /**
     * 
     * @param username
     * @param mail
     * @param home
     * @param foto
     * @param tipo
     */
    public void addUtenteEsterno(String username,String mail,String home,String foto,int tipo);
    /**
     * 
     * @param idUtente
     * @param posizione
     */
    public void addPosizione(long idUtente,String posizione);
    /**
     * 
     * @param idUtente
     * @param url
     */
    public void addFoto(long idUtente,String url);
    
    /**
     * 
     * @param idUtente
     * @param idLocale
     * @param op_t
     */
    public void editPreferenze(long idUtente,int idLocale,boolean op_t);
    /**
     * 
     * @param idUtente
     * @param opT
     */
    public void editEventi(long idUtente,boolean opT);
    /**
     * 
     * @param idUtente
     * @param opT
     */
    public void editNews(long idUtente,boolean opT);
    /**
     * 
     * @param idUtente
     * @param nuovaPwd
     */
    public void editPassword(long idUtente,String nuovaPwd);
    /**
     * 
     * @param idUtente
     * @param home
     */
    public void editHome(long idUtente, String home);
    
    /**
     * 
     * @param mail
     * @param password
     * @return
     */
    public Utente verificaPassword(String mail, String password);

    /**
     * 
     * @param mail
     * @return
     */
    public Utente trovaDaEmail(String mail);

    /**
     * 
     * @param username
     * @param password
     * @param indirizzo
     * @param mail
     * @param foto
     */
    public void addUtente(String username, String password, String indirizzo, String mail, String foto);

    /**
     * 
     * @param idUtente
     * @return
     */
    public List<Evento> getEventi(Long idUtente);
    
    /**
     * 
     * @param idUtente
     * @return
     */
    public List<News> getNews(Long idUtente);
}
