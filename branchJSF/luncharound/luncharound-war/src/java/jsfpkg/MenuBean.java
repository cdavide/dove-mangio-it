/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**Managed bean che contiene metodi e proprietà che servono per il menu laterale
 * nell'applicazione
 * 
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name="menuBean")
@SessionScoped
public class MenuBean {

   
	private MenuModel model;
        private boolean loggedIn;
        private boolean gestore;
        private String username;

        /**Costruttore
         */
        public MenuBean() {
		model = new DefaultMenuModel();
		
		//First submenu
		Submenu submenu = new Submenu();
		submenu.setLabel("Dynamic Submenu 1");
		
		MenuItem item = new MenuItem();
		item.setValue("Dynamic Menuitem 1.1");
		item.setUrl("#");
		submenu.getChildren().add(item);
		
		model.addSubmenu(submenu);
		
		//Second submenu
		submenu = new Submenu();
		submenu.setLabel("Dynamic Submenu 2");
		
		item = new MenuItem();
		item.setValue("Dynamic Menuitem 2.1");
		item.setUrl("#");
		submenu.getChildren().add(item);
		
		item = new MenuItem();
		item.setValue("Dynamic Menuitem 2.2");
		item.setUrl("#");
		submenu.getChildren().add(item);
		
		model.addSubmenu(submenu);
	}
	
        
        /**Post costruttore, invocato dopo il costruttore in modo automatico 
         * serve per compilare i campi dell'oggetto
         */
        @PostConstruct
        public void init(){
            RequestContext context = RequestContext.getCurrentInstance();
            FacesMessage msg = null;
            loggedIn = false;
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession httpSession = request.getSession();
            int idLocale;
            long idUtente;
            try{
            idLocale = (Integer) httpSession.getAttribute("idLocale");
            gestore = true;
            }catch(Exception e){
                gestore = false;
            }
            try{
                idUtente = (Long) httpSession.getAttribute("idUtente");
                loggedIn = true;
            }catch(Exception e){
                loggedIn = false;
            }
            try{
                username = (String) httpSession.getAttribute("nome_utente");
            }catch (Exception e){
                System.out.println("[MenuBean]: nessun nome utente");           
            }
        }
        
        
        //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
        /**
         *
         * @return
         */
        public MenuModel getModel() {
            return model;
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
        public String getUsername() {
            return username;
        }
        
        /**
         *
         * @param username
         */
        public void setUsername(String username) {
            this.username = username;
        }
        //</editor-fold>
  
        
}
			