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
    
    public void addUtenteDaReq(HttpServletRequest req);
    public void addUtenteEsterno(String username,String mail,String home,String foto,int tipo);
    public void addPosizione(long idUtente,String posizione);
    public void addFoto(long idUtente,String url);
    
    public void editPreferenze(long idUtente,int idLocale,boolean op_t);
    public void editEventi(long idUtente,boolean opT);
    public void editNews(long idUtente,boolean opT);
    public void editPassword(long idUtente,String nuovaPwd);
    public void editHome(long idUtente, String home);
    
    public Utente verificaPassword(String mail, String password);

    public Utente trovaDaEmail(String mail);

    public void addUtente(String username, String password, String indirizzo, String mail, String foto);

    //public List<Evento> getEventi(String email);
}
