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
        //Impostare che cancelli le vecchie
        String deleteQuery = "DELETE V FROM Evento V WHERE V.dataFine = ?1";
	Query searchById = em.createQuery(deleteQuery);
        searchById.executeUpdate();
    }
    
    @Override
    public List<Evento> findByLocale(long idLocale ){
        String selectQuery = "SELECT V FROM Evento V ORDER BY V.dataInizio, V.dataFine WHERE V.idLocale = ?1";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idLocale);
        return searchById.getResultList();
    }
    
    @Override
    public List<Evento> findByLocali(List<Integer> idLocali ){
        String selectQuery = "SELECT V FROM Evento V ORDER BY V.dataInizio, V.dataFine WHERE V.idLocale IN ?1";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idLocali);
        return searchById.getResultList();
    }
    
}
