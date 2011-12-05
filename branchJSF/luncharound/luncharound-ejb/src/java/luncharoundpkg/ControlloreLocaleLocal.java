/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.Date;
import javax.ejb.Local;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Local
public interface ControlloreLocaleLocal {
    
    public void addPiatto(String nome, Categoria categoria, float prezzo, byte flags,int idLocale);
    public void remPiatto(long id);
    
    public void addPiattoCombo(String descr, float prezzo, int idLocale);
    public void remPiattoCombo(long id);
    
    public void addMenu(int idLocale, List<Piatto> listaPiatti, Date validita);
    public void editMenu(int idLocale, List<Piatto> listaPiatti, Date validita);
    
    public void addNews(int idLocale, Date dataInizio, String descr);
    public void addEvento(int idLocale, Date dataInizio, Date dataFine, String descr);
    
    public void addLocale(String nome, String indirizzo,long idUtente, double longitudine, double latitudine, String proprietario, String pIVA,String descrizione);
    
    public void addLocale(Locale locale);
    //metodo di utilità
    public Menu menuDiLocale(int idLocale);
    
    //metodi forse solo x test, necessario restituire le liste di oggetti?
    public String mostraMenu(int idLocale);
    public String mostraCombo(int  idLocale);
    
    public List<String> listaCat();
    
    public void localeDaReq(HttpServletRequest req);
    
    public String locali();
    
    public List<Locale> trovaLocali(double lat, double lon, double dist);
    public boolean menuValido(int idLocale);

    public Locale getLocali(long idUtente);
    
    public List<Locale> getTuttiLocali();

    public Locale findById(int idLocale);

    public java.util.List<luncharoundpkg.Menu> allMenu();

    public java.util.List<luncharoundpkg.Piatto> allPiatti();
    public List<Evento> getEventi(int idLocale);
    
}
