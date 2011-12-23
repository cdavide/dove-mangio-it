/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import luncharoundpkg.Locale;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
@ManagedBean(name="ricercaBean")
@ViewScoped
public class RicercaBean{
    @EJB
    ControlloreLocaleLocal controlloreLocale;
    @EJB
    ControlloreUtenteLocal controlloreUtente;
    
    //parametri del front-end
    String indirizzo;
    double latitudine;
    double longitudine;
    boolean salva;
    int distanza;
    int tipo;
    //variabili del bean per l'output
    boolean noSearch;
    boolean zeroResults;
    private MapModel advancedModel;
    private Marker marker;
    int zoom;
    List<Locale> lista;

    /** Creates a new instance of RicercaBean */
    public RicercaBean() {
        
    }
    
    @PostConstruct
    public void init(){
    
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession session = request.getSession(); 
        
        if(session.getAttribute("home")!=null){ indirizzo=(String)session.getAttribute("home");}
        
        latitudine=0;
        longitudine=0;
        distanza=1;
        salva=false;
        noSearch=true;
        zeroResults=true;
        zoom=15;
        tipo=1;
        
    }
    
    public void submit(){
        create_results();
        set_zoom();
        save_location();
        noSearch=false;
    }
  
    public void create_results(){
        int i;
        String pushPin;
        String color;
        String description;
        LatLng tempPoint;    
        advancedModel = new DefaultMapModel();
        
        // creo un marker per la posizione attuale, con icona diversa dalle altre
        tempPoint=new LatLng(latitudine,longitudine);
        pushPin="https://chart.googleapis.com/chart?chst=d_map_pin_icon&chld=glyphish_target%7C00AAFF";
        advancedModel.addOverlay(new Marker(tempPoint,"La tua posizione","",pushPin));
        
        lista=controlloreLocale.trovaLocali(latitudine, longitudine, distanza);
        
        if(lista.size()==0){
            zeroResults=true;
            return;
        }
        
        zeroResults=false;               
        i=0;
        for(Locale loc : lista){
            i++;
            tempPoint = new LatLng(loc.getLatitudine(),loc.getLongitudine());

            if(controlloreLocale.menuValido(loc.getId())) color="52B552";//colore sfondo pin
            else color="FA3333";
                
            pushPin="https://chart.googleapis.com/chart?"
                        + "chst=d_bubble_icon_text_small&"
                        + "chld=restaurant%7C"
                        + "bb%7C"+i+"%7C"+color+"%7C000000";
            
            description=loc.getNome()+"</br>"+loc.getIndirizzo();
            
            advancedModel.addOverlay(new Marker(tempPoint,description,"manca locale.getfoto!!!",pushPin));
        }
    }
        
    
    public void set_zoom(){
        //visualizzazione ottimale della mappa
        switch (distanza) {
            case 20:
                zoom = 10;
                break;
            case 15:
                zoom = 11;
                break;
            case 10:
                zoom = 12;
                break;
            case 5:
                zoom = 12;
                break;
            case 2:
                zoom = 14;
                break;
            case 1:
                zoom = 15;
                break;
            default:
                zoom = 11;
                break;
        }
        
    
    }

    public String visualizzaLocale() {
        System.err.println("visualizza Locale");
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
    
    public String go(){
        return "visualizzaLocale";
    } 
    
    //metodo per nuvoletta sulla mappa
        public void onMarkerSelect(OverlaySelectEvent event) { 
        System.err.println("dentro!");
        marker = (Marker) event.getOverlay();  
    } 
      
    public Marker getMarker() {  
        return marker;  
    }
    
    
    public void save_location(){
       
        if(salva){
            FacesContext context = FacesContext.getCurrentInstance();  
            HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
            HttpSession session = request.getSession(); 
            Long id=(Long)session.getAttribute("idUtente");
            
            if(id!=null){
                controlloreUtente.editHome(id, indirizzo);
                session.setAttribute("home",indirizzo);
            }
        } 
    }




    
    //<editor-fold defaultstate="collapsed" desc="costruttori">
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public List<Locale> getLista() {
        return lista;
    }

    public void setLista(List<Locale> lista) {
        this.lista = lista;
    }
    
    
    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
    
    public boolean isZeroResults() {
        return zeroResults;
    }

    public void setZeroResults(boolean zeroResults) {
        this.zeroResults = zeroResults;
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void setAdvancedModel(MapModel advancedModel) {
        this.advancedModel = advancedModel;
    }
    
      
    public boolean isNoSearch() {
        return noSearch;
    }

    public void setNoSearch(boolean noSearch) {
        this.noSearch = noSearch;
    }
    
    public String getIndirizzo() {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getDistanza() {
        return distanza;
    }

    public void setDistanza(int distanza) {
        this.distanza = distanza;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
    
    public boolean isSalva() {
        return salva;
    }
    
    public void setSalva(boolean salva) {
        this.salva = salva;
    }
    //</editor-fold>
    
}
