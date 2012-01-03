/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsfpkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import luncharoundpkg.ControlloreValutazioneLocal;
import luncharoundpkg.Locale;
import luncharoundpkg.LocaleFacadeLocal;

/**
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

    //questo viene chiamato dopo il costruttore, 
    // dopo che il container ha fatto la injection degli ejb
    @PostConstruct
    public void init() {
        System.out.println("[LocaliBean] Inizializzazione bean");
        try {
            locali = controlloreLocale.getTuttiLocali();
        } catch (NullPointerException e) {
            System.err.println("[LocaliBean.java] Non ci sono locali nel DB. Lista: " + controlloreLocale.locali());
            locali = new ArrayList<Locale>();
        }
        System.out.println("Locali presenti: " + controlloreLocale.locali());
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

    public void clearForm() {
        nuovo = new Locale();
        locali = null;
        setNome("");
        setIndirizzo("");
        setCitta("");
        setDescrizione("");
        return;
    }

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

    public String getTwitLocale() {
        return twitLocale;
    }

  
    
    public void setTwitLocale(String twitLocale) {
        this.twitLocale = twitLocale;
    }
    
    public List<Locale> getLocali() {
        return locali;
    }
    

    public Locale getNuovo() {
        return nuovo;
    }

    public void setNuovo(Locale nuovo) {
        this.nuovo = nuovo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }
    //</editor-fold>
}
