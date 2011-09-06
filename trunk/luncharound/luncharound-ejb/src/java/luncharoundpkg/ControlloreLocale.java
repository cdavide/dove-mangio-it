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

    //cerca il menù del locale, crea oggetto con i parametri e id ottenuto dalla ricerca
    //e lo inserisce nella lista. SUPPONGO VENGA SOSTITUITO QUELLO VECCHIO CON "edit"
    //non si tiene la storia dei menù. nella lista ce ne sarà uno solo appartenente
    //al locale, perciò devo eliminare quello precedente
    @Override
    public void editMenu(int idLocale, List<Piatto> listaPiatti, GregorianCalendar validita) {
        Menu me,temp;

        me=menuDiLocale(idLocale);
        if(me!=null){
            
            ordinaCat(listaPiatti);
            temp=new Menu(me.getId(),idLocale,listaPiatti,validita);
            menuFacade.edit(temp);
        }
        else{
            temp=new Menu(idLocale,listaPiatti,validita);
            menuFacade.create(temp);        
        }
        
        
    
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
    
    //restituisce il menù di un dato locale, NULL se non esiste
    public Menu menuDiLocale(int idLocale){
        
        List<Menu> lm;
        lm=menuFacade.findAll();

        for(int i=0; i<lm.size(); i++){
             
            if(idLocale == lm.get(i).getIdLocale()) return lm.get(i);
        }
        return null;
    }
    
    //metodo grezzo, adatto al test.Visualizza il menù del giorno
    //per un dato locale.Se non valido, la stringa ritornata inizierà 
    //con *
    @Override
    public String mostraMenu(int idLocale) {
        
        String ret="";
        Menu me;
        List<Piatto> lp;
        Piatto pi;
        GregorianCalendar now= new GregorianCalendar();
        int oldcatnum=-1; //utile per la separazione delle categorie
        
        me=menuDiLocale(idLocale);
        
        if(me==null) return "*** NESSUN MENU' TROVATO ***";
        
        //controllo che sia valido
        if(me.getValidita().compareTo(now)<=0) ret+="*** MENU' NON AGGIORNATO ***\n\n";
        //ottengo e scorro la lista dei piatti del menù    
        lp=me.getListaPiatti();
        for(int j=0; j<lp.size(); j++){
                    
            pi=lp.get(j);
            //inserisco un separatore tra le categorie di piatti(GIA'ORDINATI!)
            if(pi.getCategoria().ordinal()>oldcatnum){
                ret+="---- "+pi.getCategoria()+" ----\n";
                oldcatnum=pi.getCategoria().ordinal();
            }
                    
            ret+=pi.getNome()+"___€"+pi.getPrezzo()+"\n";        
        }
        return ret;
    }
    
    //ordinamento della lista di piatti secondo la propria categoria
    //necessario per una corretta visualizzazione del menù.
    //implementazione dell'insertion sort
    //verificare se con "set" si ha comportamento simile ad array
    private void ordinaCat(List<Piatto> lp){
        
        int j;
        Piatto temp;
    
        for(int i=1;i<lp.size();i++){
            
            j=i;
            temp=lp.get(i);
            
            while(j>0 && lp.get(j-1).getCategoria().ordinal()>temp.getCategoria().ordinal()){
                lp.set(j,lp.get(j-1));
                j--;
            }
            
            lp.set(j, temp);
        }
    }
    
    

    
    
}
