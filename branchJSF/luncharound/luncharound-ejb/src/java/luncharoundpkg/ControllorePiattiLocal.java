/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.Local;

/**Interfaccia controllore piatti (local)
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Local
public interface ControllorePiattiLocal {

    /**Rende persistenti una lista di piatti
     * 
     * @param lista la lista di piatti da rendere persistenti
     */
    public void addPersistency(List<Piatto> lista);
    
    /**Modifica una lista di piatti rendendo persistenti le modifiche effettuate su ciascuno
     * 
     * @param lista la lista modificata da rendere persistente
     */
    public void editListaPiatti(List<Piatto> lista);
    /**Aggiunge un piatto al DB rendendolo persistente
     * 
     * @param p il piatto da agguingere
     */

    public void addPiatto(Piatto p);
    
    
    /** metodo che permette di avere un oggetto piatto parzialmente compilato
     * 
     * @param cat la categoria a cui deve appartenere il template del piatto
     * @param idLocale l'id del locale a cui appartiene il piatto
     * @return il template  del piatto
     */

    public Piatto createTemplatePiatto(Categoria cat, int idLocale );
    
    /**Rimuove un piatto dal DB se presente
     * 
     * @param p il piatto da rimuovere
     */

    public void removePiatto(Piatto p);
    
    
    /**Permette di trovare un piatto dato il su id
     * 
     * @param idPiatto l'id del piatto da ricercare
     * @return il piatto
     */

    public Piatto findById(long idPiatto);
    /**Permette di ottenere la lista di tutti i piatti che appartengono ad una categoria
     * 
     * @param idLocale l'id del locale 
     * @param cat la categoria 
     * @return la lista di tutti i piatti del locale che appartengono alla categoria cat
     */

    public List<Piatto> getCategoriaLocale(int idLocale, Categoria cat);
    
}