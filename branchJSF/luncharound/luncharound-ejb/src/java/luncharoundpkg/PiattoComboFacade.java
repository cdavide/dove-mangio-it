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
 * @author Lorenzo Bovio, Bronzino Francesco, Concas Davide
 */
@Stateless
public class PiattoComboFacade extends AbstractFacade<PiattoCombo> implements PiattoComboFacadeLocal {
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
    public PiattoComboFacade() {
        super(PiattoCombo.class);
    }
    
    /**Restituisce la lista di combinazioni offferte da un locale
     * 
     * @param idLocale l'id del locale
     * @return la lista delle combinazioni
     */
    @Override
    public List<PiattoCombo> findByLocale(int idLocale) {
        String selectQuery = "SELECT DISTINCT OBJECT(V) FROM PiattoCombo V WHERE V.idLocale = ?1 ";
        Query searchById = em.createQuery(selectQuery);
        searchById.setParameter(1, idLocale);
        return searchById.getResultList();
    }
}
