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

    void create(News news);

    void edit(News news);

    void remove(News news);

    News find(Object id);

    List<News> findAll();

    List<News> findRange(int[] range);

    int count();
    
    public void deleteOld();
    
    public List<News> findByLocale(int idLocale);
    
    public List<News> findByLocali(List<Integer> idLocali);
    
    public List<News> findNext();
    
}
