/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 *@author Bovio Lorenzo Bronzino Francesco Concas Davide
 */
@Stateless
public class ValutazioneFacade extends AbstractFacade<Valutazione> implements ValutazioneFacadeLocal {
    @PersistenceContext(unitName = "luncharound-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValutazioneFacade() {
        super(Valutazione.class);
    }
    
    @Override
    public List<Valutazione> findByLocale(long idLocale){
        String selectQuery = "SELECT A FROM Valutazione A WHERE A.idLocale = ?1";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idLocale);
        return searchById.getResultList();
    }
    @Override
    public List<Valutazione> findByUtente(long idUtente){
        String selectQuery = "SELECT A FROM Valutazione V WHERE V.idUtente = ?1";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idUtente);
        return searchById.getResultList();
    }
    
    @Override
    public Valutazione findValutazioneLocFromUtente(long idLocale, long idUtente){
         String selectQuery = "SELECT A FROM Valutazione V WHERE V.idUtente = ?1 AND V.idLocale = ?2";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idUtente);
        searchById.setParameter(1, idLocale);
        return (Valutazione) searchById.getSingleResult();
    }
    
    
}
