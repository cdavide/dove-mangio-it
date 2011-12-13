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
import luncharoundpkg.Locale;
import luncharoundpkg.News;
import luncharoundpkg.ControlloreNewsLocal;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@ManagedBean(name = "newsBean")
@RequestScoped
public class NewsBean {
    
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    @EJB
    private ControlloreUtenteLocal controlloreUtente;
    @EJB
    private ControlloreNewsLocal controlloreNews;
    private List<News> news;
    private News selectedNews;
    boolean gestore; // per c:if nella jsf
    boolean loggedIn;
    Locale locale;
    Date dataInizio;
    String descr;
    String titolo;
    String tutteNews;
    
    /** Creates a new instance of NewsBean */
    public NewsBean() {
    }
    
    public void init(){
        int idLocale;
        long idUtente;
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
            news = controlloreLocale.getNews(idLocale);
            tutteNews = news.toString();
            tutteNews = tutteNews+"gestore";
        }
        //Non sto visualizzando un locale ma sono loggato
        else if (loggedIn & idUtente >= 0){
            news = controlloreUtente.getNews(idUtente);
            news.addAll(controlloreNews.getNews());
            tutteNews = news.toString();
            tutteNews = tutteNews+"utente";
        }
        else
            news = controlloreNews.getNews();
    }
    
    public void addNews(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        int idLocale = (Integer) httpSession.getAttribute("idLocale");
        controlloreLocale.addNews(idLocale, dataInizio, titolo, descr);
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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public News getSelectedNews() {
        return selectedNews;
    }

    public void setSelectedNews(News selectedNews) {
        this.selectedNews = selectedNews;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTutteNews() {
        return tutteNews;
    }

    public void setTutteNews(String tutteNews) {
        this.tutteNews = tutteNews;
    }
    
    
}
