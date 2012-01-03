/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpkg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreValutazioneLocal;
import luncharoundpkg.Valutazione;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

 @RequestScoped
public class Statistiche extends HttpServlet {
    @EJB
    private ControlloreValutazioneLocal controlloreValutazione;

	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("image/png");
                ServletOutputStream outputStream = response.getOutputStream();
	
                CategoryDataset dataset = createDataset(request,4);
                JFreeChart chart = createChart(dataset);        
		int width = 500;
		int height = 350;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
                outputStream.flush();
	}

               

        /* Metodo per creare il dataset, ci sono 6 serie di dati, una per ogni parametro
         */
        
        private CategoryDataset  createDataset(HttpServletRequest request, int weeks) {
            
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
            for( int i= weeks-1; i>= 0; i--){
                currentVal = mediaSettimana(request, currentweek-i);
                dataset.addValue(currentVal.getAffollamento(),affollamento,serie+i);
                dataset.addValue(currentVal.getVelocita(),velocita,serie+i);
                dataset.addValue(currentVal.getCortesia(),cortesia,serie+i);
                dataset.addValue(currentVal.getQualita(),qualita,serie+i);
                dataset.addValue(currentVal.getPulizia(),pulizia,serie+i);
                dataset.addValue(currentVal.getQuantita(),quantita,serie+i);
            }
            return dataset;
        }
        
        /**
         * Crea un grafico dato un certo dataset
         * @param dataset  the dataset.
         * lo devo cambiare per avere più dataset
         * nello stesso grafico, per ora faccio arrivare diversi dataset
         */
        
        private JFreeChart createChart(CategoryDataset dataset) {
            final JFreeChart chart = ChartFactory.createBarChart(
                "Valutazioni ultime 4 settimane",
                "Settimana", 
                "Voto medio settimana", 
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );
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
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
            return chart;    
        }
        
        /*Metodo pubblico per ottenere la media delle valutazioni dato il numero della settimana
         L'id del locale è un attributo nella sessione
         */
        
        public Valutazione mediaSettimana(HttpServletRequest request, int week){
            HttpSession session = request.getSession();
            int idlocale;
            try {
                idlocale = (Integer) session.getAttribute("idlocale");
            }catch(NullPointerException e){
                System.out.println("Errore attributo locale non inserito nella sessione");
                return null;
            }
            return mediaValutazioni(controlloreValutazione.valutazioniSettimana(idlocale, week));   
        }
       
        /*Metodo pubblico per calcolare la media di una lista di valutazioni
         */
        
        public Valutazione mediaValutazioni(List<Valutazione> val){
            if(val == null || val.isEmpty()){
                System.err.println("[Statistiche.java ]Non sono  state trovate valutazioni per il locale");
                return new Valutazione();
            }
            int cortesia =0;
            int velocita =0;
            int quantita =0;
            int pulizia =0;
            int affollamento=0;
            int qualita = 0;
            int numValutazioni =0;
            for(Valutazione v : val){
                numValutazioni++;
                affollamento += v.getAffollamento();
                cortesia += v.getCortesia();
                velocita += v.getVelocita();
                quantita += v.getQuantita();
                qualita += v.getQuantita();
                pulizia += v.getPulizia();        
            }

            Valutazione media = new Valutazione();
            media.setAffollamento( (affollamento / numValutazioni));
            media.setCortesia( (cortesia / numValutazioni));
            media.setVelocita( (velocita / numValutazioni));
            media.setQuantita( (quantita / numValutazioni));
            media.setQualita( (qualita / numValutazioni));
            media.setPulizia( (pulizia / numValutazioni));
            return media;
        }
}
