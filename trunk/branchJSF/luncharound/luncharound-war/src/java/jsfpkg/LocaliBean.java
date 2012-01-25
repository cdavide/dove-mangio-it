/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsfpkg;

import java.io.Serializable;
import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.Locale;

/**Session Bean che contiene le funzioni per gestire la parte di presentazione 
 * dei locali
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "localiBean")
@RequestScoped
public class LocaliBean implements Serializable {
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    Locale nuovo = new Locale();
    List<Locale> locali;
    String nome;
    String indirizzo;
    String citta;
    String descrizione;
    double lon;
    double lat;
    String piva = "";
    String twitLocale;
    
    /** Creates a new instance of LocaliBean */
    public LocaliBean() {
        // non fa nulla
        System.err.println("Inizializzazione bean [LocaliBean.java]");
        twitLocale= "";
    }


    /**Metodo che inizializza le variabili nel frontend richiamando i servizi nel backend
     * questo viene chiamato dopo il costruttore, 
     * dopo che il container ha fatto la injection degli ejb
     */
    @PostConstruct
    public void init() {
        System.out.println("[LocaliBean] Inizializzazione bean");
        try {
            locali = controlloreLocale.getTuttiLocali();
        } catch (NullPointerException e) {
            System.err.println("[LocaliBean.java] Non ci sono locali nel DB. Lista: " + controlloreLocale.locali());
            locali = new ArrayList<Locale>();
        }
        //System.out.println("Locali presenti: " + controlloreLocale.locali());
        twitLocale = "<a href='https://twitter.com/share'"
                + "class='twitter-share-button' "
                + "data-text='I love to eat at "
                + nome + " ' "
                + "data-count='none' "
                + "data-via='luncharound'>"
                + "Tweet"
                + "</a>"
                + "<script type='text/javascript' "
                + "src='//platform.twitter.com/widgets.js'>"
                + "</script>";
    }
    

    /**Aggiunge un locale nel DB
     * 
     * @return null in modo da ricaricare la pagina (serve per le JSF)
     */
    public String save() {
        FacesMessage msg = null;
        // prendo la sessione
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();

        //controllare id utente altrimenti non si puo procedere, oppure che la sessione sia valida
        if ((Long) httpSession.getAttribute("idUtente") == null || 
                !request.isRequestedSessionIdValid() || 
                httpSession.getAttribute("mioLocale") != null) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Errore!", "Errore registrazione locale.Controlla: login, session,un solo locale per username");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            return null;
        }

       
        try{
            long idUtente =  (Long) httpSession.getAttribute("idUtente");
            String username =  (String) httpSession.getAttribute("nome_utente");
            controlloreLocale.addLocale(nome,
                indirizzo,
                idUtente,
                lon,
                lat,
                username,
                piva, descrizione);
            httpSession.setAttribute("mioLocale", controlloreLocale.getLocali(idUtente).getId());
            httpSession.setAttribute("idLocale", controlloreLocale.getLocali(idUtente).getId());
            httpSession.setAttribute("gestore", true);
        }
        catch(Exception e){
            System.out.println("[LocaliBean] eccezione su inserimento in DB"+e.getLocalizedMessage().toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Failure!", "Locale non aggiunto");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            clearForm();
            return null;
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Locale aggiunto corettamente");
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
        clearForm();
        
        // ricarico la lista dei locali
        try {
            locali = controlloreLocale.getTuttiLocali();

        } catch (NullPointerException e) {
            System.err.println("[LocaliBean.java] Non ci sono locali nel DB. Lista : " + controlloreLocale.locali());
        }
        return "home";
    }

    
    /**Pulisce le variabili locali per il form
     * 
     */
    public void clearForm() {
        nuovo = new Locale();
        locali = null;
        setNome("");
        setIndirizzo("");
        setCitta("");
        setDescrizione("");
        return;
    }

    /**Metodo che riceve l'id di un locale,aggiunge alla sessione 
     * il valore id locale e reindirizza l'utente sulla pagina visualizzalocale.xhtml
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
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    /**
     * 
     * @return
     */
    public String getTwitLocale() {
        return twitLocale;
    }

  
    
    /**
     * 
     * @param twitLocale
     */
    public void setTwitLocale(String twitLocale) {
        this.twitLocale = twitLocale;
    }
    
    /**
     * 
     * @return
     */
    public List<Locale> getLocali() {
        return locali;
    }
    

    /**
     * 
     * @return
     */
    public Locale getNuovo() {
        return nuovo;
    }

    /**
     * 
     * @param nuovo
     */
    public void setNuovo(Locale nuovo) {
        this.nuovo = nuovo;
    }

    /**
     * 
     * @return
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * 
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * 
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * 
     * @param indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * 
     * @return
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * 
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * 
     * @return
     */
    public String getCitta() {
        return citta;
    }

    /**
     * 
     * @param citta
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     * 
     * @return
     */
    public double getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * 
     * @return
     */
    public double getLon() {
        return lon;
    }

    /**
     * 
     * @param lon
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * 
     * @return
     */
    public String getPiva() {
        return piva;
    }

    /**
     * 
     * @param piva
     */
    public void setPiva(String piva) {
        this.piva = piva;
    }
    //</editor-fold>
}
