/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.Local;
import java.util.List;

/**Interfaccia Controllore Piatto Combo
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Local
public interface ControllorePiattoComboLocal {
    /**Rende persistente una lista di piatti
     * 
     * @param lista La lista di piatti da rendere persistente
     */
    public void addPersistency(List<PiattoCombo> lista);
    /**Modifica una lista di piatti nel db a partire da una lista
     * 
     * @param lista Lista dei piatti da modificare
     */
    public void editListaPiatti(List<PiattoCombo> lista);
    /**Rende persistente un piatto
     * 
     * @param p Il piatto da rendere persistente
     */
    public void addPiatto(PiattoCombo p);
    /**Ritorna un piatto vuoto con impostato l'id del locale
     * 
     * @param idLocale L'id del locale da impostare
     * @return Un piatto vuoto con impostato l'id del locale
     */
    public PiattoCombo createTemplatePiatto(int idLocale );
    /**Rimuove un piatto dal db
     * 
     * @param p Il piatto da rimuovere
     */
    public void removePiatto(PiattoCombo p);
    /**Cerca un piatto nel db a partire da un id
     * 
     * @param idPiattoCombo il piatto da cercare
     * @return Il piatto se l'id appartiene al db, null altrimenti
     */
    public PiattoCombo findById(long idPiattoCombo);
}
