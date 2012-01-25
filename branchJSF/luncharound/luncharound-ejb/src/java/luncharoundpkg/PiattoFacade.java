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
 * @author Bovio Lorenzo,Bronzino Francesco, Concas Davide
 */
@Stateless
public class PiattoFacade extends AbstractFacade<Piatto> implements PiattoFacadeLocal {
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
    public PiattoFacade() {
        super(Piatto.class);
    }
    
    /**Restituisce il menu di un locale
     * 
     * @param idLocale l'id del locale di cui si vuole ottenere il menu
     * @return il menu del locale
     */
    @Override
    public Menu findByLocale(int idLocale) {
        Menu ret;
        String selectQuery = "SELECT V FROM Menu V WHERE V.idLocale = ?1 ";
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
    

    /**Restituisce la lista di piatti di un locale
     * 
     * @param idLocale l'id del locale di cui si vogliono ricevere i piatti
     * @param cat la categoria dei piatti
     * @return la lista di tutti i piatti presenti nel locale che appartengono alla categoria cat
     */
    @Override
    public List<Piatto> getCategoriaLocale(int idLocale,Categoria cat) {
        List<Piatto> ret;
        String selectQuery = "SELECT V FROM Piatto V WHERE V.idLocale = ?1 AND V.categoria = ?2 ";
        Query search = em.createQuery(selectQuery);
        search.setParameter(1, idLocale);
        search.setParameter(2, cat);
        ret = search.getResultList();
        return ret;
    }
}