/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import luncharoundpkg.Locale;
import luncharoundpkg.Utente;
import org.primefaces.context.RequestContext;

/** Managed Bean che gestisce il login
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    @EJB
    private ControlloreUtenteLocal controlloreUtente;
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    private String username;
    private String password;
    private String indirizzo;
    private String mail;
    private String foto;
    private boolean hasPhoto;
    private boolean loggedIn;
    private boolean reg;
    private String fburl;
    private boolean gestore; // indica se l'utente è un gestore oppure no
    //serve per il menu laterale

    // empty constructor
    /** costruttore della classe
     * 
     */
    public LoginBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        try {
            setGestore((Boolean) httpSession.getAttribute("gestore"));
            setLoggedIn((Boolean) httpSession.getAttribute("loggedIn"));
            setUsername((String) httpSession.getAttribute("nome_utente"));
            setIndirizzo((String) httpSession.getAttribute("home"));
            setFoto((String) httpSession.getAttribute("foto"));
        } catch (Exception e) {
            System.out.println("LoginBean.java fresh session");
        }
        fburl  = "<a href=\"https://www.facebook.com/dialog/oauth?"
                + "client_id=241460472572920&"
                + "redirect_uri=http://localhost:8080/luncharound-war/FacebookServlet&"
                + "scope=email,user_location\">"
                + "<img src=\"resources/Facebook.png\">"
                + "</a>";

        System.err.println("Inizializzazione bean [LoginBean.java]");
    }

    /** effettua il login dell'utente
     * 
     * @return la stringa "home"
     */
    public String login() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        loggedIn = false;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();

        Utente persona = controlloreUtente.verificaPassword(username, password);

        if (persona == null) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        } else {

            loggedIn = true;

            httpSession.setAttribute("nome_utente", persona.getUsername());
            httpSession.setAttribute("idUtente", persona.getId());
            httpSession.setAttribute("eventi", persona.isEventi());
            httpSession.setAttribute("news", persona.isNews());
            httpSession.setAttribute("home", persona.getHome());
            httpSession.setAttribute("tipo", persona.getTipo());
            httpSession.setAttribute("loggedIn", loggedIn);
            httpSession.setAttribute("gestore", gestore);
            if (null == persona.getFoto() || persona.getFoto().equals("")) {
                foto = "resources/user.png";
            } else {
                foto = persona.getFoto();
            }
            httpSession.setAttribute("foto", foto);
            System.err.println("Utente Loggato");
            try {
                Locale loc = controlloreLocale.getLocali(persona.getId());
                System.err.println("l'utnte è il gestore del locale: " + loc.getNome());
                httpSession.setAttribute("mioLocale", loc.getId()); // aggiunta variabile per locale personale
                httpSession.setAttribute("idLocale", loc.getId()); // aggiunta variabile per locale da visualizzare
                setGestore(true);
                System.err.println("gest: " + gestore);
            } catch (NullPointerException e) {
                System.err.println("L'utente non ha associato nessun locale personale");
                httpSession.setAttribute("emptyLocal", true); // serve per visualizzare un link di aggiunta locale
                // se l'utente non ha ancora aggiunto alcun locale
            }
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bentornato su LunchAround", username);
            // cercare sulle news e sugli eventi a cui l-utente e` abbonato e aggiungere i popup 
            // per avvisarlo
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("gestore", gestore);
        httpSession.setAttribute("loggedIn", loggedIn);
        httpSession.setAttribute("gestore", gestore);
        
        return "home";
    }
    

    /** metodo per registrare i dati dell'utente
     * 
     * @param actionEvent l'evento che genera la chiamata
     */
    public void register(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        reg = false;
        controlloreUtente.addUtente(username, password, indirizzo, mail, foto);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione effettuata con successo su LunchAround", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reg = true;
        try {
            context.addCallbackParam("reg", reg);
        } catch (Exception e) {
            System.out.println("Errore, impossibile inserire parametri nella callback");
        }
    }

    //
    /** metodo per controllare se è stato tentato un login
     * 
     */
    public void checkLogin() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean newLogin;

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();

        try {
            setLoggedIn((Boolean) httpSession.getAttribute("loggedIn"));
        } catch (Exception e) {
            setLoggedIn(false);
        }
        try {
            newLogin = (Boolean) httpSession.getAttribute("newLogin");

        } catch (Exception e) {

            newLogin = false;
        }
        if (newLogin) { //c'è stato un tentativo di login recente

            httpSession.setAttribute("newLogin", false); //pulisco
            if (httpSession.getAttribute("errore") != null) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, (String) httpSession.getAttribute("errore"), "");
                httpSession.removeAttribute("errore"); //pulisco

            } else {//non ci sono errori
                loggedIn = true; //tutti i dati dell'utente sono già in sessione!
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciao " + (String) httpSession.getAttribute("nome_utente"), "");
                foto = (String) httpSession.getAttribute("foto");
                try {
                    Locale loc = controlloreLocale.getLocali((Long) httpSession.getAttribute("idUtente"));
                    System.err.println("l'utnte è il gestore del locale: " + loc.getNome());
                    httpSession.setAttribute("mioLocale", loc.getId()); // aggiunta variabile per locale personale
                    httpSession.setAttribute("idLocale", loc.getId()); // aggiunta variabile per locale da visualizzare
                    setGestore(true);
                    System.err.println("gest: " + gestore);
                } catch (NullPointerException e) {
                    System.err.println("L'utente non ha associato nessun locale personale");
                    httpSession.setAttribute("emptyLocal", true); // serve per visualizzare un link di aggiunta locale
                    // se l'utente non ha ancora aggiunto alcun locale
                }
                context.addCallbackParam("loggedIn", loggedIn);
                context.addCallbackParam("gestore", gestore);
                httpSession.setAttribute("loggedIn", loggedIn);
                httpSession.setAttribute("gestore", gestore);
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        context.addCallbackParam("newLogin", newLogin);

    }

    /* 
     * 
     */
    /** Metodo per invalidare la sessione utente
     * 
     * @return la stringa "home"
     */
    public String logout() {
        System.out.println("[Login Bean ] logout");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        setLoggedIn(false);
        setGestore(false);
        httpSession.setAttribute("loggedIn", false);
        httpSession.setAttribute("gestore", false);
        httpSession.setAttribute("nome_utente", "");
        httpSession.setAttribute("idUtente", null);
        httpSession.setAttribute("eventi", false);
        httpSession.setAttribute("news", false);
        httpSession.setAttribute("home", "");
        httpSession.setAttribute("tipo", "");
        httpSession.setAttribute("foto", "");
        httpSession.setAttribute("mioLocale", null);
        httpSession.setAttribute("idLocale", null);
        httpSession.setAttribute("oauth_token", null);
        httpSession.setAttribute("oauth_token_secret", null);
        //try{
        //    httpSession.removeAttribute(mail);
        //}
        //catch(Exception e) {
        //    System.out.println("Non ero loggato via twitter");
        //}
        FacesMessage msg = null;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout effettuato con successo", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        /*rimando l'utente alla home per evitare
        che le jsf si rompano*/
        System.err.println("Sessione invalidata, rimando alla homepage");
        return "home";
    }

    /** metodo per ritornare alla home
     * 
     * @return stringa "home"
     */
    public String doNavigation() {
        return "home";
    }

    /** metodo per il login da twitter
     * 
     */
    public void twitLogin() {
        String url = "/TwitterServlet?metodo=login";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext context2 = FacesContext.getCurrentInstance();
        loggedIn = false;
        gestore = false;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("loggedIn", loggedIn);
        httpSession.setAttribute("gestore", gestore);
        try {
            context2.getExternalContext().dispatch(url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context2.responseComplete();
        }
    }

    
    
    /** metodo per visualizzare il mio locale
     * 
     * @return la stringa "visualizzalocale"
     */
    public   String visualizzaMioLocale(){
         HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("idLocale",httpSession.getAttribute("mioLocale"));
        return "visualizzaLocale";
    }
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     * 
     * @return
     */
    public boolean isLoggedIn() {
        return loggedIn;
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
     * @param loggedIn
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * 
     * @return
     */
    public String getFoto() {
        return foto;
    }

    /**
     * 
     * @param foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * 
     * @return
     */
    public String getMail() {
        return mail;
    }

    /**
     * 
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
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
     * @param indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     */
    public boolean isHasPhoto() {
        return hasPhoto;
    }

    /**
     * 
     * @param hasPhoto
     */
    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    /**
     * 
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     */
    public boolean isReg() {
        return reg;
    }

    /**
     * 
     * @param reg
     */
    public void setReg(boolean reg) {
        this.reg = reg;
    }

    /**
     * 
     * @return
     */
    public String getFburl() {

        return this.fburl;
    }

    /**
     * 
     * @param fburl
     */
    public void setFburl(String fburl) {
        this.fburl = fburl;
    }
    //</editor-fold>
}
