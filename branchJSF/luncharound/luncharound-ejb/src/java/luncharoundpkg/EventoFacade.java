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
public class EventoFacade extends AbstractFacade<Evento> implements EventoFacadeLocal {
    @PersistenceContext(unitName = "luncharound-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoFacade() {
        super(Evento.class);
    }
    
    @Override
    public void deleteOld(){
        Date oggi = new Date();
        String deleteQuery = "DELETE V FROM Evento V WHERE V.dataFine < ?1";
	Query deleteOldEvents = em.createQuery(deleteQuery);
        deleteOldEvents.setParameter(1, oggi);
        deleteOldEvents.executeUpdate();
    }
    
    @Override
    public List<Evento> findByLocale(int idLocale){
        //deleteOld();
        String selectQuery = "SELECT DISTINCT OBJECT(V) FROM Evento V WHERE V.idLocale = ?1 ORDER BY V.dataInizio, V.dataFine";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idLocale);
        return searchById.getResultList();
    }
    
    @Override
    public List<Evento> findByLocali(List<Integer> idLocali){
        //deleteOld();
        //String selectQuery = "SELECT V FROM Evento V WHERE V.idLocale IN ?1 ORDER BY V.dataInizio, V.dataFine";
        String selectQuery = "SELECT OBJECT(V) FROM Evento V ORDER BY V.dataInizio, V.dataFine";
	Query searchById = em.createQuery(selectQuery);
	//searchById.setParameter(1, idLocali);
        return searchById.getResultList();
    }
    
    @Override
    public List<Evento> findNext(){
        //deleteOld();
        //La query mi deve restituire un subset degli eventi nel sistema ordinati e più vicini nel tempo
        String selectQuery = "SELECT OBJECT(V) FROM Evento V ORDER BY V.dataInizio, V.dataFine";
	Query searchById = em.createQuery(selectQuery);
        return searchById.getResultList();
    }
}
