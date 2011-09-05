/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author lore0487
 */
@Stateless
public class ControlloreLocale implements ControlloreLocaleLocal {
    
    private PiattoFacadeLocal piattoFacade;
    private PiattoComboFacadeLocal piattoComboFacade;
    private MenuFacadeLocal menuFacade;
    private EventoFacadeLocal eventoFacade;
    private NewsFacadeLocal newsFacade;
    

    @Override
    public void addPiattoCombo(String descr, float prezzo, int idLocale) {
        
        PiattoCombo pc = new PiattoCombo(descr,prezzo,idLocale);
        piattoComboFacade.create(pc);
    }

    @Override
    public void addEvento(int idLocale, GregorianCalendar dataInizio, GregorianCalendar dataFine, String descr) {

        Evento ev = new Evento(idLocale,dataInizio,dataFine,descr);
        eventoFacade.create(ev);
    }

    @Override
    public void addMenu(int idLocale, List<Piatto> listaPiatti, GregorianCalendar validita) {
        Menu me = new Menu(idLocale,listaPiatti,validita);
        menuFacade.create(me);
    }

    @Override
    public void addNews(int idLocale, GregorianCalendar dataInizio, String descr) {
        News ne = new News(idLocale,dataInizio,descr);
        newsFacade.create(ne);
    }

    //AGGIUSTARE QUESTIONE VALIDITA'!!!
    @Override
    public void addPiatto(String nome, Categoria categoria, float prezzo, byte flags, int idLocale) {
        Piatto pi = new Piatto(nome,categoria,prezzo,true,flags,idLocale);
        piattoFacade.create(pi);
    }

    @Override
    public void editMenu(int idLocale, List<Piatto> listaPiatti, GregorianCalendar validita) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
    //ASSICURARSI CHE LA RIMOZIONE SIA CORRETTA
    @Override
    public void remPiattoCombo(long id) {

        PiattoCombo pc = piattoComboFacade.find(id);
        piattoComboFacade.remove(pc);
    }

    @Override
    public void remPiatto(long id) {
        Piatto pi = piattoFacade.find(id);
        piattoFacade.remove(pi);
    }
    
    //metodo grezzo, adatto al test.Visualizza il menù del giorno
    //per un dato locale.Se non valido, la stringa ritornata inizierà 
    //con *
    @Override
    public String mostraMenu(int idLocale) {
        
        String ret="";
        List<Menu> lm;
        List<Piatto> lp;
        Piatto pi;
        GregorianCalendar now= new GregorianCalendar();
        int oldcatnum=-1; //utile per la suparazione delle categorie
        
        lm=menuFacade.findAll();
        //scorro tutti i menù
        for(int i=0; i<lm.size(); i++){ 
            //se trovo quello del locale che cerco
            if(idLocale == lm.get(i).getIdLocale()){
                //controllo che sia valido
                if(lm.get(i).getValidita().compareTo(now)<=0) ret+="*** MENU' NON AGGIORNATO ***\n\n";
                //ottengo e scorro la lista dei piatti del menù    
                lp=lm.get(i).getListaPiatti();
                for(int j=0; j<lp.size(); j++){
                    
                    pi=lp.get(j);
                    //inserisco un separatore tra le categorie di piatti(GIA'ORDINATI!)
                    if(pi.getCategoria().ordinal()>oldcatnum){
                        ret+="---- "+pi.getCategoria()+" ----\n";
                        oldcatnum=pi.getCategoria().ordinal();
                    }
                    
                    ret+=pi.getNome()+"___€"+pi.getPrezzo()+"\n";
                    
                }
                break;
            }
        }
        if(ret.equals(""))return "*** NESSUN MENU' TROVATO ***";
        return ret;
    }
    
    

    
    
}
