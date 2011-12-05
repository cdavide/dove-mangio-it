/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.io.Serializable;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.Locale;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean(name="mapBean")
@RequestScoped
public class MapBean implements Serializable {
    @EJB
    ControlloreLocaleLocal controlloreLocale;

    private MapModel simpleModel;
    double centerLat;
    double centerLong;

    /*Costruttore vuoto*/
    public MapBean() {
    }
    
    /*Post costruttore chiamato dopo l'injection delle dipendenze sul backend*/
    @PostConstruct
    public void init(){
        simpleModel = new DefaultMapModel();
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(); 
        
        int idLocale = (Integer) httpSession.getAttribute("idLocale");
        Locale current = controlloreLocale.findById(idLocale);
        
        //Shared coordinates
        LatLng coord1 = new LatLng(current.getLatitudine(),current.getLongitudine());
        centerLat= current.getLatitudine();
        centerLong= current.getLongitudine();

        //Basic marker
        simpleModel.addOverlay(new Marker(coord1, current.getNome()));
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
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

    public MapModel getSimpleModel() {
        return simpleModel;
    }
    //</editor-fold>
}
