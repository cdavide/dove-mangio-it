/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import luncharoundpkg.Locale;
import luncharoundpkg.Evento;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
    private List<Evento> eventi;
    private Evento selectedEvent;
    boolean gestore; // per c:if nella jsf
    boolean loggedIn;
    Locale locale;
    Date dataInizio;
    Date dataFine;
    String descr;
    String titolo;
    
    /** Creates a new instance of EventBean */
    public EventBean() {
    }
    
    public void init(){
        int idLocale;
        long idUtente;
        dataInizio = new Date();
        dataFine = new Date();
        titolo = "Nuovo evento";
        descr = "Descrizione evento";
        System.out.println("[VisualizzaLocale] Inizializzazione bean");
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
        }
        //Non sto visualizzando un locale ma sono loggato
        else if (loggedIn & idUtente >= 0){
            eventi = controlloreUtente.getEventi(idUtente);
        }
        else
            eventi = null;
    }
    
    public void addEvento(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        int idLocale = (Integer) httpSession.getAttribute("idLocale");
        controlloreLocale.addEvento(idLocale, dataInizio, dataFine, titolo, descr);
        dataInizio = new Date();
        dataFine = new Date();
        titolo = "Nuovo evento";
        descr = "Descrizione evento";
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
}
