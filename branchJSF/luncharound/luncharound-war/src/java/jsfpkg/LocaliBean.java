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
@ManagedBean(name="LocaliBean")
@SessionScoped
public class LocaliBean implements Serializable{

    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    Locale nuovo = new Locale();
    List<Locale> locali;
    String nome;
    String indirizzo;
    String citta;
    
    
    /** Creates a new instance of LocaliBean */
    public LocaliBean() {
        // non fa nulla
    }
    
    //questo viene chiamato dopo il costruttore, 
    // dopo che il container ha fatto la injection degli ejb
    
    @PostConstruct
    public void init() {
                System.out.println("[LocaliBean] Inizializzazione bean");
        try{
           locali =  controlloreLocale.getTuttiLocali();
        }catch(NullPointerException e){
            System.err.println("[LocaliBean.java] Non ci sono locali nel DB. Lista grande: "+ controlloreLocale.locali());
        }
        System.out.println("Locali presenti: "+controlloreLocale.locali());
    }
    public List<Locale> getLocali(){
        return locali;
    }

    public Locale getNuovo() {
        return nuovo;
    }

    public void setNuovo(Locale nuovo) {
        this.nuovo = nuovo;
    }

    public void save(){
        nuovo = new Locale();
        nuovo.setIndirizzo(indirizzo);
        nuovo.setNome(nome);
        FacesMessage msg = null;
        System.out.println("[LocaliBean] Dentro save locale");
        controlloreLocale.addLocale(nome,indirizzo,(long)1,(double)12,(double)12,"io","asdfeafa");
         msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Locale aggiunto corettamente");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         clearForm();
        // ricarico la lista dei locali
        try{
           locali =  controlloreLocale.getTuttiLocali();
           
        }catch(NullPointerException e){
            System.err.println("[LocaliBean.java] Non ci sono locali nel DB. Lista grande: "+ controlloreLocale.locali());
        }
    }
   
    public void clearForm(){
        Locale nuovo = new Locale();
        locali = null;
        nome = "";
        indirizzo = "";
           
    }
            
    
    
    public void setNome(String nome){
        this.nome = nome;
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNome() {
        return nome;
    }
    
    public String visualizzaLocale(){
        System.out.println("visualizza Locale");
        // prendo la sessione
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(); 

        // prendo il parametro passato dalla jsf
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        int idlocale = Integer.parseInt(params.get("idLocale"));
        httpSession.setAttribute("idlocale",idlocale);
        System.out.println("idlocale: "+idlocale);
        return "visualizzaLocale";
       
        
    }
    

    
}
