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
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.Locale;
import luncharoundpkg.LocaleFacadeLocal;

/**
 *
 * @author lore0487
 */
@WebServlet(name = "localiServlet", urlPatterns = {"/localiServlet"})
public class localiServlet extends HttpServlet {
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
        
        String azione = request.getParameter("azione");
        
        if(azione.equals("mostra_locale")){
            
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
        
    }
    
    //crea codice html da visualizzare nella jsp
    private String visualizzaLocale(int id){
        
        String ret="";
        Locale loc=localeFacade.find(id);
        ret+="<h2>"+loc.getNome()+"</h2><br>";
        ret+="<h4> di: "+loc.getProprietario()+"</h4><br><br>";
        ret+="partita iva:"+loc.getpIVA()+"<br>";
        ret+="Indirizzo:"+loc.getIndirizzo()+"<br>";
        ret+="Dove si trova:<br>"+creaMappa(loc.getIndirizzo());
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
        return "<a href=\"mainServlet?azione=mostra_locale&id="+idLocale+"\">"+nome+"</a>";
    }
    
    //metodo per creare una mappa statica come immagine. da rendere parametrica?
    private String creaMappa(String indirizzo){ 
          String zoom="15";
          String size="400x400";
          String ret="";
          ret+="<img src=\"http://maps.googleapis.com/maps/api/staticmap?zoom="+zoom;
          ret+="&size="+size;
          ret+="&markers=icon:http://chart.apis.google.com/chart?chst=d_map_pin_icon%26chld=restaurant%257CFF6600";
          ret+="%7C"+indirizzo+"&sensor=false\">";
          return ret;
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
