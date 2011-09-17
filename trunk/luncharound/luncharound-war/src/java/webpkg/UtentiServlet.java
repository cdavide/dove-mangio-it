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
import luncharoundpkg.ControlloreUtenteLocal;
import luncharoundpkg.Utente;
import luncharoundpkg.UtenteFacadeLocal;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
@WebServlet(name = "UtentiServlet", urlPatterns = {"/UtentiServlet"})
public class UtentiServlet extends HttpServlet {
    @EJB
    private UtenteFacadeLocal utenteFacade;
    @EJB
    private ControlloreUtenteLocal controlloreUtente;
    
    

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
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
       
        String azione = request.getParameter("azione");
        
        if(azione.equals("registra_utente")){
            
            request.getRequestDispatcher("registrazioneUtente.jsp").forward(request, response);
            
        }
        else if(azione.equals("aggiungi_utente")){
            
            try{
                controlloreUtente.addUtenteDaReq(request);
            }
            catch(Exception e){
                request.setAttribute("errore", "Registrazione non effettuata. Email gia' in uso");
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        else if(azione.equals("login")){
        
            Utente persona=controlloreUtente.verificaPassword(request.getParameter("mail"), request.getParameter("password"));
        
            if(persona==null){
                request.setAttribute("errore", "Login fallito! credenziali non corrette!");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            else{
                session.setAttribute("nome_utente", persona.getUsername());
                session.setAttribute("eventi", persona.isEventi());
                session.setAttribute("news",persona.isNews());
                session.setAttribute("home", persona.getHome());
                
                request.getRequestDispatcher("index.jsp").forward(request, response);

            }
        }
        else if(azione.equals("logout")){
            
            session.removeAttribute("nome_utente");
            session.removeAttribute("eventi");
            session.removeAttribute("news");
            session.removeAttribute("home");
            
            request.getRequestDispatcher("index.jsp").forward(request, response);

        
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
