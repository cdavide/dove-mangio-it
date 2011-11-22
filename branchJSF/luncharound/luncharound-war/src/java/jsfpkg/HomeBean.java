/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.Locale;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@ManagedBean(name="HomeBean")
@RequestScoped
public class HomeBean {
    @EJB
    ControlloreLocaleLocal controlloreLocale;
    private MapModel simpleModel;   
    double centerLat;
    double centerLong;
    /** Creates a new instance of HomeBean */
    public HomeBean() {
    }
    
    @PostConstruct
    public void init(){
        simpleModel = new DefaultMapModel();
        List<Locale> current = controlloreLocale.getTuttiLocali();
        List<Marker> ml = new ArrayList<Marker>();
        List<LatLng> cl = new ArrayList<LatLng>();
        int i=0;
        for(Locale ll: current){
            centerLat= ll.getLatitudine();
            centerLong= ll.getLongitudine();
            //Basic marker
            cl.add(i,new LatLng(ll.getLatitudine(),ll.getLongitudine()));
            ml.add(i, new Marker(cl.get(i), ll.getNome()));
            
            simpleModel.addOverlay(ml.get(i));
            i++;
            
        }
    }
    
    
    public String backHome(){
        return "home";
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public double getCenterLat() {
        return centerLat;
    }
    
    public void setCenterLat(double centerLat) {
        this.centerLat = centerLat;
    }
    
    public double getCenterLong() {
        return centerLong;
    }
    
    public void setCenterLong(double centerLong) {
        this.centerLong = centerLong;
    }
    
    public ControlloreLocaleLocal getControlloreLocale() {
        return controlloreLocale;
    }
    
    public void setControlloreLocale(ControlloreLocaleLocal controlloreLocale) {
        this.controlloreLocale = controlloreLocale;
    }
    
    public MapModel getSimpleModel() {
        return simpleModel;
    }
    
    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }
    //</editor-fold>
}
