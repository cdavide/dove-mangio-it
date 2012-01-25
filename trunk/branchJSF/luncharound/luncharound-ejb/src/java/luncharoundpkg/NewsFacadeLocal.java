/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/** facade local di news
 *
 * @author lore0487
 */
@Local
public interface NewsFacadeLocal {

    /**
     * 
     * @param news
     */
    void create(News news);

    /**
     * 
     * @param news
     */
    void edit(News news);

    /**
     * 
     * @param news
     */
    void remove(News news);

    /**
     * 
     * @param id
     * @return
     */
    News find(Object id);

    /**
     * 
     * @return
     */
    List<News> findAll();

    /**
     * 
     * @param range
     * @return
     */
    List<News> findRange(int[] range);

    /**
     * 
     * @return
     */
    int count();
    
    /** @deprecated 
     * 
     */
    public void deleteOld();
    
    /** restituisce una lista di news appartenenti ad un locale
     * 
     * @param idLocale l'id del locale
     * @return la lista di news
     */
    public List<News> findByLocale(int idLocale);
    
    /** @deprecated 
     * 
     * @param idLocali
     * @return
     */
    public List<News> findByLocali(List<Integer> idLocali);
    
    /**restituisce una lista con eventi del sistema ordinati e più vicini nel tempo 
     * 
     * @return la lista di news
     */
    public List<News> findNext();
    
}
