/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreValutazioneLocal;
import luncharoundpkg.Valutazione;


@ManagedBean(name="ratingController")
@SessionScoped
public class RatingController {
    
    @EJB
    private ControlloreValutazioneLocal controlloreValutazione;
    
    Valutazione media;
    Valutazione userRate ;
    int idLocale;
    long idUtente;
    double pulizia = 0;
    double qualita = 0;
    double velocita = 0;
    double affollamento = 0;
    double quantita = 0;
    double cortesia = 0;
    // variabili per la media
    double pul = 0;
    double qual = 0;
    double vel = 0;
    double aff = 0;
    double quan = 0;
    double cort = 0;
    
    
    
    
    /* Questo metodo deve sottomettere la votazione e ricaricare le medie.
     */
    public void submitRating(){
        if(userRate  == null) System.err.println("Nullo!!!");
        userRate.setDataVal(new Date());
        userRate.setIdLocale(idLocale);
        userRate.setIdUtente(idUtente);
        userRate.setAffollamento((int)affollamento);
        userRate.setCortesia((int)cortesia);
        userRate.setPulizia((int)pulizia);
        userRate.setQualita((int)qualita);
        userRate.setQuantita((int)quantita);
        userRate.setVelocita((int)velocita);
        System.err.println("[RatingController ] prima del save");
        controlloreValutazione.saveValutazione(userRate);
    }
   
    /* Empty constructor
     */
    public RatingController() {
    }
    
    
    /* Questo metodo inizializza tutte le variabili con le medie di tutti e 
     * la votazione di un particolare utente
     */
   
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(); 
        
        idLocale = (Integer) httpSession.getAttribute("idLocale");
        
        try{
            idUtente = (Long) httpSession.getAttribute("idUtente");
           
        }catch(NullPointerException e){
            System.out.println("Utente non loggato!");
           
        }
        userRate = controlloreValutazione.findValutazioneUtente(idUtente, idLocale);
        if(userRate == null) userRate = new Valutazione();
        List<Valutazione> ll = controlloreValutazione.findValutazioni(idLocale);
        
        media = controlloreValutazione.mediaValutazioni(ll);
        pul = (double)media.getPulizia();
        qual = (double) media.getQualita();
        vel = (double) media.getVelocita();
        aff = (double) media.getAffollamento();
        quan = (double) media.getQuantita();
        cort = (double) media.getCortesia();
        System.out.println("[RatingController ] dopo calcolo media");
        // ricarico la lista dei locali
        try{
           ll =  controlloreValutazione.findValutazioni(idLocale);
        }catch(NullPointerException e){
            System.err.println("[RatingController.java] Non ci sono Valutazioni nel DB ");
        }       
        
    }

    //<editor-fold defaultstate="collapsed" desc="Getter  and Setter">
    public int getIdLocale() {
        return idLocale;
    }
    
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }
    
    public long getIdUtente() {
        return idUtente;
    }
    
    public void setIdUtente(long idUtente) {
        this.idUtente = idUtente;
    }
    
    public Valutazione getMedia() {
        return media;
    }
    
    public void setMedia(Valutazione media) {
        this.media = media;
    }
    
    public Valutazione getUserRate() {
        return userRate;
    }
    
    public void setUserRate(Valutazione userRate) {
        this.userRate = userRate;
    }
    
    public double getAffollamento() {
        return affollamento;
    }
    
    public double getAff() {
        return aff;
    }
    
    public void setAff(double aff) {
        this.aff = aff;
    }
    
    public double getCort() {
        return cort;
    }
    
    public void setCort(double cort) {
        this.cort = cort;
    }
    
    public double getPul() {
        return pul;
    }
    
    public void setPul(double pul) {
        this.pul = pul;
    }
    
    public double getQual() {
        return qual;
    }
    
    public void setQual(double qual) {
        this.qual = qual;
    }
    
    public double getQuan() {
        return quan;
    }
    
    public void setQuan(double quan) {
        this.quan = quan;
    }
    
    public double getVel() {
        return vel;
    }
    
    public void setVel(double vel) {
        this.vel = vel;
    }
    
    public void setAffollamento(double affollamento) {
        this.affollamento = affollamento;
    }
    
    public double getCortesia() {
        return cortesia;
    }
    
    public void setCortesia(double cortesia) {
        this.cortesia = cortesia;
    }
    
    public double getPulizia() {
        return pulizia;
    }
    
    public void setPulizia(double pulizia) {
        this.pulizia = pulizia;
    }
    
    public double getQualita() {
        return qualita;
    }
    
    public void setQualita(double qualita) {
        this.qualita = qualita;
    }
    
    public double getQuantita() {
        return quantita;
    }
    
    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }
    
    public double getVelocita() {
        return velocita;
    }
    
    public void setVelocita(double velocita) {
        this.velocita = velocita;
    }
    
    

    //</editor-fold>
}
