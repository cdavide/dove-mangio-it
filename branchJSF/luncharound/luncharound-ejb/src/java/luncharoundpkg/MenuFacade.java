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
 * @author lore0487
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> implements MenuFacadeLocal {

    @PersistenceContext(unitName = "luncharound-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }

    @Override
    public Menu findByLocale(int idLocale) {
        Menu ret;
        String selectQuery = "SELECT V FROM Menu V WHERE V.idLocale = ?1";
        Query searchById = em.createQuery(selectQuery);
        searchById.setParameter(1, idLocale);
        try{
            ret = (Menu) searchById.getSingleResult();
        }
        catch (Exception e){
            System.out.println("[Menu Facade] errore, nessun menu trovato per il locale");
            ret = null;
        }
        return ret;
    }
}
