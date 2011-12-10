/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */


@Stateless
public class ControlloreLocale implements ControlloreLocaleLocal {
    @EJB
    private ValutazioneFacadeLocal valutazioneFacade;
    @EJB
    private PiattoFacadeLocal piattoFacade;
    @EJB
    private PiattoComboFacadeLocal piattoComboFacade;
    @EJB
    private MenuFacadeLocal menuFacade;
    @EJB
    private LocaleFacadeLocal localeFacade;
    @EJB
    private EventoFacadeLocal eventoFacade;
    @EJB
    private NewsFacadeLocal newsFacade;
    
    private EntityManager em;
    
    private static int NUMFLAGS=6;
        
    @Override
    public void addPiattoCombo(String descr, float prezzo, int idLocale) {
        
        PiattoCombo pc = new PiattoCombo();
        pc.setDescr(descr);
        pc.setPrezzo(prezzo);
        pc.setIdLocale(idLocale);
        piattoComboFacade.create(pc);
    }

    @Override
    public void addEvento(int idLocale, Date dataInizio, Date dataFine, String titolo, String descr) {

        Evento ev = new Evento();
        ev.setDataFine(dataFine);
        ev.setDataInizio(dataInizio);
        ev.setDescr(descr);
        ev.setIdLocale(idLocale);
        ev.setTitolo(titolo);
        
        eventoFacade.create(ev);
    }

    @Override
    public void addMenu(int idLocale, List<Piatto> listaPiatti, Date validita) {
        Menu me = new Menu();
        me.setIdLocale(idLocale);
        me.setListaPiatti(listaPiatti);
        me.setValidita(validita);
        menuFacade.create(me);
    }

    @Override
    public void addNews(int idLocale, Date dataInizio, String descr) {
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
    
    @Override
    public void addLocale(String nome, String indirizzo,long idUtente, double longitudine, double latitudine, String proprietario, String pIVA, String descrizione){
        
        Locale lo= new Locale();
        lo.setDescrizione(descrizione);
        lo.setIndirizzo(indirizzo);
        lo.setLatitudine(latitudine);
        lo.setIdUtente(idUtente);
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
    public void editMenu(int idLocale, List<Piatto> listaPiatti, Date validita) {
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
   
    public List<Menu> allMenu(){
        return menuFacade.findAll();
    }
    
    public List<Piatto> allPiatti(){
     return piattoFacade.findAll();   
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
    @Override
    public Menu menuDiLocale(int idLocale){
        return menuFacade.findByLocale(idLocale);
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
        if(me.getValidita().compareTo(now.getTime())<=0) ret+="*** MENU' NON AGGIORNATO ***\n\n";
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
        
        if(ret.equals("")) return "*** nessuna combinazione trovata ***";
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
    
    @Override
    public void localeDaReq(HttpServletRequest req){
        Locale loc= new Locale();
        Util.riempi(req,loc);
        localeFacade.create(loc);
    }
    
    @Override
    public String locali(){
        String ret="";
        List<Locale> ll=localeFacade.findAll();
        System.err.println("ci sono: "+ll.size()+" elementi");
        for(int i = 0; i<ll.size();i++) ret+=ll.get(i).getNome()+"<br>";
        return ret;
    }
    
    public List<Locale> trovaLocali(double lat, double lon, double dist){

        List<Locale> ll=localeFacade.findAll();
        List<Locale> ret=new ArrayList<Locale>();

        for(Locale loc : ll){
            if(vicino(loc,lat,lon,dist)){
                ret.add(loc);
            }
        }
        
        return ret;        
    }
    
    private boolean vicino(Locale loc,double lat,double lon,double dist){
        int R=6371;
        double a,c,d;
        double dlat = Math.toRadians(lat - loc.getLatitudine());
        double dlon = Math.toRadians(lon - loc.getLongitudine());
        lat = Math.toRadians(lat);
        lon = Math.toRadians(lon);
        a=Math.sin(dlat/2)*Math.sin(dlat/2)+
                Math.sin(dlon/2)*Math.sin(dlon/2)*Math.cos(lat)*Math.cos(lon);
        c=2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        d=(double)R*c;
        
        System.out.println("la distanza è: "+d);
        
        if(d<=dist) return true;
        else return false;
    }
    
    //lista booleani in ordine corretto:CARNE,PESCE,VEGETARIANO,VEGANO,CELIACO,ALCOLICO
    //FLAGS contrario: 0.0.ALCOLICO.CELIACO.VEGANO.VEGETARIANO.PESCE.CARNE
    private byte generaFlags(boolean[] bools){
        byte flags=0;
        
        for(int i=0;i<NUMFLAGS;i++){
            if(bools[i]) flags= (byte) (flags|(1 << i));
        }
        return flags;
    }
    
    
    private boolean[] generaBool(byte flags){
        boolean[] bools=new boolean[NUMFLAGS];
        
        for(int i=0;i<NUMFLAGS;i++){
            if((flags & (1<<i))==(byte)Math.pow(2,i)) bools[i]=true;
            else bools[i]=false;
        }
        return bools;
    }
    
    public boolean menuValido(int idLocale){
    
        List<Menu> lm= menuFacade.findAll();
        GregorianCalendar now= new GregorianCalendar();
        for(Menu menu : lm){
            if(menu.getIdLocale()==idLocale){
                if(menu.getValidita().compareTo(now.getTime())<=0) return false;
                else return true;
            }
        }
        return false;
    }
    
        
    @Override
    public Locale getLocali(long idUtente){
        return localeFacade.findByUtente(idUtente);
    }
    

    @Override
    public List<Locale> getTuttiLocali(){
        List<Locale> loc = localeFacade.findAll();
        System.err.println("[ControlloreLocale.java] la lista ha dimensioni : "+loc.size());
        return loc;
    }

    @Override
    public void addLocale(Locale locale){
       localeFacade.create(locale);
    }
    @Override
    public Locale findById(int idLocale){
        return localeFacade.find(idLocale);
    }

    @Override
    public List<Evento> getEventi(int idLocale){
        return eventoFacade.findByLocale(idLocale);
    }
}
