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
    
    /**
     * 
     * @param idLocale
     * @return
     */
    @Override
    public List<PiattoCombo> findByLocale(int idLocale) {
        String selectQuery = "SELECT DISTINCT OBJECT(V) FROM PiattoCombo V WHERE V.idLocale = ?1 ";
        Query searchById = em.createQuery(selectQuery);
        searchById.setParameter(1, idLocale);
        return searchById.getResultList();
    }
}
