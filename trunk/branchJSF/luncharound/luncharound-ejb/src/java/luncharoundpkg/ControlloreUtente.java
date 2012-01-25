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

/**
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

    /**
     * 
     * @param req
     */
    @Override
    public void addUtenteDaReq(HttpServletRequest req) {

        Utente ut = new Utente();
        Util.riempi(req, ut);
        utenteFacade.create(ut);
    }

    /**
     * 
     * @param username
     * @param mail
     * @param home
     * @param foto
     * @param tipo
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

    /**
     * 
     * @param idUtente
     * @param posizione
     */
    @Override
    public void addPosizione(long idUtente, String posizione) {

        Utente ut = utenteFacade.find(idUtente);
        ut.setHome(posizione);
        utenteFacade.edit(ut);
    }

    /**
     * 
     * @param idUtente
     * @param url
     */
    @Override
    public void addFoto(long idUtente, String url) {
        Utente ut = utenteFacade.find(idUtente);
        ut.setHome(url);
        utenteFacade.edit(ut);
    }
    //opT true=aggiungere un locale

    /**
     * 
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
     * 
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
     * 
     * @param idUtente
     * @param opT
     */
    @Override
    public void editNews(long idUtente, boolean opT) {

        Utente ut = utenteFacade.find(idUtente);
        ut.setEventi(opT);
        utenteFacade.edit(ut);

    }

    //eseguire md5 della password!!
    /**
     * 
     * @param idUtente
     * @param nuovaPwd
     */
    @Override
    public void editPassword(long idUtente, String nuovaPwd) {
        Utente ut = utenteFacade.find(idUtente);
        ut.setPassword(nuovaPwd);
        utenteFacade.edit(ut);

    }

    /**
     * 
     * @param idUtente
     * @param home
     */
    @Override
    public void editHome(long idUtente, String home) {
        Utente ut = utenteFacade.find(idUtente);
        ut.setHome(home);
        utenteFacade.edit(ut);
    }

    //anche qui manca md5!!
    /**
     * 
     * @param mail
     * @param password
     * @return
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

    /**
     * 
     * @param mail
     * @return
     */
    @Override
    public Utente trovaDaEmail(String mail) {
        return utenteFacade.findByEmail(mail);
    }

    
    /**
     * 
     * @param username
     * @param password
     * @param indirizzo
     * @param mail
     * @param foto
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
           //FIX
           //TODO 
           // far tornare un booleano per poter mostrare feedback per errori nel frontend
        }
        
    }
    
    /**
     * 
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
     * 
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
