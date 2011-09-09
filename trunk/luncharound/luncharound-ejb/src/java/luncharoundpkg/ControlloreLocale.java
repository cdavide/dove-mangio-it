/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
/**
 *
 * @author lore0487
 */
@Stateless
public class ControlloreLocale implements ControlloreLocaleLocal {
    
    private PiattoFacadeLocal piattoFacade;
    private PiattoComboFacadeLocal piattoComboFacade;
    private MenuFacadeLocal menuFacade;
    private LocaleFacadeLocal localeFacade;
    private EventoFacadeLocal eventoFacade;
    private NewsFacadeLocal newsFacade;
    

    @Override
    public void addPiattoCombo(String descr, float prezzo, int idLocale) {
        
        PiattoCombo pc = new PiattoCombo();
        pc.setDescr(descr);
        pc.setPrezzo(prezzo);
        pc.setIdLocale(idLocale);
        piattoComboFacade.create(pc);
    }

    @Override
    public void addEvento(int idLocale, GregorianCalendar dataInizio, GregorianCalendar dataFine, String descr) {

        Evento ev = new Evento();
        ev.setDataFine(dataFine);
        ev.setDataInizio(dataInizio);
        ev.setDescr(descr);
        ev.setIdLocale(idLocale);
        
        eventoFacade.create(ev);
    }

    @Override
    public void addMenu(int idLocale, List<Piatto> listaPiatti, GregorianCalendar validita) {
        Menu me = new Menu();
        me.setIdLocale(idLocale);
        me.setListaPiatti(listaPiatti);
        me.setValidita(validita);
        menuFacade.create(me);
    }

    @Override
    public void addNews(int idLocale, GregorianCalendar dataInizio, String descr) {
        News ne = new News();
        ne.setDataInizio(dataInizio);
        ne.setDescr(descr);
        ne.setIdLocale(idLocale);
        newsFacade.create(ne);
    }

    //AGGIUSTARE QUESTIONE VALIDITA'!!!
    @Override
    public void addPiatto(String nome, Categoria categoria, float prezzo, byte flags, int idLocale) {
        
        Piatto pi = new Piatto();
        pi.setCategoria(categoria);
        pi.setCorrente(true);
        pi.setFlags(flags);
        pi.setNome(nome);
        pi.setPrezzo(prezzo);
        pi.setIdLocale(idLocale);
        piattoFacade.create(pi);
    }
    
    public void addLocale(String nome, String indirizzo, float longitudine, float latitudine, String proprietario, String pIVA){
        
        Locale lo= new Locale();
        lo.setIndirizzo(indirizzo);
        lo.setLatitudine(latitudine);
        lo.setLongitudine(longitudine);
        lo.setNome(nome);
        lo.setProprietario(proprietario);
        lo.setpIVA(pIVA);
        localeFacade.create(lo);
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
            temp=new Menu();
            temp.setId(me.getId());
            temp.setIdLocale(idLocale);
            temp.setListaPiatti(listaPiatti);
            temp.setValidita(validita);
            menuFacade.edit(temp);
        }
        else{
            temp=new Menu();
            temp.setIdLocale(idLocale);
            temp.setListaPiatti(listaPiatti);
            temp.setValidita(validita);
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
    
    //dato un locale, restituisce una stringa con tutte le sue combo e i prezzi
    public String mostraCombo(int idLocale){
        String ret="";
        List<PiattoCombo> pc =piattoComboFacade.findAll();
        
        for(int i=0; i<pc.size();i++){
            if(pc.get(i).getIdLocale()==idLocale){
               ret+=pc.get(i).getDescr()+"_______€"+pc.get(i).getPrezzo()+"\n"; 
            }
        }
        
        if(ret.equals("")) return "*** nessuna combonazione trovata ***";
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
    
    public List<String> listaCat(){
        
        List<String> ret= new ArrayList<String>();
        
        for(Categoria cat : Categoria.values()){
            ret.add(cat.toString());
        }
        return ret;
    
    }
    
    public void localeDaReq(HttpServletRequest req){
        
        Locale loc= new Locale();
        HashMap map = new HashMap();
        Enumeration names = req.getParameterNames();
        while(names.hasMoreElements()){
            String name= (String)names.nextElement();
            map.put(name,req.getParameterValues(name));
        }
        System.err.println("prima del populate");
        
        try {
            BeanUtils.populate(loc, map) ;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControlloreLocale.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ControlloreLocale.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.err.println("dopo il populate"
                + " populate");
        localeFacade.create(loc);
    }
    
    public String locali(){
        String ret="";
        List<Locale> ll=localeFacade.findAll();
        
        for(int i =0; i<ll.size();i++) ret+=ll.get(i).getNome()+"<br>";
        return ret;
    }

    
    

    
    
}
