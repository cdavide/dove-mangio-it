/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
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
    private boolean loggedIn;
    private boolean reg;
    private String fburl;
    // empty constructor
    public LoginBean() {
    }

    
    
    public void login(ActionEvent actionEvent) {
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
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        reg = false;
        controlloreUtente.addUtente(username, password, indirizzo, mail, foto);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione effettuata con successo su LunchAround","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reg = true;
        try{
            context.addCallbackParam("reg", reg);    
        }
        catch(Exception e){
            System.out.println("Errore, impossibile inserire parametri nella callback");
        }
    }
    
    //metodo per controllare se è stato tentato un login
    public void checkLogin(){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        loggedIn = false;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        boolean newLogin=(Boolean)httpSession.getAttribute("newLogin");
        
        if(newLogin){ //c'è stato un tentativo di login recente
            
            httpSession.setAttribute("newLogin",false); //pulisco
            
            if(httpSession.getAttribute("errore")!=null){
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN,(String)httpSession.getAttribute("errore"),"");
                httpSession.removeAttribute("errore"); //pulisco

            }
            else{//non ci sono errori
                loggedIn=true; //tutti i dati dell'utente sono già in sessione!
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Ciao "+(String)httpSession.getAttribute("nome_utente"),"");
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }  
        context.addCallbackParam("newLogin", newLogin);    

    }
    
    
    
    /* Metodo per invalidare la sessione utente
     * l
     */
    
    public void logout(){
        System.out.println("[Login Bean ] logout");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        final HttpServletRequest request = (HttpServletRequest)ec.getRequest();
        request.getSession( false ).invalidate();
        FacesMessage msg = null;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout effettuato con successo","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }

    public boolean isReg() {
        return reg;
    }

    public void setReg(boolean reg) {
        this.reg = reg;
    }

    public String getFburl() {
        String url="<a href=\"https://www.facebook.com/dialog/oauth?"
                + "client_id=241460472572920&"
                + "redirect_uri=http://localhost:8080/luncharound-war/FacebookServlet&"
                + "scope=email,user_location\">"
                + "<img src=\"https://meetin.gs/images/meetings/facebook_login_button.png\" heigh=\"40\" width=\"280\">"
                + "</a>";

        return url;
    }

    public void setFburl(String fburl) {
        this.fburl = fburl;
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    
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
