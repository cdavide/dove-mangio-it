/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lore0487
 */
@Stateless
public class PiattoComboFacade extends AbstractFacade<PiattoCombo> implements PiattoComboFacadeLocal {
    @PersistenceContext(unitName = "luncharound-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PiattoComboFacade() {
        super(PiattoCombo.class);
    }
    
}
