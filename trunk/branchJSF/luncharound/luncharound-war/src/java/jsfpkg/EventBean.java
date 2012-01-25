/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;
import luncharoundpkg.Locale;
import luncharoundpkg.Evento;
import luncharoundpkg.ControlloreEventiLocal;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.ObjectMessage;
import javax.annotation.Resource;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "eventBean")
@RequestScoped
public class EventBean {
    @Resource(mappedName = "jms/codaEventi")
    private Topic eventiMsg;
    @Resource(mappedName = "jms/codaEventiFactory")
    private ConnectionFactory eventiMsgFactory;
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    @EJB
    private ControlloreUtenteLocal controlloreUtente;
    @EJB
    private ControlloreEventiLocal controlloreEventi;
    private List<Evento> eventi;
    private Evento selectedEvent;
    boolean gestore; // per c:if nella jsf
    boolean loggedIn;
    Locale locale;
    Date dataInizio;
    Date dataFine;
    String descr;
    String titolo;
    String path;
    
    /** Creates a new instance of EventBean */
    public EventBean() {
        
    }
    
    /**
     * 
     */
    @PostConstruct
    public void init(){
        int idLocale;
        long idUtente;
        try{
            System.out.println("[EventBean] Inizializzazione bean");
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession();
            path = request.getRequestURI();
            //Controllo se l'utente e' loggato
            try {
                loggedIn = (Boolean) httpSession.getAttribute("loggedIn");
            } catch (Exception e) {
                loggedIn = false;
            }
            //Recupero l'idUtente se loggato
            try {
                idUtente = (Long) httpSession.getAttribute("idUtente");

            } catch (NullPointerException e) {
                idUtente = -1;
            }
            //Controllo se sto visualizzando un locale
            if(path.matches("/luncharound-war/faces/eventiPage.xhtml")){
                eventi = controlloreEventi.getEventi();
            }
            else{
                try {
                    idLocale = (Integer) httpSession.getAttribute("idLocale");
                } catch (NullPointerException e) {
                    idLocale = -1;
                }
               //Caso in cui sto visualizzando un locale
                if (idLocale>=0) {
                    locale = controlloreLocale.findById(idLocale);
                    if (idUtente == locale.getIdUtente()) {
                        gestore = true;
                    }
                    else {
                        gestore = false;
                    }
                    eventi = controlloreLocale.getEventi(idLocale);
                }
                else{
                    eventi = controlloreEventi.getEventi();
                }
            }
        }
        catch (Exception e){
            eventi = null;
        }
    }
    
    private Message createJMSMessageForjmsEventiMsg(Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        
        ObjectMessage tm = session.createObjectMessage();
        tm.setObject((String) messageData);
        return tm;
    }

    private void sendJMSMessageToEventiMsg(Object messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = eventiMsgFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(eventiMsg);
            messageProducer.send(createJMSMessageForjmsEventiMsg(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    System.out.println("Cannot close session");
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    /**
     * 
     */
    public void addEvento(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        int idLocale = (Integer) httpSession.getAttribute("idLocale");
        controlloreLocale.addEvento(idLocale, dataInizio, dataFine, titolo, descr);
        String msg = "<evento>";
        msg = msg.concat("<dataInizio>"+dataInizio.toString()+"</dataInizio>");
        msg = msg.concat("<dataFine>"+dataFine.toString()+"</dataFine>");
        msg = msg.concat("<titolo>"+titolo.toString()+"</titolo>");
        msg = msg.concat("<descr>"+descr.toString()+"</descr>");
        Locale loc = controlloreLocale.findById(idLocale);
        msg = msg.concat("<locale>"+loc.getNome()+"</locale></evento>");
        try{
            sendJMSMessageToEventiMsg((Object) msg);
        }
        catch (JMSException e){
            System.err.println("Impossibile mandare il nuovo evento alla jms");
        }
    }
    
    /**
     * 
     * @return
     */
    public String visualizzaLocale() {
        System.out.println("visualizza Locale");
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
    
    /**
     * 
     * @return
     */
    public Evento getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * 
     * @param selectedEvent
     */
    public void setSelectedEvent(Evento selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    /**
     * 
     * @return
     */
    public List<Evento> getEventi() {
        return eventi;
    }

    /**
     * 
     * @param eventi
     */
    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

    /**
     * 
     * @return
     */
    public boolean isGestore() {
        return gestore;
    }

    /**
     * 
     * @param gestore
     */
    public void setGestore(boolean gestore) {
        this.gestore = gestore;
    }

    /**
     * 
     * @return
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * 
     * @return
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * 
     * @param loggedIn
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * 
     * @return
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * 
     * @param dataFine
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * 
     * @return
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * 
     * @param dataInizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * 
     * @return
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 
     * @param descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * 
     * @return
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * 
     * @param titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * 
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    
}
