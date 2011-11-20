/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;


import java.awt.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
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
@ManagedBean(name="VisualizzaLocale")
@RequestScoped
public class VisualizzaLocale implements Serializable{

    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    @EJB
    private ControlloreValutazioneLocal controlloreValutazione;
    
    Locale locale;

    String mappa;
    String facebook;
    String menu;
    String offerte;
    String valutazioni;

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getOfferte() {
        return offerte;
    }

    public void setOfferte(String offerte) {
        this.offerte = offerte;
    }

    public String getValutazioni() {
        return valutazioni;
    }

    public void setValutazioni(String valutazioni) {
        this.valutazioni = valutazioni;
    }
    
    /** Creates a new instance of LocaliBean */
    public VisualizzaLocale() {
        // non fa nulla
    }
    
    //questo viene chiamato dopo il costruttore, 
    // dopo che il container ha fatto la injection degli ejb
    
    
    @PostConstruct
    public void init() {
        System.out.println("[VisualizzaLocale] Inizializzazione bean");
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(); 
        int idLocale = (Integer) httpSession.getAttribute ("idlocale");
        try{
           locale =  controlloreLocale.findById(idLocale);
        }catch(NullPointerException e){
            System.err.println("[Visualizza locale.java] Locale non trovato, impossibile visualizzarlo!");
        }
        mappa = "http://maps.google.com?q=" + locale.getIndirizzo();
                //creaMappaStatica(locale.getIndirizzo());
        
        System.out.println("mappa: "+mappa);
        facebook = creaFbDialog(locale);
        menu = controlloreLocale.mostraMenu(idLocale);
        offerte = controlloreLocale.mostraCombo(idLocale);
        
        //se è il proprietario deve viaualizzare statistiche e altre cose
        // non deve poter valutare
        
        valutazioni = controlloreValutazione.mostraValutazioni(request);
    }
    
    
    public Locale getLocale(){
        return locale;
    }

    public void setLocale(Locale locale){
        this.locale = locale;
    }

    public String getMappa() {
            return mappa;
     }
    
   public void setMappa(String url) {
            this.mappa = url;
     }

   //metodo per creare una mappa statica come immagine. da rendere parametrica?
    private String creaMappaStatica(String indirizzo) {
        String zoom = "15";
        String size = "400x400";
        String ret = "";
        ret += "<a href=\"http://maps.google.com?q=" + indirizzo + "\" ><img src=\"http://maps.googleapis.com/maps/api/staticmap?zoom=" + zoom;
        ret += "&size=" + size;
        ret += "&markers=icon:http://chart.apis.google.com/chart?chst=d_map_pin_icon%26chld=restaurant%257CFF6600";
        ret += "%7C" + indirizzo + "&sensor=false\"></a>";
        return ret;
    }
    
    private String creaFbDialog(Locale loc) {

        String dialog;
        String img_prova = "http://www.ahfourthgrade.net/resources/Charles-C--Ebbets-Lunch-Atop-A-Skyscraper-1932-8619.jpg";
        String url_prova = "http://localhost:8080/luncharound-war/";
        //url di prov       
        dialog = "<a href=\"";
        dialog += "https://www.facebook.com/dialog/feed?";
        dialog += "app_id=241460472572920&";
        dialog += "link=" + url_prova + "&";
        //sostituire con loc.getfoto!!
        dialog += "picture=" + img_prova + "&";
        dialog += "name=Io oggi vado a mangiare qui grazie a LunchAround!&";
        dialog += "caption=" + loc.getNome() + "&";
        dialog += "description=Si trova in " + loc.getIndirizzo() + ". "
                + "   Clicca qui per visualizzare il loro menu' di oggi!&";
        dialog += "redirect_uri=http://www.facebook.com\"";
        dialog += " target=\"_blank\">"
                + "<img src=http://tuttoilweb.myblog.it/media/00/02/579068133.png"
                + "  width=\"250\" height=\"30\"></a>";

        return dialog;
    }

    
}
