/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import java.util.List;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lore0487
 */
@Local
public interface ControlloreLocaleLocal {
    
    public void addPiatto(String nome, Categoria categoria, float prezzo, byte flags,int idLocale);
    public void remPiatto(long id);
    
    public void addPiattoCombo(String descr, float prezzo, int idLocale);
    public void remPiattoCombo(long id);
    
    public void addMenu(int idLocale, List<Piatto> listaPiatti, GregorianCalendar validita);
    public void editMenu(int idLocale, List<Piatto> listaPiatti, GregorianCalendar validita);
    
    public void addNews(int idLocale, GregorianCalendar dataInizio, String descr);
    public void addEvento(int idLocale, GregorianCalendar dataInizio, GregorianCalendar dataFine, String descr);
    
    public void addLocale(String nome, String indirizzo, double longitudine, double latitudine, String proprietario, String pIVA);
    
    //metodo di utilità
    public Menu menuDiLocale(int idLocale);
    
    //metodi forse solo x test, necessario restituire le liste di oggetti?
    public String mostraMenu(int idLocale);
    public String mostraCombo(int idLocale);
    
    public List<String> listaCat();
    
    public void localeDaReq(HttpServletRequest req);
    
    public String locali();
    
    public List<Locale> trovaLocali(double lat, double lon, double dist);
    
    

    
    
}
