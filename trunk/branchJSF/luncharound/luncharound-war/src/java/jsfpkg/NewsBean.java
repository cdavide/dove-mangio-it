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

/**Managed Bean che contiene le funzioni di backend visualizzaLocale.xhtml e in newsPage.xhtml
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
    
    /**Inizializza le variabili del session bean ricavando la lista delle news dal controlloreNews o dal controlloreLocale a seconda della pagina
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
    
    /**Aggiunge una news al sistema a partire dai volori contenuti nei paramentri del bean
     * 
     */
    public void addNews(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        int idLocale = (Integer) httpSession.getAttribute("idLocale");
        controlloreLocale.addNews(idLocale, dataInizio, titolo, descr);
    }
    
    /**Imposta l'id del locale come variabile di sessione e ritorna la pagina per visualizzarlo
     * 
     * @return Ritorna il nome della pagina da aprire
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

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    /**Ritorna la data iniziale della variabile del bean
     *
     * @return la data iniziale
     */
    public Date getDataInizio() {
        return dataInizio;
    }
    
    /**Imposta la data iniziale della variabile del bean
     *
     * @param dataInizio la data da impostare
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }
    
    /**Ritorna la descrizione della variabile del bean
     *
     * @return la descrizione
     */
    public String getDescr() {
        return descr;
    }
    
    /**Imposta la descrizione della variabile del bean
     *
     * @param descr la descrizione da impostare
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }
    
    /**Ritorna il booleano che mi dice se il locale visualizzato è quello dell'utente che lo sta visualizzando
     *
     * @return true se è il gestore, false altrimenti
     */
    public boolean isGestore() {
        return gestore;
    }
    
    /**Imposta il booleano che indica se un utente è il gestore del locale visualizzato
     *
     * @param gestore il valore da impostare
     */
    public void setGestore(boolean gestore) {
        this.gestore = gestore;
    }
    
    /**Il locale visualizzato
     *
     * @return il locale
     */
    public Locale getLocale() {
        return locale;
    }
    
    /**Imposta il locale visualizzato
     *
     * @param locale il locale da impostare
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    /**Controlla il booleano che mi dice se l'utente è loggato o meno
     *
     * @return true se è loggato, false altrimenti
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    /**Imposta il booleano che mi dice se l'utente è loggato o meno
     *
     * @param loggedIn valore da impostare
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    /**Ritorna la lista di news da visualizzare
     *
     * @return lista di news
     */
    public List<News> getNews() {
        return news;
    }
    
    /**Imposta la lista di news da visualizzare
     *
     * @param news la lista di news
     */
    public void setNews(List<News> news) {
        this.news = news;
    }
    
    /**Ritorna la news selezionata
     *
     * @return la news selezionata
     */
    public News getSelectedNews() {
        return selectedNews;
    }
    
    /**Imposta la news selezionata
     *
     * @param selectedNews la news da impostare
     */
    public void setSelectedNews(News selectedNews) {
        this.selectedNews = selectedNews;
    }
    
    /**Ritorna il titolo della variabile del bean
     *
     * @return il titolo
     */
    public String getTitolo() {
        return titolo;
    }
    
    /**Imposta il titolo della variabile del bean
     *
     * @param titolo il titolo da impostare
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    
    /**Path della pagina visualizzata
     *
     * @return il path
     */
    public String getPath() {
        return path;
    }
    
    /**Imposta il path della pagina visualizzata
     *
     * @param path il path da impostare
     */
    public void setPath(String path) {
        this.path = path;
    }
    //</editor-fold>
    
    
}
