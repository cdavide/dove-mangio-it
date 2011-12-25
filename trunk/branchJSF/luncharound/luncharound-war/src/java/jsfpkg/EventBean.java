/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;
import luncharoundpkg.Locale;
import luncharoundpkg.Evento;
import luncharoundpkg.ControlloreEventiLocal;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "eventBean")
@RequestScoped
public class EventBean {
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    @EJB
    private ControlloreUtenteLocal controlloreUtente;
    @EJB
    private ControlloreEventiLocal controlloreEventi;
    private List<Evento> eventi;
    private Evento selectedEvent;
    boolean gestore; // per c:if nella jsf
    boolean loggedIn;
    Locale locale;
    Date dataInizio;
    Date dataFine;
    String descr;
    String titolo;
    String tuttiEventi;
    
    /** Creates a new instance of EventBean */
    public EventBean() {
        
    }
    
    @PostConstruct
    public void init(){
        int idLocale;
        long idUtente;
        System.out.println("[EventBean] Inizializzazione bean");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        //Controllo se l'utente e' loggato
        try {
            loggedIn = (Boolean) httpSession.getAttribute("loggedIn");
        } catch (Exception e) {
            loggedIn = false;
        }
        //Recupero l'idUtente se loggato
        try {
            idUtente = (Long) httpSession.getAttribute("idUtente");
            
        } catch (NullPointerException e) {
            idUtente = -1;
        }
        //Controllo se sto visualizzando un locale
        try {
            idLocale = (Integer) httpSession.getAttribute("idLocale");
        } catch (NullPointerException e) {
            idLocale = -1;
        }
        //Caso in cui sto visualizzando un locale
        if (idLocale>=0) {
            locale = controlloreLocale.findById(idLocale);
            if (idUtente == locale.getIdUtente()) {
                gestore = true;
            }
            else {
                gestore = false;
            }
            eventi = controlloreLocale.getEventi(idLocale);
            tuttiEventi = eventi.toString();
            tuttiEventi = tuttiEventi+"gestore";
        }
        //Non sto visualizzando un locale ma sono loggato
        else if (loggedIn & idUtente >= 0){
            eventi = controlloreUtente.getEventi(idUtente);
            eventi.addAll(controlloreEventi.getEventi());
            tuttiEventi = eventi.toString();
            tuttiEventi = tuttiEventi+"utente";
        }
        else
            eventi = controlloreEventi.getEventi();
            tuttiEventi = eventi.toString();
    }
    
    public void addEvento(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        int idLocale = (Integer) httpSession.getAttribute("idLocale");
        controlloreLocale.addEvento(idLocale, dataInizio, dataFine, titolo, descr);
    }
    
    public Evento getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Evento selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public List<Evento> getEventi() {
        return eventi;
    }

    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

    public boolean isGestore() {
        return gestore;
    }

    public void setGestore(boolean gestore) {
        this.gestore = gestore;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTuttiEventi() {
        return tuttiEventi;
    }

    public void setTuttiEventi(String tuttiEventi) {
        this.tuttiEventi = tuttiEventi;
    }
}
