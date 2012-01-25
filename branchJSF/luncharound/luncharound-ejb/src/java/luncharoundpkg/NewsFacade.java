/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;
import java.util.Date;

/**
 *
 * @author lore0487
 */
@Stateless
public class NewsFacade extends AbstractFacade<News> implements NewsFacadeLocal {
    @PersistenceContext(unitName = "luncharound-ejbPU")
    private EntityManager em;

    /**
     * 
     * @return
     */
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * 
     */
    public NewsFacade() {
        super(News.class);
    }
    
    /**
     * 
     */
    @Override
    public void deleteOld(){
        Date oggi = new Date();
        String deleteQuery = "DELETE V FROM News V WHERE V.dataInizio < ?1";
	Query deleteOldNews = em.createQuery(deleteQuery);
        deleteOldNews.setParameter(1, oggi);
        deleteOldNews.executeUpdate();
    }
    
    /**
     * 
     * @param idLocale
     * @return
     */
    @Override
    public List<News> findByLocale(int idLocale){
        //deleteOld();
        String selectQuery = "SELECT DISTINCT OBJECT(V) FROM News V WHERE V.idLocale = ?1 AND V.dataInizio >= ?2 ORDER BY V.dataInizio";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idLocale);
        Date data = new Date();
        searchById.setParameter(2, data);
        return searchById.getResultList();
    }
    
    /**
     * 
     * @param idLocali
     * @return
     */
    @Override
    public List<News> findByLocali(List<Integer> idLocali){
        //deleteOld();
        String selectQuery = "SELECT OBJECT(V) FROM News V WHERE V.dataInizio >= ?1 ORDER BY V.dataInizio";
	Query searchById = em.createQuery(selectQuery);
	//searchById.setParameter(1, idLocali);
        Date data = new Date();
        searchById.setParameter(1, data);
        return searchById.getResultList();
    }
    
    /**
     * 
     * @return
     */
    @Override
    public List<News> findNext(){
        //deleteOld();
        //La query mi deve restituire un subset degli eventi nel sistema ordinati e più vicini nel tempo
        String selectQuery = "SELECT OBJECT(V) FROM News V WHERE V.dataInizio >= ?1 ORDER BY V.dataInizio";
	Query searchById = em.createQuery(selectQuery);
        Date data = new Date();
        searchById.setParameter(1, data);
        return searchById.getResultList();
    }
    
}
