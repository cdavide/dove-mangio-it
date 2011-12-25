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

    protected EntityManager getEntityManager() {
        return em;
    }

    public NewsFacade() {
        super(News.class);
    }
    
    @Override
    public void deleteOld(){
        Date oggi = new Date();
        String deleteQuery = "DELETE V FROM News V WHERE V.dataInizio < ?1";
	Query deleteOldNews = em.createQuery(deleteQuery);
        deleteOldNews.setParameter(1, oggi);
        deleteOldNews.executeUpdate();
    }
    
    @Override
    public List<News> findByLocale(int idLocale){
        //deleteOld();
        String selectQuery = "SELECT DISTINCT OBJECT(V) FROM News V WHERE V.idLocale = ?1 ORDER BY V.dataInizio";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idLocale);
        return searchById.getResultList();
    }
    
    @Override
    public List<News> findByLocali(List<Integer> idLocali){
        //deleteOld();
        String selectQuery = "SELECT OBJECT(V) FROM News V ORDER BY V.dataInizio";
	Query searchById = em.createQuery(selectQuery);
	//searchById.setParameter(1, idLocali);
        return searchById.getResultList();
    }
    
    @Override
    public List<News> findNext(){
        //deleteOld();
        //La query mi deve restituire un subset degli eventi nel sistema ordinati e più vicini nel tempo
        String selectQuery = "SELECT OBJECT(V) FROM News V ORDER BY V.dataInizio";
	Query searchById = em.createQuery(selectQuery);
        return searchById.getResultList();
    }
    
}
