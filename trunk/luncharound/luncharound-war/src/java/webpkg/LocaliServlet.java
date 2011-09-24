/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpkg;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.Locale;
import luncharoundpkg.LocaleFacadeLocal;
import utility.Maps;

/**
 *
 * @author lore0487
 */
@WebServlet(name = "LocaliServlet", urlPatterns = {"/LocaliServlet"})
public class LocaliServlet extends HttpServlet {
    @EJB
    private LocaleFacadeLocal localeFacade;
    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession();
        
        String azione = request.getParameter("azione");
        
        if(azione.equals("aggiungi_locale")){

            controlloreLocale.localeDaReq(request);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        else if(azione.equals("mostra_locale")){
            
            int idLocale = Integer.parseInt(request.getParameter("id"));
            
            String temp= visualizzaLocale(idLocale);
            request.setAttribute("contenuto",temp);
            request.getRequestDispatcher("locale.jsp").forward(request, response);
        
        }
        else if(azione.equals("mostra_tutti")){
            
            String temp= elencoLocali();
            request.setAttribute("contenuto",temp);
            request.getRequestDispatcher("ricerca.jsp").forward(request, response);
        }
        else if(azione.equals("ricerca_locali")){
            
            double lat=Double.parseDouble(request.getParameter("latitudine"));
            double lon=Double.parseDouble(request.getParameter("longitudine"));
            double dist=Double.parseDouble(request.getParameter("distanza"));

            String[] risultati =ricerca(lat,lon,dist);
            request.setAttribute("contenuto",risultati[0]);
            request.setAttribute("script",risultati[1]);
            //controllo se ha chiesto di memorizzare l'indirizzo e se è registrato
           
           // if(request.getParameter("salva").equals("true") && session.getAttribute("nome_utente")!=null){
           //     UtentiServlet.cambiaHome(request.getParameter("indirizzo"),session);
           // }
            request.getRequestDispatcher("risultati.jsp").forward(request, response);
        }
        else if(azione.equals("vai_a_ricerca")){
            //nella versione finale, forward a una pagina che include form_ricerca
            request.getRequestDispatcher("form_ricerca.jsp").forward(request, response);
        }
        else{
            request.setAttribute("errore","azione non valida!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        
    }
    
    //crea codice html da visualizzare nella jsp
    private String visualizzaLocale(int id){
        
        String ret="";
        Locale loc=localeFacade.find(id);
        ret+="<h2>"+loc.getNome()+"</h2><br>";
        ret+="<h4> di: "+loc.getProprietario()+"</h4><br><br>";
        ret+="partita iva:"+loc.getpIVA()+"<br>";
        ret+="Indirizzo:"+loc.getIndirizzo()+"<br>";
        ret+="latitudine e longitudine: "+loc.getLatitudine()+","+loc.getLongitudine();
        ret+="Dove si trova:<br>"+creaMappaStatica(loc.getIndirizzo());
        ret+="<hr>";
        ret+="<>MENU' DEL GIORNO:<br>"+controlloreLocale.mostraMenu(loc.getId());
        ret+="<hr>";
        ret+="COMBINAZIONI ED OFFERTE DEL LOCALE:<br>"+controlloreLocale.mostraCombo(id);
        
        return ret;
    }
            
    //crea codice html da visualizzare nella jsp
    private String elencoLocali(){
        
        String ret="";
        List<Locale> ll=localeFacade.findAll();
        
        ret+="Elenco locali presenti:<br>";
        
        for(Locale loc : ll){
            ret+=creaLink(loc.getId(),loc.getNome())+"<br>";
        }
        
        return ret;
    }
    
    
    //metodo per creare un collegamento alla pagina di descrizione del locale
    private String creaLink(int idLocale, String nome){
        return "<a href=\"LocaliServlet?azione=mostra_locale&id="+idLocale+"\">"+nome+"</a>";
    }
    private String creaLink2(int idLocale, String nome){
        return "<a href=\\\"LocaliServlet?azione=mostra_locale&id="+idLocale+"\\\">"+nome+"</a>";
    }
    
    //metodo per creare una mappa statica come immagine. da rendere parametrica?
    private String creaMappaStatica(String indirizzo){ 
          String zoom="15";
          String size="400x400";
          String ret="";
          ret+="<a href=\"http://maps.google.com?q="+indirizzo+"\" ><img src=\"http://maps.googleapis.com/maps/api/staticmap?zoom="+zoom;
          ret+="&size="+size;
          ret+="&markers=icon:http://chart.apis.google.com/chart?chst=d_map_pin_icon%26chld=restaurant%257CFF6600";
          ret+="%7C"+indirizzo+"&sensor=false\"></a>";
          return ret;
    }
    
    //restituisce due stringhe: la prima contiene la lista dei risultati in html
    //la seconda contiene lo script in js per generare la mappa dinamica
    private String[] ricerca(double lat, double lon, double dist){
            
        String[] ret= new String[2];
        String markers;
        List<Locale> ll = controlloreLocale.trovaLocali(lat, lon, dist);
        Locale loc;
        boolean valido;
        
        markers="";
        ret[0]="";
        for(int i=0;i<ll.size();i++){
            
            loc=ll.get(i);
            valido=controlloreLocale.menuValido(loc.getId());
            
            //creo la descrizione del locale per la lista
            ret[0]+="<br><hr><br>";
            ret[0]+="<h1>"+(i+1)+". "+creaLink(loc.getId(),loc.getNome())+"</h1><br>";
            ret[0]+="indirizzo: "+loc.getIndirizzo()+"<br>";
            ret[0]+= valido ? "Menu'aggiornato" : "Menu' non aggiornato";
            //ora creo il marker, verde se aggiornato, rosso altrimenti
            //con un numero corrispondente e un fumetto in caso di click
            //è necessario quindi un listener x l'evento 'click'
            markers+="var marker"+i+" = new google.maps.Marker({"
                    + "position: new google.maps.LatLng("+loc.getLatitudine()+","
                    + loc.getLongitudine()+"), map: map,"
                    + "icon: \"https://chart.googleapis.com/chart?chst=d_map_pin_letter_withshadow"
                    + "&chld="+(i+1)+"|";
            markers+= valido ? "00E304" : "F24657";
            markers+= "|000000\"});"
                    + " var infowindow"+i+" = new google.maps.InfoWindow"
                    + "({content: \" "+creaLink2(loc.getId(),loc.getNome())+"\""
                    + "});"
                    + "google.maps.event.addListener(marker"+i+", 'click', function() {"
                    + "infowindow"+i+".open(map,marker"+i+");});";
        }
        
        //ora creo lo script 'initialize()' per intero, sfruttando i markers appena creati
        ret[1]=generaScriptInitialize(markers,lat,lon,dist);
        
        return ret;
    }
    
    private String generaScriptInitialize(String markers,double lat, double lon,double dist){
        
        String script;
        String zoom;
        switch((int)dist){
            case 20: zoom="10";break;
            case 15: zoom="11";break;
            case 10: zoom="12";break;
            case 5: zoom="13";break;
            case 2: zoom="14";break;
            case 1: zoom="15";break;
            default: zoom="11";break;    
        }
        script="<script type=\"text/javascript\"> function initialize() {"
                + " var latlng = new google.maps.LatLng("+lat+","+lon+");";
        script+="var myOptions = {zoom: "+zoom+",";
        script+="center: latlng, mapTypeId: google.maps.MapTypeId.ROADMAP};";
        script+="var map = new google.maps.Map(document.getElementById(\"map_canvas\"),myOptions);";
        script+=markers+"}</script>";
        
        return script;
    }
    

    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
