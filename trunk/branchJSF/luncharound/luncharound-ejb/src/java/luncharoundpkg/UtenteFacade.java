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
public class UtenteFacade extends AbstractFacade<Utente> implements UtenteFacadeLocal {
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
    public UtenteFacade() {
        super(Utente.class);
    }
    
    /**
     * 
     * @param mail
     * @return
     */
    @Override
    public Utente findByEmail(String mail){
        String selectQuery = "SELECT U FROM Utente U WHERE U.mail = ?1";
	Query searchByMail = em.createQuery(selectQuery);
	searchByMail.setParameter(1, mail);
        try{
            return (Utente)searchByMail.getSingleResult();
        }
        catch(Exception e){return null;}
    }
    
}
