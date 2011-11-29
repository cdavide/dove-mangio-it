/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpkg;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import webpkg.TwitterClient.*;
import twitter.twitteroauth.twitterresponse.*;
import com.sun.jersey.api.client.UniformInterfaceException;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControlloreUtenteLocal;
import luncharoundpkg.Utente;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@WebServlet(name = "TwitterServlet", urlPatterns = {"/TwitterServlet"})
public class TwitterServlet extends HttpServlet {
    
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
        PrintWriter out = response.getWriter();
        String formAction = request.getParameter("metodo");
        HttpSession httpSession = request.getSession();
        try {
            if (formAction.equals("login")) {
                TwitterClient twitter = new TwitterClient("xml");
                twitter.initOAuth(request, response);
                twitter.makeOAuthRequestUnique();
                try {
                    Statuses statuses = twitter.getUserTimeline(Statuses.class, null, null, null, "10");
                    out.println(statuses.getStatus().toString());
                    StatusType st = statuses.getStatus().get(0);
                    UserType user = st.getUser();
                    String foto = user.getProfileImageUrl();
                    String nome = user.getName();
                    String home = user.getLocation();
                    String username = user.getScreenName();
                    String email = username+"@twitter.com";
                    twitter.close();
                    Utente persona=controlloreUtente.trovaDaEmail(email);
            
                    if(persona!=null){ //utente esistente
                
                        if(persona.getTipo()!=2){//ma appartenente al sito o altro social network
                            httpSession.setAttribute("errore", "Impossibile utilizzare l'account Facebook, email già utilizzata da un altro utente");
                            httpSession.setAttribute("newLogin",true);
                            request.getRequestDispatcher("faces/home.xhtml").forward(request, response);    
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
                        System.err.println("L'utente non ha associato nessun locale personale");
                    }
                    httpSession.setAttribute("newLogin",true);
                    request.getRequestDispatcher("faces/home.xhtml").forward(request, response); 
                
                } catch (UniformInterfaceException ex) {
                    httpSession.setAttribute("oauth_token",null);
                    twitter.initOAuth(request, response);
                }
                //else {
                //httpSession.setAttribute("errore","impossibile effettuare il login da facebook");
                //httpSession.setAttribute("newLogin",true);
                //request.getRequestDispatcher("faces/home.xhtml").forward(request, response);
                //}
            }
            else if(formAction.equals("logged")){
                TwitterClient twitter = new TwitterClient("xml");
                twitter.initOAuth(request, response);
                twitter.makeOAuthRequestUnique();
                try {
                    Statuses statuses = twitter.getUserTimeline(Statuses.class, null, null, null, "10");
                    out.println(statuses.getStatus().toString());
                    StatusType st = statuses.getStatus().get(0);
                    UserType user = st.getUser();
                    String foto = user.getProfileImageUrl();
                    String nome = user.getName();
                    String home = user.getLocation();
                    String username = user.getScreenName();
                    String email = username+"@twitter.com";
                    twitter.close();
                    Utente persona=controlloreUtente.trovaDaEmail(email);
            
                    if(persona!=null){ //utente esistente
                
                        if(persona.getTipo()!=2){//ma appartenente al sito o altro social network
                            httpSession.setAttribute("errore", "Impossibile utilizzare l'account Facebook, email già utilizzata da un altro utente");
                            httpSession.setAttribute("newLogin",true);
                            request.getRequestDispatcher("faces/home.xhtml").forward(request, response);    
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
                        System.err.println("L'utente non ha associato nessun locale personale");
                    }
                    httpSession.setAttribute("newLogin",true);
                    request.getRequestDispatcher("faces/home.xhtml").forward(request, response); 
                
                } catch (UniformInterfaceException ex) {
                    httpSession.setAttribute("oauth_token",null);
                    twitter.initOAuth(request, response);
                }
            }
        } finally {            
            out.close();
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
