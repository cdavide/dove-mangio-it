/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 *@author Bovio Lorenzo Bronzino Francesco Concas Davide
 */
@Stateless
public class ValutazioneFacade extends AbstractFacade<Valutazione> implements ValutazioneFacadeLocal {
    @PersistenceContext(unitName = "luncharound-ejbPU")
    private EntityManager em;

    /**
     * 
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * 
     */
    public ValutazioneFacade() {
        super(Valutazione.class);
    }
    
    /**
     * 
     * @param idLocale
     * @return
     */
    @Override
    public List<Valutazione> findByLocale(long idLocale){
        String selectQuery = "SELECT A FROM Valutazione A WHERE A.idLocale = ?1";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idLocale);
        return searchById.getResultList();
    }
    /**
     * 
     * @param idUtente
     * @return
     */
    @Override
    public List<Valutazione> findByUtente(long idUtente){
        String selectQuery = "SELECT V FROM Valutazione V WHERE V.idUtente = ?1";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idUtente);
        return searchById.getResultList();
    }
    
    /**
     * 
     * @param idLocale
     * @param idUtente
     * @return
     */
    @Override
    public Valutazione findValutazioneLocFromUtente(long idLocale, long idUtente){
        Valutazione val;
        String selectQuery = "SELECT V FROM Valutazione V WHERE V.idUtente = ?1 AND V.idLocale = ?2";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idUtente);
        searchById.setParameter(2, idLocale);
        try {
            val = (Valutazione) searchById.getSingleResult();
        }catch (NoResultException e){
            val = null;
        }
        return val;
    }
    
    /**
     * 
     * @param idLocale
     * @param week
     * @return
     */
    @Override
    public List<Valutazione> findByLocaleWeek(long idLocale, int week){
        List<Valutazione> val;
        String selectQuery = "SELECT V FROM Valutazione V WHERE (V.idLocale = ?1) AND V.dataVal  BETWEEN ?2 AND ?3";
	Query searchById = em.createQuery(selectQuery);
        // id locale
	searchById.setParameter(1, idLocale);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.WEEK_OF_YEAR, week);
        calendar.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.MONDAY);
        int tmp = calendar.get(GregorianCalendar.DAY_OF_YEAR);
        Date date = calendar.getTime();
        //lowerbound
        searchById.setParameter(2, date);
        calendar.set(GregorianCalendar.DAY_OF_YEAR, tmp+6);
        Date date2 = calendar.getTime();
        //upperbound
        searchById.setParameter(3, date2);
        try {
            val = (List<Valutazione>) searchById.getResultList();
        }catch (NoResultException e){
            val = null;
        }
        return val;
    }
    
}

