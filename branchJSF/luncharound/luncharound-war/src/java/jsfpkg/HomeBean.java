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
import javax.faces.bean.ManagedBean;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.Locale;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**Managed Bean che serve per gestire la homepage
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@ManagedBean(name="homeBean")
@RequestScoped
public class HomeBean {
    @EJB
    ControlloreLocaleLocal controlloreLocale;
    String TwitPage;
    String FollowLA;
    private MapModel simpleModel;   
    double centerLat;
    double centerLong;
    String FBLike;
    
    /** Creates a new instance of HomeBean */
    public HomeBean() {
    }
    
    /**Inizializza la mappa principale utilizzando le posizioni dei locali
     * interrogando il DB attraverso i servizi del backend
     * 
     */
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
        FBLike="<iframe src=\"//www.facebook.com/plugins/like.php?locale=it_IT&"
                + "app_id=241460472572920&amp;"
                + "href=www.facebook.com/luncharound&amp;"
                + "send=false&amp;"
                + "layout=standard&amp;"
                + "width=600&amp;"
                + "show_faces=true&amp;"
                + "action=like&amp;"
                + "colorscheme=light&amp;"
                + "font&amp;height=100\" "
                + "scrolling=\"no\" "
                + "frameborder=\"0\" "
                + "style=\"border:none; "
                + "overflow:hidden; "
                + "height:28px;\" "
                + "allowTransparency=\"true\">"
                + "</iframe>";
        
        TwitPage = "<a href='https://twitter.com/share'"
                + "class='twitter-share-button' "
                + "data-text='I love this website!' "
                + "data-count='horizontal' "
                + "data-via='luncharound'>"
                + "Tweet"
                + "</a>"
                + "<script type='text/javascript' "
                + "src='//platform.twitter.com/widgets.js'>"
                + "</script>";
        
        FollowLA = "<a href='https://twitter.com/luncharound' "
                + "class='twitter-follow-button' "
                + "data-show-count='false'>"
                + "Follow @luncharound"
                + "</a>"
                + "<script src='//platform.twitter.com/widgets.js' "
                + "type='text/javascript'>"
                + "</script>";
        
    }
    

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    /**
     * 
     * @return
     */
    public double getCenterLat() {
        return centerLat;
    }
    
    /**
     * 
     * @param centerLat
     */
    public void setCenterLat(double centerLat) {
        this.centerLat = centerLat;
    }
    
    /**
     * 
     * @return
     */
    public double getCenterLong() {
        return centerLong;
    }
    
    /**
     * 
     * @param centerLong
     */
    public void setCenterLong(double centerLong) {
        this.centerLong = centerLong;
    }
    
    /**
     * 
     * @return
     */
    public ControlloreLocaleLocal getControlloreLocale() {
        return controlloreLocale;
    }
    
    /**
     * 
     * @param controlloreLocale
     */
    public void setControlloreLocale(ControlloreLocaleLocal controlloreLocale) {
        this.controlloreLocale = controlloreLocale;
    }
    
    /**
     * 
     * @return
     */
    public MapModel getSimpleModel() {
        return simpleModel;
    }
    
    /**
     * 
     * @param simpleModel
     */
    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }
    
    
        /**
     * 
     * @return
     */
    public String getFollowLA() {
        return FollowLA;
    }

    /**
     * 
     * @param FollowLA
     */
    public void setFollowLA(String FollowLA) {
        this.FollowLA = FollowLA;
    }

    /**
     * 
     * @return
     */
    public String getTwitPage() {
        return TwitPage;
    }

    /**
     * 
     * @param TwitPage
     */
    public void setTwitPage(String TwitPage) {
        this.TwitPage = TwitPage;
    }
    
    /**
     * 
     * @return
     */
    public String backHome(){
        return "home";
    }

    /**
     * 
     * @return
     */
    public String getFBLike() {
        return FBLike;
    }

    /**
     * 
     * @param FBLike
     */
    public void setFBLike(String FBLike) {
        this.FBLike = FBLike;
    }
    
    //</editor-fold>
}
