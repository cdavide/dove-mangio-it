/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
@ViewScoped
public class AddMenuBean {

    
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    @EJB
    private ControllorePiattiLocal controllorePiatto;
    private Menu menu;
    private List<Piatto> newListPiatti;
    private List<Piatto> allPiattoNames;
    private Integer idLocale;
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

    public void AddMenuBean() {
        System.err.println("Costruttore inizio [addmenuBean]!");
     
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        idLocale = (Integer) session.getAttribute("mioLocale");
        idUtente = (Long) session.getAttribute("idUtente");
        if (idLocale == null || idUtente == null || !request.isRequestedSessionIdValid()) {
            System.out.println("[AddMenuBean] attenzione non hai effettuato il login, il locale non esiste, la sessione è scaduta");
            // devo impedire la visualizzazione del menu del locale
            //
        }
        
        try {
            menu = controlloreLocale.menuDiLocale(idLocale);
            newListPiatti = menu.getListaPiatti();
        } catch (Exception e) {
            // il locale non ha alcun menu
            // il menu non contiene piatti
            menu = new Menu();
            newListPiatti = new ArrayList<Piatto>();
        }
        try {
            allPiattoNames = controlloreLocale.allPiatti(); // la predizione del
            // piatto viene fatta su tutti i piatti nel DB, non solo su quelli del locale
        } catch (Exception e) {
            // non son opresenti piatti nel DB
        }
        /*divisione in tab in base alla categoria*/
        nuovoAntipasto = controllorePiatto.createTemplatePiatto(Categoria.ANTIPASTI, idLocale);
        
        nuovaBevanda = controllorePiatto.createTemplatePiatto(Categoria.BEVANDE, idLocale);
        
        nuovoDolce = controllorePiatto.createTemplatePiatto(Categoria.DOLCI, idLocale);
        
        nuovoSecondo = controllorePiatto.createTemplatePiatto(Categoria.SECONDI, idLocale); 
        
        nuovoPrimo = controllorePiatto.createTemplatePiatto(Categoria.PRIMI, idLocale);
        
        nuovoContorno = controllorePiatto.createTemplatePiatto(Categoria.CONTORNI, idLocale);
        
        try {
            primi = controllorePiatto.getCategoriaLocale(idLocale, Categoria.PRIMI);
            secondi = controllorePiatto.getCategoriaLocale(idLocale, Categoria.SECONDI);
            contorni = controllorePiatto.getCategoriaLocale(idLocale, Categoria.CONTORNI);
            antipasti = controllorePiatto.getCategoriaLocale(idLocale, Categoria.ANTIPASTI);
            bevande = controllorePiatto.getCategoriaLocale(idLocale, Categoria.BEVANDE);
            dolci = controllorePiatto.getCategoriaLocale(idLocale, Categoria.DOLCI);
        } catch (Exception e) {

            primi = new ArrayList<Piatto>();
            secondi = new ArrayList<Piatto>();
            contorni = new ArrayList<Piatto>();
            antipasti = new ArrayList<Piatto>();
            bevande = new ArrayList<Piatto>();
            dolci = new ArrayList<Piatto>();
        }

        /*Piatti vuoti per aggiunta nel menu*/
        primi.add(nuovoPrimo);
        secondi.add(nuovoSecondo);
        antipasti.add(nuovoAntipasto);
        contorni.add(nuovoContorno);
        bevande.add(nuovaBevanda);
        dolci.add(nuovoDolce);
        System.err.println("Post constructor OK!");
    }


    
    public void remove() {
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

    
    public void add() {
        FacesContext context = FacesContext.getCurrentInstance();
        //controllare id utente altrimenti non si puo procedere, oppure che la sessione sia valida
        if (!checkUserLogin()) { // l'utente non è padrone del locale
            addFacesMessage("Devi effettuare il login per registrare un nuovo menu.", "Errore!");
            return;
        }
        // prendo il parametro passato dalla jsf
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        int idOperation = Integer.parseInt(params.get("idOperation"));
        System.out.println("idOp: "+idOperation);
        
        switch (idOperation) {
            case 0:// antipasto
                System.out.println("aggiunta antipasto!");
                controllorePiatto.addPiatto(nuovoAntipasto); // aggiungo il piatto nel DB
                newListPiatti.add(nuovoAntipasto);// aggiungo il piatto alla lista
                nuovoAntipasto = controllorePiatto.createTemplatePiatto(Categoria.ANTIPASTI, idLocale);
                antipasti.add(nuovoAntipasto);
                break;
            case 1: // primo
                System.out.println("aggiunta primo!");
                controllorePiatto.addPiatto(nuovoPrimo); // aggingo il piatto nel DB;
                newListPiatti.add(nuovoPrimo);// aggiungo il piatto alla lista
                nuovoPrimo  =  controllorePiatto.createTemplatePiatto(Categoria.PRIMI, idLocale);
                primi.add(nuovoPrimo);
                break;
            case 2: // contorno
                System.out.println("aggiunta contorno!");
                controllorePiatto.addPiatto(nuovoContorno); // aggingo il piatto nel DB ;
                newListPiatti.add(nuovoContorno);// aggiungo il piatto alla lista
                nuovoContorno  = controllorePiatto.createTemplatePiatto(Categoria.CONTORNI, idLocale);
                contorni.add(nuovoContorno);
                break;
            case 3:   // secondo
                System.out.println("aggiunta secondo!");
                controllorePiatto.addPiatto(nuovoSecondo); // aggingo il piatto nel DB;
                newListPiatti.add(nuovoSecondo);// aggiungo il piatto alla lista
                nuovoSecondo  = controllorePiatto.createTemplatePiatto(Categoria.SECONDI, idLocale);
                secondi.add(nuovoSecondo);
                break;

            case 4: // dolce
                System.out.println("aggiunta dolce!");
                controllorePiatto.addPiatto(nuovoDolce); // aggingo il piatto nel DB;
                newListPiatti.add(nuovoDolce);// aggiungo il piatto alla lista
                nuovoDolce  = controllorePiatto.createTemplatePiatto(Categoria.DOLCI, idLocale);
                dolci.add(nuovoDolce);
                break;
            case 5: // bevanda
                System.out.println("aggiunta bevanda!");
                controllorePiatto.addPiatto(nuovaBevanda); // aggingo il piatto nel DB;
                newListPiatti.add(nuovaBevanda);// aggiungo il piatto alla lista
                nuovaBevanda  = controllorePiatto.createTemplatePiatto(Categoria.BEVANDE, idLocale);
                bevande.add(nuovaBevanda);
                break;
            }
        // la lista è aggiornata, propago le modifice al DB

        editMenu();
    }


    /*Funzione richiamata quando viene cancellata una entry dalla tabella dei piatti
     */
    public void delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        Piatto toRem;
        //controllare id utente altrimenti non si puo procedere, oppure che la sessione sia valida
        if (!checkUserLogin()) {
            addFacesMessage("Devi effettuare il login per registrare un nuovo menu.", "Errore!");
            return;
        }
        // prendo il parametro passato dalla jsf
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        long idPiatto = Integer.parseInt(params.get("idPiatto"));
        System.err.println("idPiatto: "+idPiatto);
        try{
            toRem = controllorePiatto.findById(idPiatto);
            // inverto l'ordine delle remove per vincoli integrità db
            newListPiatti.remove(toRem);// rimuovo il piatto alla lista
            editMenu();
            antipasti.remove(toRem);
            controllorePiatto.removePiatto(toRem); // rimuovo il piatto nel DB;
            // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
        }
        catch(Exception e) {
            System.out.println("[AddMenuBean.java]: non trovato piatto da cancellare, forse id errato?");
        }
    }

    

    public void editMenu() {
        try {
            controllorePiatto.editListaPiatti(newListPiatti);
            controlloreLocale.editMenu(idLocale, newListPiatti, new Date());
        } // aggiorno il menu
        catch (Exception e) {
            addFacesMessage("Modifiche non eseguite ", "Failure!");
            return;
        }
        addFacesMessage("Modifiche menu effettuate correttamente", "Success!");
    }

   
    
    public void addFacesMessage(String str, String severity) {
        FacesMessage msg = null;
        System.out.println(str);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, severity, str);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }

    public boolean checkUserLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        idUtente = (Long) httpSession.getAttribute("idUtente");
        idLocale = (Integer) httpSession.getAttribute("idLocale");

        return (idUtente == null || // user not logged in
                !request.isRequestedSessionIdValid() || // sessione scaduta
                idLocale != controlloreLocale.getLocali(idUtente).getIdUtente()); // l'utente non è padrone del locale
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
    
    
    
    //</editor-fold>
}
