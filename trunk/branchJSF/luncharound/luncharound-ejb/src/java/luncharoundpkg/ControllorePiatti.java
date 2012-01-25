/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**Classe controllore piatti 
 * Contiene tutti i metodi per interagire con gli EJB Piatto
 * Il session EJB è stateless
 * 
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Stateless
public class ControllorePiatti implements ControllorePiattiLocal {
    @EJB
    private PiattoFacadeLocal piattoFacade;

    /**Rende persistenti una lista di piatti
     * 
     * @param lista la lista di piatti da rendere persistenti
     */
    @Override
    public void addPersistency(List<Piatto> lista) {
        for (Piatto pp : lista){
            piattoFacade.create(pp);
        }
    }
    
    /**Modifica una lista di piatti rendendo persistenti le modifiche effettuate su ciascuno
     * 
     * @param lista la lista modificata da rendere persistente
     */
    @Override
    public void editListaPiatti(List<Piatto> lista){
        for (Piatto pp : lista){
            piattoFacade.edit(pp);
        }
    }
    /**Aggiunge un piatto al DB rendendolo persistente
     * 
     * @param p il piatto da agguingere
     */
    @Override
    public void addPiatto(Piatto p){
        piattoFacade.create(p);
    }
    
    
    /** metodo che permette di avere un oggetto piatto parzialmente compilato
     * 
     * @param cat la categoria a cui deve appartenere il template del piatto
     * @param idLocale l'id del locale a cui appartiene il piatto
     * @return il template  del piatto
     */
    @Override
    public Piatto createTemplatePiatto(Categoria cat, int idLocale ){
        Piatto ret = new Piatto();
        ret.setCategoria(cat);
        ret.setIdLocale(idLocale);
        ret.setCorrente(true);
        return ret;
    }
    
    /**Rimuove un piatto dal DB se presente
     * 
     * @param p il piatto da rimuovere
     */
    @Override
    public void removePiatto(Piatto p){
        piattoFacade.remove(p);
    }
    
    
    /**Permette di trovare un piatto dato il su id
     * 
     * @param idPiatto l'id del piatto da ricercare
     * @return il piatto
     */
    @Override
    public Piatto findById(long idPiatto){
        return piattoFacade.find(idPiatto);
    }
    /**Permette di ottenere la lista di tutti i piatti che appartengono ad una categoria
     * 
     * @param idLocale l'id del locale 
     * @param cat la categoria 
     * @return la lista di tutti i piatti del locale che appartengono alla categoria cat
     */
    @Override
    public List<Piatto> getCategoriaLocale(int idLocale, Categoria cat){
        return piattoFacade.getCategoriaLocale(idLocale,cat);
    }
    
}
