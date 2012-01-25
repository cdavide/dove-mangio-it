/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

/**Session bean che gestisce gli utenti
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
@Stateless
public class ControlloreUtente implements ControlloreUtenteLocal {

    @EJB
    private LocaleFacadeLocal localeFacade;
    @EJB
    private UtenteFacadeLocal utenteFacade;
    @EJB
    private EventoFacadeLocal eventoFacade;
    @EJB
    private NewsFacadeLocal newsFacade;

    /**Aggiunge un utente contenuto in un oggetto HttpServletRequest
     * 
     * @param req La HttpServletRequest che contiene l'utente
     */
    @Override
    public void addUtenteDaReq(HttpServletRequest req) {

        Utente ut = new Utente();
        Util.riempi(req, ut);
        utenteFacade.create(ut);
    }

    /**Inserisce un utente appartente a Facebook o Twitter
     * 
     * @param username Lo username dell'utente
     * @param mail La mail dell'utente
     * @param home L'indirizzo di casa dell'utente
     * @param foto La foto dell'utente come URL
     * @param tipo Tipo di utente
     */
    @Override
    public void addUtenteEsterno(String username, String mail, String home, String foto, int tipo) {
        Utente ut = new Utente();
        ut.setUsername(username);
        ut.setMail(mail);
        ut.setHome(home);
        ut.setFoto(foto);
        ut.setTipo(tipo);
        ut.setEventi(false);
        ut.setNews(false);
        utenteFacade.create(ut);
    }

    /**Aggiunge l'indirizzo di casa dell'utente
     * 
     * @param idUtente Id dell'utente
     * @param posizione Indirizzo da aggiungere
     */
    @Override
    public void addPosizione(long idUtente, String posizione) {

        Utente ut = utenteFacade.find(idUtente);
        ut.setHome(posizione);
        utenteFacade.edit(ut);
    }

    /**Aggiunge una foto all'utente come URL
     * 
     * @param idUtente Id dell'utente
     * @param url URL
     */
    @Override
    public void addFoto(long idUtente, String url) {
        Utente ut = utenteFacade.find(idUtente);
        ut.setHome(url);
        utenteFacade.edit(ut);
    }

    /**
     * @deprecated 
     * @param idUtente
     * @param idLocale
     * @param opT
     */
    @Override
    public void editPreferenze(long idUtente, int idLocale, boolean opT) {

        Utente ut = utenteFacade.find(idUtente);
        List<Locale> locali = ut.getPreferiti();
        Locale lo = localeFacade.find(idLocale);

        if (opT) {
            locali.add(lo);
        } else {
            locali.remove(lo);
        }
        ut.setPreferiti(locali);
        utenteFacade.edit(ut);
    }

    /**
     * @deprecated 
     * @param idUtente
     * @param opT
     */
    @Override
    public void editEventi(long idUtente, boolean opT) {

        Utente ut = utenteFacade.find(idUtente);
        ut.setEventi(opT);
        utenteFacade.edit(ut);

    }

    /**
     * @deprecated 
     * @param idUtente
     * @param opT
     */
    @Override
    public void editNews(long idUtente, boolean opT) {

        Utente ut = utenteFacade.find(idUtente);
        ut.setEventi(opT);
        utenteFacade.edit(ut);

    }

    /**Modifica la password di un utente
     * 
     * @param idUtente Id dell'utente
     * @param nuovaPwd Nuova password
     */
    @Override
    public void editPassword(long idUtente, String nuovaPwd) {
        Utente ut = utenteFacade.find(idUtente);
        ut.setPassword(nuovaPwd);
        utenteFacade.edit(ut);

    }

    /**Modifica la posizione di un utente
     * 
     * @param idUtente L'id dell'utente
     * @param home L'indirizzo di casa
     */
    @Override
    public void editHome(long idUtente, String home) {
        Utente ut = utenteFacade.find(idUtente);
        ut.setHome(home);
        utenteFacade.edit(ut);
    }

    /**Verica la password associata ad una mail
     * 
     * @param mail La mail da controllare
     * @param password La password da controllare
     * @return L'utente se la password corrisponde all'utente identificato dalla mail, null altrimenti
     */
    @Override
    public Utente verificaPassword(String mail, String password) {

        List<Utente> lu = utenteFacade.findAll();
        for (Utente ut : lu) {
            if (ut.getMail().equals(mail) && ut.getPassword().equals(password)) {
                return ut;
            }
        }
        return null;

    }

    /**Trova un utente a partire dalla sua email
     * 
     * @param mail La mail da usare
     * @return L'utente se la mail esiste, null altrimenti
     */
    @Override
    public Utente trovaDaEmail(String mail) {
        return utenteFacade.findByEmail(mail);
    }

    
    /**Aggiunge un utente al sistema
     * 
     * @param username Lo username
     * @param password La password
     * @param indirizzo L'indirizzo di casa
     * @param mail La mail
     * @param foto La foto in formato URL
     */
    @Override
    public void addUtente(String username, String password, String indirizzo, String mail, String foto) {
        System.err.println("[ControlloreUtente] aggiungendo utente ");
        Utente ut = new Utente();
        ut.setUsername(username);
        ut.setPassword(password);
        ut.setMail(mail);
        ut.setHome(indirizzo);
        ut.setFoto(foto);
        ut.setEventi(false);
        ut.setNews(false);
        try{
            utenteFacade.create(ut);   
        }catch(Exception e){
           System.out.println("[ControloloreUtente] attenzione errore inserimento!!");
        }
        
    }
    
    /**
     * @deprecated 
     * @param idUtente
     * @return
     */
    @Override
    public List<Evento> getEventi(Long idUtente){
        Utente utente = utenteFacade.find(idUtente);
        List<Locale> locali = utente.getPreferiti();
        ArrayList<Integer> idLocali = new ArrayList();;
        for (Locale ut : locali) {
            idLocali.add(ut.getId());
        }
        return eventoFacade.findByLocali(idLocali);
    }
    
    /**
     * @deprecated 
     * @param idUtente
     * @return
     */
    @Override
    public List<News> getNews(Long idUtente){
        Utente utente = utenteFacade.find(idUtente);
        List<Locale> locali = utente.getPreferiti();
        ArrayList<Integer> idLocali = new ArrayList();;
        for (Locale ut : locali) {
            idLocali.add(ut.getId());
        }
        return newsFacade.findByLocali(idLocali);
    }
}
