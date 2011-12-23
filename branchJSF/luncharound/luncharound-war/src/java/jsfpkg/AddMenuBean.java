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
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
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
    
    private List<Piatto> primi;
    private List<Piatto> secondi;
    private List<Piatto> contorni;
    private List<Piatto> dolci;
    private List<Piatto> bevande;
    private List<Piatto> antipasti;
    private Piatto nuovoPrimo;
    private Piatto nuovoAntipasto;
    private Piatto nuovoSecondo;
    private Piatto nuovoDolce;
    private Piatto nuovaBevanda;
    private Piatto nuovoContorno;
    // serve per eliminare i piatti dal menu
    // direttamente dalla datatable
    private Piatto currentPiatto;
            
    
    // il piatto nuovo viene reinizializzato
    //
    
    public String reinit() {
        newOne = new Piatto();
        return null;
    }

    public void AddMenuBean() {
        System.err.println("Costruttore inizio [addmenuBean]!");
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
        /*divisione in tab in base alla categoria*/
        nuovoAntipasto= new Piatto();
        nuovaBevanda= new Piatto();
        nuovoDolce = new Piatto();
        nuovoSecondo = new Piatto();
        nuovoPrimo = new Piatto();
        nuovoContorno = new Piatto();
        try{
            primi = controllorePiatto.getCategoriaLocale(idLocale, Categoria.PRIMI);
            secondi = controllorePiatto.getCategoriaLocale(idLocale, Categoria.SECONDI);
            contorni = controllorePiatto.getCategoriaLocale(idLocale, Categoria.CONTORNI);
            antipasti = controllorePiatto.getCategoriaLocale(idLocale, Categoria.ANTIPASTI);
            bevande = controllorePiatto.getCategoriaLocale(idLocale, Categoria.BEVANDE);
        }
        catch (Exception e){

            primi = new ArrayList<Piatto> ();
            secondi = new ArrayList<Piatto> ();
            contorni = new ArrayList<Piatto> ();
            antipasti = new ArrayList<Piatto> ();
            bevande = new ArrayList<Piatto> ();
            dolci =new ArrayList<Piatto>(); 
        }
        
        /*Piatti vuoti per aggiunta nel menu*/
        primi.add(nuovoPrimo);
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        primi.add(new Piatto());
        
        secondi.add(nuovoSecondo);
        antipasti.add(nuovoAntipasto);
        contorni.add(nuovoContorno);
        bevande.add(nuovaBevanda);
        dolci.add(nuovoDolce);
        newOne= new Piatto();
        System.err.println("Post constructor OK!");
    }

    
public void add (){
    
}
    
public void remove(){
    
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

    public Piatto getNuovaBevanda() {
        return nuovaBevanda;
    }

    public void setNuovaBevanda(Piatto nuovaBevanda) {
        this.nuovaBevanda = nuovaBevanda;
    }

    public Piatto getNuovoAntipasto() {
        return nuovoAntipasto;
    }

    public void setNuovoAntipasto(Piatto nuovoAntipasto) {
        this.nuovoAntipasto = nuovoAntipasto;
    }

    public Piatto getNuovoContorno() {
        return nuovoContorno;
    }

    public void setNuovoContorno(Piatto nuovoContorno) {
        this.nuovoContorno = nuovoContorno;
    }

    public Piatto getNuovoDolce() {
        return nuovoDolce;
    }

    public void setNuovoDolce(Piatto nuovoDolce) {
        this.nuovoDolce = nuovoDolce;
    }

    public Piatto getNuovoPrimo() {
        return nuovoPrimo;
    }

    public void setNuovoPrimo(Piatto nuovoPrimo) {
        this.nuovoPrimo = nuovoPrimo;
    }

    public Piatto getNuovoSecondo() {
        return nuovoSecondo;
    }

    public void setNuovoSecondo(Piatto nuovoSecondo) {
        this.nuovoSecondo = nuovoSecondo;
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

    public List<Piatto> getAntipasti() {
        return antipasti;
    }

    public void setAntipasti(List<Piatto> antipasti) {
        this.antipasti = antipasti;
    }

    public List<Piatto> getBevande() {
        return bevande;
    }

    public void setBevande(List<Piatto> bevande) {
        this.bevande = bevande;
    }

    public List<Piatto> getContorni() {
        return contorni;
    }

    public void setContorni(List<Piatto> contorni) {
        this.contorni = contorni;
    }

    public List<Piatto> getDolci() {
        return dolci;
    }

    public void setDolci(List<Piatto> dolci) {
        this.dolci = dolci;
    }

    public List<Piatto> getPrimi() {
        return primi;
    }

    public void setPrimi(List<Piatto> primi) {
        this.primi = primi;
    }

    public List<Piatto> getSecondi() {
        return secondi;
    }

    public void setSecondi(List<Piatto> secondi) {
        this.secondi = secondi;
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
