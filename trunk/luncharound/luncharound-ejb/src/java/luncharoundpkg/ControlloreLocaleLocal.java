/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import java.util.List;
import java.util.GregorianCalendar;

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
    
    public String mostraMenu(int idLocale); 
    
    
}
