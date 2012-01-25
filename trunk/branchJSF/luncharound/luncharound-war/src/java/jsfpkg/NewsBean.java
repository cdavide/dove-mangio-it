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
    String path;
    
    /** Creates a new instance of NewsBean */
    public NewsBean() {
    }
    
    /**
     * 
     */
    @PostConstruct
    public void init(){
        int idLocale;
        long idUtente;
        try{
            System.out.println("[VisualizzaLocale] Inizializzazione bean");
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession();
            path = request.getRequestURI();
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

            if(path.matches("/luncharound-war/faces/newsPage.xhtml")){
                news = controlloreNews.getNews();
            }
            else{
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
                }
                else{
                    news = controlloreNews.getNews();
                }
            }
        }
        catch (Exception e){
            news = null;
        }
    }
    
    /**
     * 
     */
    public void addNews(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        int idLocale = (Integer) httpSession.getAttribute("idLocale");
        controlloreLocale.addNews(idLocale, dataInizio, titolo, descr);
    }
    
    /**
     * 
     * @return
     */
    public String visualizzaLocale() {
        System.out.println("visualizza Locale");
        // prendo la sessione
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();

        // prendo il parametro passato dalla jsf
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        int idlocale = Integer.parseInt(params.get("idLocale"));
        httpSession.setAttribute("idLocale", idlocale);
        System.out.println("idLocale: " + idlocale);
        return "visualizzaLocale";
    }

    /**
     * 
     * @return
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * 
     * @param dataInizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * 
     * @return
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 
     * @param descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * 
     * @return
     */
    public boolean isGestore() {
        return gestore;
    }

    /**
     * 
     * @param gestore
     */
    public void setGestore(boolean gestore) {
        this.gestore = gestore;
    }

    /**
     * 
     * @return
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * 
     * @return
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * 
     * @param loggedIn
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * 
     * @return
     */
    public List<News> getNews() {
        return news;
    }

    /**
     * 
     * @param news
     */
    public void setNews(List<News> news) {
        this.news = news;
    }

    /**
     * 
     * @return
     */
    public News getSelectedNews() {
        return selectedNews;
    }

    /**
     * 
     * @param selectedNews
     */
    public void setSelectedNews(News selectedNews) {
        this.selectedNews = selectedNews;
    }

    /**
     * 
     * @return
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * 
     * @param titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * 
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    
}
