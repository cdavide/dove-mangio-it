/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** Provvisorio, forse controllore locale sta diventando troppo grasso
 * valutiamo se spezzare e fare un controllore piatto
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Stateless
public class ControllorePiatti implements ControllorePiattiLocal {
    @EJB
    private PiattoFacadeLocal piattoFacade;

    @Override
    public void addPersistency(List<Piatto> lista) {
        for (Piatto pp : lista){
            piattoFacade.create(pp);
        }
        
    }
    
    @Override
    public void editListaPiatti(List<Piatto> lista){
        for (Piatto pp : lista){
            piattoFacade.edit(pp);
        }
    }
    @Override
    public void addPiatto(Piatto p){
        piattoFacade.create(p);
    }
    
    
    // metodo che permette di avere un oggetto piatto parzialmente compilato
    
    @Override
    public Piatto createTemplatePiatto(Categoria cat, int idLocale ){
        Piatto ret = new Piatto();
        ret.setCategoria(cat);
        ret.setIdLocale(idLocale);
        ret.setCorrente(true);
        return ret;
    }
    
    @Override
    public void removePiatto(Piatto p){
        piattoFacade.remove(p);
    }
    
    
        @Override
    public Piatto findById(long idPiatto){
        return piattoFacade.find(idPiatto);
    }
    @Override
    public List<Piatto> getCategoriaLocale(int idLocale, Categoria cat){
        return piattoFacade.getCategoriaLocale(idLocale,cat);
    }
    
}
