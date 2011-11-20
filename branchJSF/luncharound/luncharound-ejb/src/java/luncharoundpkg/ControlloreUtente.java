/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
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
    
    
    @Override
    public void addUtenteDaReq(HttpServletRequest req){
        
        Utente ut= new Utente();
        Util.riempi(req, ut);
        utenteFacade.create(ut);
    }
    
    @Override
    public void addUtenteEsterno(String username,String mail,String home,String foto, int tipo){
        Utente ut= new Utente();
        ut.setUsername(username);
        ut.setMail(mail);
        ut.setHome(home);
        ut.setFoto(foto);
        ut.setTipo(tipo);
        ut.setEventi(false);
        ut.setNews(false);
        utenteFacade.create(ut);
    }
    
    @Override
    public void addPosizione(long idUtente,String posizione){
    
        Utente ut=utenteFacade.find(idUtente);
        ut.setHome(posizione);
        utenteFacade.edit(ut);
    }
    
    @Override
    public void addFoto(long idUtente, String url){
        
        Utente ut=utenteFacade.find(idUtente);
        ut.setHome(url);
        utenteFacade.edit(ut);
    }
    //opT true=aggiungere un locale
    @Override
    public void editPreferenze(long idUtente,int idLocale,boolean opT){
    
        Utente ut=utenteFacade.find(idUtente);
        List<Locale> locali = ut.getPreferiti();
        Locale lo=localeFacade.find(idLocale);

        if(opT){
            locali.add(lo);
        }
        else{
            locali.remove(lo);
        }
        ut.setPreferiti(locali);
        utenteFacade.edit(ut);
    }
    
    @Override
    public void editEventi(long idUtente,boolean opT){
    
       Utente ut=utenteFacade.find(idUtente);
       ut.setEventi(opT);
       utenteFacade.edit(ut);
           
    }
    
    @Override
    public void editNews(long idUtente,boolean opT){
       
       Utente ut=utenteFacade.find(idUtente);
       ut.setEventi(opT);
       utenteFacade.edit(ut);
       
    }
    
    //eseguire md5 della password!!
    @Override
    public void editPassword(long idUtente,String nuovaPwd){
       Utente ut=utenteFacade.find(idUtente);
       ut.setPassword(nuovaPwd);
       utenteFacade.edit(ut);
    
    }
    
    @Override
    public void editHome(long idUtente, String home) {
       Utente ut=utenteFacade.find(idUtente);
       ut.setHome(home);
       utenteFacade.edit(ut);
    }
     
    //anche qui manca md5!!
    @Override
    public Utente verificaPassword(String mail, String password){
        
        List<Utente> lu = utenteFacade.findAll();
        for(Utente ut : lu){
            if(ut.getMail().equals(mail) && ut.getPassword().equals(password)) return ut;
        }
        return null;
    
    }
    
    @Override
    public Utente trovaDaEmail(String mail){
        return utenteFacade.findByEmail(mail);
    }    
    }
    
   
