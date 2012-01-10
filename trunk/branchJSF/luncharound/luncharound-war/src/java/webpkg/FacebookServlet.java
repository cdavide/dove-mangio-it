/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import luncharoundpkg.Utente;
import utility.JSONObject;
import utility.JSONReader;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
@WebServlet(name = "FacebookServlet", urlPatterns = {"/FacebookServlet"})
public class FacebookServlet extends HttpServlet {
    
    @EJB
    private ControlloreUtenteLocal controlloreUtente;
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
        HttpSession httpSession = request.getSession();
        PrintWriter out = response.getWriter();
        
        String nome="";
        String email="";
        String foto="";
        String home="";
        
        try {

            String codice=(String)request.getParameter("code");
            
            if(codice!=null){
                
                String url="https://graph.facebook.com/oauth/access_token?"
                    + "client_id=241460472572920&"
                    + "redirect_uri=http://localhost:8080/luncharound-war/FacebookServlet&"
                    + "client_secret=cd1a87fcb55ceeb01a7805ca55b76a7e&"
                    + "code="+codice;
                
                String accessToken= readFromUrl(url);
                String url2="https://graph.facebook.com/me?"+accessToken;
                
                try{//provo a leggere dal graph di FB i dati dell'utente
                    JSONObject dati= JSONReader.readJsonFromUrl(url2);
                    
                    nome=dati.getString("name");
                    email=dati.getString("email");
                    foto="https://graph.facebook.com/"+dati.getString("id")+"/picture";
                    home=dati.getJSONObject("location").getString("name");
                    
                }
                catch(Exception e){
                    
                    if(e.getMessage().equalsIgnoreCase("JSONObject[\"location\"] not found.")){
                    //se tutto ok ma l'utente non ha una location,non genero errori ma metto campo vuoto
                        home="";
                    }
                    else{
                    //problemi di lettura oggetto json
                        httpSession.setAttribute("errore","impossibile effettuare il login da facebook");
                        httpSession.setAttribute("newLogin",true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }//fine lettura dati json
                 
                Utente persona=controlloreUtente.trovaDaEmail(email);
            
                if(persona!=null){ //utente esistente
                
                    if(persona.getTipo()!=2){//ma appartenente al sito o altro social network
                    
                        httpSession.setAttribute("errore", "Impossibile utilizzare l'account Facebook, email già utilizzata da un altro utente");
                        httpSession.setAttribute("newLogin",true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);    
                    }
                }
                else{//non esiste nel database, lo memorizzo
                    controlloreUtente.addUtenteEsterno(nome,email,home,foto,2);
                    //riottengo i dati per memorizzarli nella sessione(non riscrivo tutte le assegnazioni)
                    persona=controlloreUtente.trovaDaEmail(email);
                }
            
                httpSession.setAttribute("nome_utente", persona.getUsername());
                httpSession.setAttribute("idUtente", persona.getId());
                httpSession.setAttribute("eventi", persona.isEventi());
                httpSession.setAttribute("news",persona.isNews());
                httpSession.setAttribute("home", persona.getHome());
                httpSession.setAttribute("foto", persona.getFoto());            
                httpSession.setAttribute("tipo", persona.getTipo());
                try{
                     httpSession.setAttribute("localipersonali",controlloreLocale.getLocali(persona.getId()));
                }
                catch(NullPointerException e){
                    System.err.println("[fb]L'utente non ha associato nessun locale personale");
                }
                httpSession.setAttribute("newLogin",true);
                request.getRequestDispatcher("index.jsp").forward(request, response);    

            }
            else{//errore nell'interazione iniziale (non ottengo un token per il graph)
                httpSession.setAttribute("errore","impossibile effettuare il login da facebook");
                httpSession.setAttribute("newLogin",true);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
       
        } finally {            
            out.close();
        }
    }
    
   String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  String readFromUrl(String url) throws IOException{
    InputStream is = new URL(url).openStream();
    try {
        
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      return readAll(rd);
      
    } finally {
      is.close();
    }
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
