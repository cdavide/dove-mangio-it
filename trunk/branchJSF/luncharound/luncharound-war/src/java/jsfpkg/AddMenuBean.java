/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.Categoria;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControllorePiattiLocal;
import luncharoundpkg.Menu;
import luncharoundpkg.Piatto;

/**
 *
 * @author Bovio Loernzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "addMenuBean")
@SessionScoped
public class AddMenuBean {
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    
    @EJB
    private ControllorePiattiLocal controllorePiatto;
    private Menu menu;
    private List<Piatto> newListPiatti;
    private Piatto newOne;
    private List<Piatto> allPiattoNames;
    private Integer idLocale ;
    private Long idUtente;
    
    
    // il piatto nuovo viene reinizializzato
    //
    public String reinit() {
        newOne = new Piatto();
        return null;
    }

    public void AddMenuBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        idLocale = (Integer) session.getAttribute("mioLocale");
        idUtente = (Long) session.getAttribute("idUtente");
        if(idLocale == null || idUtente == null || !request.isRequestedSessionIdValid()){
            System.out.println("[AddMenuBean] attenzione non hai effettuato il login, il locale non esiste, la sessione è scaduta");
            // devo impedire la visualizzazione del menu del locale
            //
        }
    }

    
    @PostConstruct
    public void init() {
        try{
            menu = controlloreLocale.menuDiLocale(idLocale);
            newListPiatti = menu.getListaPiatti();
        }catch (Exception e){
            // il locale non ha alcun menu
            // il menu non contiene piatti
            menu = new Menu();
            newListPiatti = new ArrayList<Piatto>();
        }
        try{
            allPiattoNames = controlloreLocale.allPiatti(); // la predizione del
            // piatto viene fatta su tutti i piatti nel DB, non solo su quelli del locale
        }catch (Exception e) {
            // non son opresenti piatti nel DB
        }
        newOne= new Piatto();
    }

    
    
    
public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();  
        // creare query nel controllore che chiama la facade per avere i risultati
        // in base al nome fornito
        // la signature del metodo sarà simile a:
        // controlloreLocale.completeQuery(nomeTabella, nome campo della tabella, stringa input)
        // solleva una eccezione 
        for (int i = 0; i < 10; i++) {  
            results.add(query + i);  
        }  
          
        return results;  
    }  
    
    
    public void save() {
        FacesMessage msg = null;
        // prendo la sessione
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        Long idUtente = (Long) httpSession.getAttribute("idUtente");
        int idLocale =  (Integer) httpSession.getAttribute("idLocale");
        //controllare id utente altrimenti non si puo procedere, oppure che la sessione sia valida
        if (idUtente == null || // user not logged in
                !request.isRequestedSessionIdValid() ||  // sessione scaduta
                idLocale != controlloreLocale.getLocali(idUtente).getIdUtente()) { // l'utente non è padrone del locale
            System.out.println("[AddMenuBean] attenzione non hai effettuato il login, il locale non esiste, la sessione è scaduta");    
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Errore!", "Devi effettuare il login per registrare un nuovo menu.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
       
        try{
            controllorePiatto.addPiatto(newOne); // aggingo il piatto nel DB
            newListPiatti.add(newOne);// aggiungo il piatto alla lista
            controlloreLocale.editMenu(idLocale, newListPiatti, new Date()); // aggiorno il menu
        }
        catch(Exception e){
            System.out.println("[AddMenuBean] eccezione su inserimento in DB"+e.getLocalizedMessage().toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Failure!", "Modifiche menu non eseguite");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            reinit();
            return;
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Modifiche menu effettuate correttamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reinit();
    }
    
    /*Funzione richiamata quando viene cancellata una entry dalla tabella dei piatti
     */
    
    public void delete() {
        FacesMessage msg = null;
        // prendo la sessione
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        Long idUtente = (Long) httpSession.getAttribute("idUtente");
        int idLocale =  (Integer) httpSession.getAttribute("idLocale");
        //controllare id utente altrimenti non si puo procedere, oppure che la sessione sia valida
        if (idUtente == null || // user not logged in
                !request.isRequestedSessionIdValid() ||  // sessione scaduta
                idLocale != controlloreLocale.getLocali(idUtente).getIdUtente()) { // l'utente non è padrone del locale
            System.out.println("[AddMenuBean] attenzione non hai effettuato il login, il locale non esiste, la sessione è scaduta");    
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Errore!", "Devi effettuare il login per registrare un nuovo menu.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
       
        try{
            controllorePiatto.addPiatto(newOne); // aggingo il piatto nel DB
            newListPiatti.add(newOne);// aggiungo il piatto alla lista
            controlloreLocale.editMenu(idLocale, newListPiatti, new Date()); // aggiorno il menu
        }
        catch(Exception e){
            System.out.println("[AddMenuBean] eccezione su inserimento in DB"+e.getLocalizedMessage().toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Failure!", "Modifiche menu non eseguite");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            reinit();
            return;
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Modifiche menu effettuate correttamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reinit();
    }
    
    /* Funzione richiamata quando viene aggiornata una entry della tabella dei piatti
     * 
     */
    
    public void update(){
        
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public List<Piatto> getAllPiattoNames() {
        return allPiattoNames;
    }
    
    public void setAllPiattoNames(List<Piatto> allPiattoNames) {
        this.allPiattoNames = allPiattoNames;
    }
    
    public Integer getIdLocale() {
        return idLocale;
    }
    
    public void setIdLocale(Integer idLocale) {
        this.idLocale = idLocale;
    }
    
    public Long getIdUtente() {
        return idUtente;
    }
    
    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }
    
    public Menu getMenu() {
        return menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
    public List<Piatto> getNewListPiatti() {
        return newListPiatti;
    }
    
    public void setNewListPiatti(List<Piatto> newListPiatti) {
        this.newListPiatti = newListPiatti;
    }
    
    public Piatto getNewOne() {
        return newOne;
    }
    
    public void setNewOne(Piatto newOne) {
        this.newOne = newOne;
    }
    //</editor-fold>
}
