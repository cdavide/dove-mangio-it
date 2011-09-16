/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
@Local
public interface ControlloreUtenteLocal {
    
    public void addUtenteDaReq(HttpServletRequest req);
    public void addPosizione(long idUtente,String posizione);
    public void addFoto(long idUtente,String url);
    
    public void editPreferenze(long idUtente,int idLocale,boolean op_t);
    public void editEventi(long idUtente,boolean opT);
    public void editNews(long idUtente,boolean opT);
    public void editPassword(long idUtente,String nuovaPwd);
    
    public boolean verificaPassword(String mail, String password);

    
}
