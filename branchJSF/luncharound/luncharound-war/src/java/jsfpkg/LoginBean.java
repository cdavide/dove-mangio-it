/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import luncharoundpkg.Utente;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Bovio Loernzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "LoginBean")
@RequestScoped
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

    // empty constructor
    public LoginBean() {
    }

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
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
            try {
                httpSession.setAttribute("localipersonali", controlloreLocale.getLocali(persona.getId()));
            } catch (NullPointerException e) {
                System.err.println("L'utente non ha associato nessun locale personale");
            }
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bentornato su LunchAround", username);
            // cercare sulle news e sugli eventi a cui l-utente e` abbonato e aggiungere i popup 
            // per avvisarlo

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);


    }

    public void register(ActionEvent actionEvent) {
        System.err.println("Entra in register");
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        boolean reg = false;

        controlloreUtente.addUtente(username, password, indirizzo, mail, foto);
        
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione effettuata con successo su LunchAround","");
            // cercare sulle news e sugli eventi a cui l-utente e` abbonato e aggiungere i popup 
            // per avvisarlo

        FacesContext.getCurrentInstance().addMessage(null, msg);
        reg = true;
        context.addCallbackParam("reg", reg);


    }
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //</editor-fold>
}
