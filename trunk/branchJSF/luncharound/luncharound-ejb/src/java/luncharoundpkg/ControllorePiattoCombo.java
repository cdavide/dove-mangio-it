/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Stateless
public class ControllorePiattoCombo implements ControllorePiattoComboLocal {

    @EJB
    private PiattoComboFacadeLocal piattoComboFacade;

    /**
     * 
     * @param lista
     */
    @Override
    public void addPersistency(List<PiattoCombo> lista) {
        for (PiattoCombo pp : lista){
            piattoComboFacade.create(pp);
        }
        
    }
    
    /**
     * 
     * @param lista
     */
    @Override
    public void editListaPiatti(List<PiattoCombo> lista){
        for (PiattoCombo pp : lista){
            piattoComboFacade.edit(pp);
        }
    }
    /**
     * 
     * @param p
     */
    @Override
    public void addPiatto(PiattoCombo p){
        piattoComboFacade.create(p);
    }
    
    
    /**
     * 
     * @param idLocale
     * @return
     */
    @Override
    public PiattoCombo createTemplatePiatto(int idLocale ){
        PiattoCombo ret = new PiattoCombo();
        ret.setIdLocale(idLocale);
        return ret;
    }
    
    /**
     * 
     * @param p
     */
    @Override
    public void removePiatto(PiattoCombo p){
        piattoComboFacade.remove(p);
    }
    
    
    /**
     * 
     * @param idPiattoCombo
     * @return
     */
    @Override
    public PiattoCombo findById(long idPiattoCombo){
        return piattoComboFacade.find(idPiattoCombo);
    }
    
}
