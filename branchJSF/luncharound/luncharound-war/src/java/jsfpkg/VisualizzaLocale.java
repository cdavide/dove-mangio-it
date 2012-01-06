/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControllorePiattiLocal;
import luncharoundpkg.ControlloreValutazioneLocal;
import luncharoundpkg.Locale;
import luncharoundpkg.Menu;
import luncharoundpkg.Piatto;
import luncharoundpkg.PiattoCombo;
import luncharoundpkg.Valutazione;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "visualizzaLocale")
@RequestScoped
public class VisualizzaLocale implements Serializable {

    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    @EJB
    private ControllorePiattiLocal controllorePiatto;
    @EJB
    private ControlloreValutazioneLocal controlloreValutazione;
    Locale locale;
    private StreamedContent chart;
    String mappa;
    String facebook;
    List<Piatto> menu;
    List<PiattoCombo> offerte;
    String valutazioni;
    boolean gestore; // per c:if nella jsf
    boolean loggedIn;
    private String TwitLocale;
    




    /** Creates a new instance of LocaliBean */
    public VisualizzaLocale() {
        System.err.println("[VisualizzaLocale.java] inizializzazione bean");
        // non fa nulla
    }

    //questo viene chiamato dopo il costruttore, 
    // dopo che il container ha fatto la injection degli ejb
    @PostConstruct
    public void init() {
        int idLocale;
        long idUtente;
        System.out.println("[VisualizzaLocale] Inizializzazione bean");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();

        try {
            idLocale = (Integer) httpSession.getAttribute("idLocale");
        } catch (NullPointerException e) {
            idLocale = (Integer) httpSession.getAttribute("mioLocale");
        }
        if ((Integer)idLocale == null) System.out.println("[Visualizza locale] idLocale è nullo !!!");
        locale = controlloreLocale.findById(idLocale);
        try {
            loggedIn = (Boolean) httpSession.getAttribute("loggedIn");
        } catch (Exception e) {
            loggedIn = false;
        }
        try {
            idUtente = (Long) httpSession.getAttribute("idUtente");
            if (idUtente == locale.getIdUtente()) {
                gestore = true;
            }
        } catch (NullPointerException e) {
            gestore = false;
        }
        
        mappa = "http://maps.google.com?q=" + locale.getIndirizzo();
        //creaMappaStatica(locale.getIndirizzo());

        System.out.println("mappa: " + mappa);
        facebook = creaFbDialog2(locale);
        try{
        menu = controlloreLocale.menuDiLocale(idLocale).getListaPiatti();
        }
        catch(Exception e){
            menu = new ArrayList<Piatto>();
        }
        offerte = controlloreLocale.getMenuCombo(idLocale);

        //se è il proprietario deve visualizzare statistiche e altre cose
        // non deve poter valutare
        // statistiche
        try {
            CategoryDataset dataset = createDataset(request, 4);
            JFreeChart mychart = createChart(dataset);
            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, mychart, 375, 300);
            chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            System.err.println("[VisualizzaLocale.java]: cannot create chart exception");
        }

        
        TwitLocale = "<a href='https://twitter.com/share'"
                + "class='twitter-share-button' "
                + "data-text='I love to eat at "
                + locale.getNome() + " ' "
                + "data-count='none' "
                + "data-via='luncharound'>"
                + "Tweet"
                + "</a>"
                + "<script type='text/javascript' "
                + "src='//platform.twitter.com/widgets.js'>"
                + "</script>";
    }

    private CategoryDataset createDataset(HttpServletRequest request, int weeks) {

        // Colonne
        String serie = "Settimana ";

        // righe
        final String cortesia = "Cortesia";
        final String velocita = "Velocità";
        final String qualita = "Qualità";
        final String quantita = "Quantità";
        final String affollamento = "Affollamento";
        final String pulizia = "pulizia";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // crea report ultime 4 settimane
        final XYSeries series = new XYSeries("Serie di dati");
        GregorianCalendar calendar = new GregorianCalendar();
        int currentweek = calendar.get(GregorianCalendar.WEEK_OF_YEAR);

        Valutazione currentVal;
        for (int i = weeks - 1; i >= 0; i--) {
            currentVal = mediaSettimana(request, currentweek - i);
            dataset.addValue(currentVal.getAffollamento(), affollamento, serie + i);
            dataset.addValue(currentVal.getVelocita(), velocita, serie + i);
            dataset.addValue(currentVal.getCortesia(), cortesia, serie + i);
            dataset.addValue(currentVal.getQualita(), qualita, serie + i);
            dataset.addValue(currentVal.getPulizia(), pulizia, serie + i);
            dataset.addValue(currentVal.getQuantita(), quantita, serie + i);
        }
        return dataset;
    }

    public Valutazione mediaSettimana(HttpServletRequest request, int week) {
        HttpSession session = request.getSession();
        int idLocale;
        try {
            idLocale = (Integer) session.getAttribute("idLocale");
        } catch (NullPointerException e) {
            System.out.println("Errore attributo locale non inserito nella sessione");
            return null;
        }
        return mediaValutazioni(controlloreValutazione.valutazioniSettimana(idLocale, week));
    }

    private String creaFbDialog2(Locale loc) {

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

    /* DEPRECATED
     * private String creaFbDialog(Locale loc) {

        String dialog;
        String img_prova = "http://www.ahfourthgrade.net/resources/Charles-C--Ebbets-Lunch-Atop-A-Skyscraper-1932-8619.jpg";
        String url_prova = "http://localhost:8080/luncharound-war/";
        //url di prov       
        dialog = "";
        dialog += "https://www.facebook.com/dialog/feed?";
        dialog += "app_id=241460472572920&";
        dialog += "link=" + url_prova + "&";
        //sostituire con loc.getfoto!!
        dialog += "picture=" + img_prova + "&";
        dialog += "name=Io oggi vado a mangiare qui grazie a LunchAround!&";
        dialog += "caption=" + loc.getNome() + "&";
        dialog += "description=Si trova in " + loc.getIndirizzo() + ". "
                + "   Clicca qui per visualizzare il loro menu' di oggi!&";
        dialog += "redirect_uri=http://www.facebook.com&output=embed";

        return dialog;
    }*/

    private JFreeChart createChart(CategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart(
                "Valutazioni ultime 4 settimane",
                "Settimana",
                "Voto medio settimana",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;
    }

    /*Metodo pubblico per calcolare la media di una lista di valutazioni
     */
    public Valutazione mediaValutazioni(List<Valutazione> val) {
        if (val == null || val.isEmpty()) {
            System.err.println("[Statistiche.java ]Non sono  state trovate valutazioni per il locale");
            return new Valutazione();
        }
        int cortesia = 0;
        int velocita = 0;
        int quantita = 0;
        int pulizia = 0;
        int affollamento = 0;
        int qualita = 0;
        int numValutazioni = 0;
        for (Valutazione v : val) {
            numValutazioni++;
            affollamento += v.getAffollamento();
            cortesia += v.getCortesia();
            velocita += v.getVelocita();
            quantita += v.getQuantita();
            qualita += v.getQuantita();
            pulizia += v.getPulizia();
        }

        Valutazione media = new Valutazione();
        media.setAffollamento((byte) (affollamento / numValutazioni));
        media.setCortesia((byte) (cortesia / numValutazioni));
        media.setVelocita((byte) (velocita / numValutazioni));
        media.setQuantita((byte) (quantita / numValutazioni));
        media.setQualita((byte) (qualita / numValutazioni));
        media.setPulizia((byte) (pulizia / numValutazioni));
        return media;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Locale getLocale() {
        return locale;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public StreamedContent getChart() {
        return chart;
    }

    public boolean isGestore() {
        return gestore;
    }

    public void setGestore(boolean gestore) {
        this.gestore = gestore;
    }

    public void setChart(StreamedContent chart) {
        this.chart = chart;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getMappa() {
        return mappa;
    }

        public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
    
    
    /**
     * Get the value of TwitLocale
     *
     * @return the value of TwitLocale
     */
    public String getTwitLocale() {
        return TwitLocale;
    }

    /**
     * Set the value of TwitLocale
     *
     * @param TwitLocale new value of TwitLocale
     */
    public void setTwitLocale(String TwitLocale) {
        this.TwitLocale = TwitLocale;
    }
    
    public List<Piatto> getMenu() {
        return menu;
    }

    public void setMenu(List<Piatto> menu) {
        this.menu = menu;
    }

    public List<PiattoCombo> getOfferte() {
        return offerte;
    }

    public void setOfferte(List<PiattoCombo> offerte) {
        this.offerte = offerte;
    }

    public String getValutazioni() {
        return valutazioni;
    }
    
    public void setMappa(String url) {
        this.mappa = url;
    }

    public void setValutazioni(String valutazioni) {
        this.valutazioni = valutazioni;
    }
    //</editor-fold>
}
