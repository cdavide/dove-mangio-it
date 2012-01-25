/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**Session Bean Stateless che serve per manipolare le combinazioni di piatti di un locale
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Stateless
public class ControllorePiattoCombo implements ControllorePiattoComboLocal {

    @EJB
    private PiattoComboFacadeLocal piattoComboFacade;

    /**Rende persistente una lista di piatti
     * 
     * @param lista La lista di piatti da rendere persistente
     */
    @Override
    public void addPersistency(List<PiattoCombo> lista) {
        for (PiattoCombo pp : lista){
            piattoComboFacade.create(pp);
        }
        
    }
    
    /**Modifica una lista di piatti nel db a partire da una lista
     * 
     * @param lista Lista dei piatti da modificare
     */
    @Override
    public void editListaPiatti(List<PiattoCombo> lista){
        for (PiattoCombo pp : lista){
            piattoComboFacade.edit(pp);
        }
    }
    /**Rende persistente un piatto
     * 
     * @param p Il piatto da rendere persistente
     */
    @Override
    public void addPiatto(PiattoCombo p){
        piattoComboFacade.create(p);
    }
    
    
    /**Ritorna un piatto vuoto con impostato l'id del locale
     * 
     * @param idLocale L'id del locale da impostare
     * @return Un piatto vuoto con impostato l'id del locale
     */
    @Override
    public PiattoCombo createTemplatePiatto(int idLocale ){
        PiattoCombo ret = new PiattoCombo();
        ret.setIdLocale(idLocale);
        return ret;
    }
    
    /**Rimuove un piatto dal db
     * 
     * @param p Il piatto da rimuovere
     */
    @Override
    public void removePiatto(PiattoCombo p){
        piattoComboFacade.remove(p);
    }
    
    
    /**Cerca un piatto nel db a partire da un id
     * 
     * @param idPiattoCombo il piatto da cercare
     * @return Il piatto se l'id appartiene al db, null altrimenti
     */
    @Override
    public PiattoCombo findById(long idPiattoCombo){
        return piattoComboFacade.find(idPiattoCombo);
    }
    
}
