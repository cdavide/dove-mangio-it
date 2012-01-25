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
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreValutazioneLocal;
import luncharoundpkg.Valutazione;

/**
 * 
 * @author dave
 */
@ManagedBean(name = "ratingController")
@ViewScoped
public class RatingController {

    @EJB
    private ControlloreValutazioneLocal controlloreValutazione;
    Valutazione media;
    Valutazione userRate;
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

    /* Empty constructor
     */
    /**
     * 
     */
    public RatingController() {
        // inizializzo le variabili
        media = new Valutazione();
        userRate = new Valutazione();
        pulizia = 0;
        qualita = 0;
        velocita = 0;
        affollamento = 0;
        quantita = 0;
        cortesia = 0;
        // variabili per la media
        pul = 0;
        qual = 0;
        vel = 0;
        aff = 0;
        quan = 0;
        cort = 0;
    }

    /* Questo metodo inizializza tutte le variabili con le medie di tutti e 
     * la votazione di un particolare utente
     */
    /**
     * 
     */
    @PostConstruct
    public void init() {
        System.out.println("[Rating Controller.java] Init");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();

        idLocale = (Integer) httpSession.getAttribute("idLocale");

        try {
            idUtente = (Long) httpSession.getAttribute("idUtente");

        } catch (NullPointerException e) {
            System.out.println("Utente non loggato!");

        }
        userRate = controlloreValutazione.findValutazioneUtente(idUtente, idLocale);
        if (userRate == null) {
            userRate = new Valutazione();
        }
        List<Valutazione> ll = controlloreValutazione.findValutazioni(idLocale);

        media = controlloreValutazione.mediaValutazioni(ll);

        pul = (double) media.getPulizia();
        qual = (double) media.getQualita();
        vel = (double) media.getVelocita();
        aff = (double) media.getAffollamento();
        quan = (double) media.getQuantita();
        cort = (double) media.getCortesia();
        System.out.println("[RatingController ] dopo calcolo media");
        System.err.printf("%d, %d,%d,%d,%d,%d,",media.getPulizia(),media.getQualita(),media.getVelocita(),media.getAffollamento(),media.getQuantita(), media.getCortesia());
        // ricarico la lista dei locali
        
        try {
            ll = controlloreValutazione.findValutazioni(idLocale);
        } catch (NullPointerException e) {
            System.err.println("[RatingController.java] Non ci sono Valutazioni nel DB ");
        }

    }

    /* Questo metodo deve sottomettere la votazione e ricaricare le medie.
     */
    /**
     * 
     * @return
     */
    public String submitRating() {
        if (userRate == null) {
            System.err.println("Nullo!!!");
        }
        userRate.setDataVal(new Date());
        userRate.setIdLocale(idLocale);
        userRate.setIdUtente(idUtente);
        userRate.setAffollamento((int) affollamento);
        userRate.setCortesia((int) cortesia);
        userRate.setPulizia((int) pulizia);
        userRate.setQualita((int) qualita);
        userRate.setQuantita((int) quantita);
        userRate.setVelocita((int) velocita);
        System.err.println("[RatingController ] prima del save");
        controlloreValutazione.saveValutazione(userRate);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Valutazione registrata con successo");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        init();
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter  and Setter">
    /**
     * 
     * @return
     */
    public int getIdLocale() {
        return idLocale;
    }

    /**
     * 
     * @param idLocale
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    /**
     * 
     * @return
     */
    public long getIdUtente() {
        return idUtente;
    }

    /**
     * 
     * @param idUtente
     */
    public void setIdUtente(long idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * 
     * @return
     */
    public Valutazione getMedia() {
        return media;
    }

    /**
     * 
     * @param media
     */
    public void setMedia(Valutazione media) {
        this.media = media;
    }

    /**
     * 
     * @return
     */
    public Valutazione getUserRate() {
        return userRate;
    }

    /**
     * 
     * @param userRate
     */
    public void setUserRate(Valutazione userRate) {
        this.userRate = userRate;
    }

    /**
     * 
     * @return
     */
    public double getAffollamento() {
        return affollamento;
    }

    /**
     * 
     * @return
     */
    public double getAff() {
        return aff;
    }

    /**
     * 
     * @param aff
     */
    public void setAff(double aff) {
        this.aff = aff;
    }

    /**
     * 
     * @return
     */
    public double getCort() {
        return cort;
    }

    /**
     * 
     * @param cort
     */
    public void setCort(double cort) {
        this.cort = cort;
    }

    /**
     * 
     * @return
     */
    public double getPul() {
        return pul;
    }

    /**
     * 
     * @param pul
     */
    public void setPul(double pul) {
        this.pul = pul;
    }

    /**
     * 
     * @return
     */
    public double getQual() {
        return qual;
    }

    /**
     * 
     * @param qual
     */
    public void setQual(double qual) {
        this.qual = qual;
    }

    /**
     * 
     * @return
     */
    public double getQuan() {
        return quan;
    }

    /**
     * 
     * @param quan
     */
    public void setQuan(double quan) {
        this.quan = quan;
    }

    /**
     * 
     * @return
     */
    public double getVel() {
        return vel;
    }

    /**
     * 
     * @param vel
     */
    public void setVel(double vel) {
        this.vel = vel;
    }

    /**
     * 
     * @param affollamento
     */
    public void setAffollamento(double affollamento) {
        this.affollamento = affollamento;
    }

    /**
     * 
     * @return
     */
    public double getCortesia() {
        return cortesia;
    }

    /**
     * 
     * @param cortesia
     */
    public void setCortesia(double cortesia) {
        this.cortesia = cortesia;
    }

    /**
     * 
     * @return
     */
    public double getPulizia() {
        return pulizia;
    }

    /**
     * 
     * @param pulizia
     */
    public void setPulizia(double pulizia) {
        this.pulizia = pulizia;
    }

    /**
     * 
     * @return
     */
    public double getQualita() {
        return qualita;
    }

    /**
     * 
     * @param qualita
     */
    public void setQualita(double qualita) {
        this.qualita = qualita;
    }

    /**
     * 
     * @return
     */
    public double getQuantita() {
        return quantita;
    }

    /**
     * 
     * @param quantita
     */
    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    /**
     * 
     * @return
     */
    public double getVelocita() {
        return velocita;
    }

    /**
     * 
     * @param velocita
     */
    public void setVelocita(double velocita) {
        this.velocita = velocita;
    }
    //</editor-fold>
}
