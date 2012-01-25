/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/**
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
    
    /**
     * 
     */
    public void deleteOld();
    
    /**
     * 
     * @param idLocale
     * @return
     */
    public List<News> findByLocale(int idLocale);
    
    /**
     * 
     * @param idLocali
     * @return
     */
    public List<News> findByLocali(List<Integer> idLocali);
    
    /**
     * 
     * @return
     */
    public List<News> findNext();
    
}
