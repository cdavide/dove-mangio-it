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

/** facade dell'oggetto locale
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Stateless
public class LocaleFacade extends AbstractFacade<Locale> implements LocaleFacadeLocal {
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
    public LocaleFacade() {
        super(Locale.class);
    }

    /** dato un id utente restituisce il locale posseduto dall'utente
     * 
     * @param idUtente l'id del proprietario del locale
     * @return il locale posseduto dall'utente
     */
    @Override
    public Locale findByUtente(long idUtente ){
        Locale ll;
        String selectQuery = "SELECT V FROM Locale V WHERE V.idUtente = ?1";
	Query searchById = em.createQuery(selectQuery);
	searchById.setParameter(1, idUtente);
        try{
            ll = (Locale) searchById.getSingleResult();
        }
        catch (Exception e){
            System.out.println("[LocaleFacade.java]: nessun locale associato all'utente!");
            ll = null;
        }
        return ll;
    }
    
}
