/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/** Session EJB stateless che permette di effettuare operazioni su un locale
 * modificare un locale, ricercare locali data una posizione geografica
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
    private static int NUMFLAGS = 6;

    /** Aggiunge una combinazione di piatti ad un locale
     * 
     * @param descr la descrizione della combinazione
     * @param prezzo Il prezzo della combinazione dei piatti
     * @param idLocale L'id del locale a cui aggiungere la combinazione
     */
    @Override
    public void addPiattoCombo(String descr, float prezzo, int idLocale) {

        PiattoCombo pc = new PiattoCombo();
        pc.setDescr(descr);
        pc.setPrezzo(prezzo);
        pc.setIdLocale(idLocale);
        piattoComboFacade.create(pc);
    }

    /** Aggiunge un evento al locale 
     * 
     * @param idLocale L'id del locale a cui l'evento fa riferimento
     * @param dataInizio La data di inizio dell'evento
     * @param dataFine La data di fine dell'evento
     * @param titolo Il titolo dell'evento da visualizzare nella homepage
     * @param descr La descrizione nel dettaglio dell'evento
     */
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

    /** Aggiunge un oggetto menu ad un locale
     * 
     * @param idLocale L'id del locale a cui aggiungere il menu
     * @param listaPiatti La lista dei piatti presenti nel menu
     * @param validita La data entro la quale il menu è da ritenersi valido
     */
    @Override
    public void addMenu(int idLocale, List<Piatto> listaPiatti, Date validita) {
        Menu me = new Menu();
        me.setIdLocale(idLocale);
        me.setListaPiatti(listaPiatti);
        me.setValidita(validita);
        menuFacade.create(me);
    }

    /** Aggiunge una news ad un locale
     * 
     * @param idLocale il locale a cui aggiungere la news
     * @param dataInizio La data di inizio della news da cui la news sarà valida
     * @param titolo Il titolo della news
     * @param descr La descrizione dettagliata della news
     */
    @Override
    public void addNews(int idLocale, Date dataInizio, String titolo, String descr) {
        News ne = new News();
        ne.setDataInizio(dataInizio);
        ne.setDescr(descr);
        ne.setIdLocale(idLocale);
        ne.setTitolo(titolo);
        newsFacade.create(ne);
    }

    /** Aggiunge un piatto nel DB
     * 
     * @param nome Il nome del piatto
     * @param categoria La categoria a cui appartiene il piatto
     * @param prezzo Il prezzo assegnato al piatto
     * @param idLocale L'id del locale che ha aggiunto il piatto
     * @param flags I flags relativi alle tolleranze alimentari
     */
    @Override
    public void addPiatto(String nome, Categoria categoria, float prezzo, int idLocale, boolean[] flags) {

        Piatto pi = new Piatto();
        pi.setCategoria(categoria);
        pi.setCorrente(true);
        pi.setCarne(flags[0]);
        pi.setPesce(flags[1]);
        pi.setVegetariano(flags[2]);
        pi.setVegano(flags[3]);
        pi.setCeliaco(flags[4]);
        pi.setAlcolico(flags[5]);
        pi.setNome(nome);
        pi.setPrezzo(prezzo);
        pi.setIdLocale(idLocale);
        piattoFacade.create(pi);
    }

    /** Aggiunge un locale al DB
     * 
     * @param nome Il nome del locale
     * @param indirizzo L'indirizzo del locale
     * @param idUtente L'id utente a cui è associato il locale
     * @param longitudine La longitudine relativa alla posizione del locale
     * @param latitudine La latitudine relativa alla posizione del locale
     * @param proprietario Il nome del proprietario
     * @param pIVA La partita iva del proprietario
     * @param descrizione La descrizione dettagliata del locale
     */
    @Override
    public void addLocale(String nome, String indirizzo, long idUtente, double longitudine, double latitudine, String proprietario, String pIVA, String descrizione) {

        Locale lo = new Locale();
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

    /** Permette di modificare un menu esistente nel DB
     * 
     * @param idLocale L'id del locale a cui il menu è associato
     * @param listaPiatti La lista dei piatti che si vuole associare al menu
     * @param validita La data di validità entro il quale il menu è da ritenersi valido
     */
    @Override
    public void editMenu(int idLocale, List<Piatto> listaPiatti, Date validita) {
        Menu me, temp;

        me = menuDiLocale(idLocale);
        if (me != null) {

            ordinaCat(listaPiatti);
            temp = new Menu();
            temp.setId(me.getId());
            temp.setIdLocale(idLocale);
            temp.setListaPiatti(listaPiatti);
            temp.setValidita(validita);
            menuFacade.edit(temp);
        } else {
            temp = new Menu();
            temp.setIdLocale(idLocale);
            temp.setListaPiatti(listaPiatti);
            temp.setValidita(validita);
            menuFacade.create(temp);
        }



    }

    /**Permette di ottenere tutti i menu presenti nel DB
     * 
     * @return La lista completa dei menu presenti sul DB
     */
    @Override
    public List<Menu> allMenu() {
        return menuFacade.findAll();
    }

    /** Premette di ottenere tutti i piatti nel DB
     * 
     * @return la lista completa dei menu presenti sul DB
     */
    @Override
    public List<Piatto> allPiatti() {
        return piattoFacade.findAll();
    }

    /** Rimuove una combinazione di piatti dal DB
     * 
     * @param id l'id della combinazione di piatti da rimuovere
     */
    @Override
    public void remPiattoCombo(long id) {

        PiattoCombo pc = piattoComboFacade.find(id);
        piattoComboFacade.remove(pc);
    }

    /**Permette di rimuovere un piatto dal DB
     * 
     * @param id L'id del piatto da rimuovere
     */
    @Override
    public void remPiatto(long id) {
        Piatto pi = piattoFacade.find(id);
        piattoFacade.remove(pi);
    }

    /**restituisce il menù di un dato locale, NULL se non esiste
     * 
     * @param idLocale l'id del locale di cui si vuole reperire il menu
     * @return il menu del locale, NULL se non esiste
     */
    @Override
    public Menu menuDiLocale(int idLocale) {
        return menuFacade.findByLocale(idLocale);
    }

    /**Restituisce le combinazioni di piatti di un locale
     * 
     * @param idLocale l'id del locale di cui si vogliono ottenere le combinazioni
     * @return La lista delle combinazioni di piatti offerte dal locale
     */
    @Override
    public List<PiattoCombo> getMenuCombo(int idLocale) {
        return piattoComboFacade.findByLocale(idLocale);

    }

    /**metodo grezzo, adatto al test.Visualizza il menù del giorno
     * per un dato locale.Se non valido, la stringa ritornata inizierà 
     * con "*"
     * @deprecated 
     * @param idLocale L'id del locale di cui si vuole visualizzare il menu
     * @return una strunga contenente il menu formattato come descritto sopra
     */
    @Override
    public String mostraMenu(int idLocale) {

        String ret = "";
        Menu me;
        List<Piatto> lp;
        Piatto pi;
        GregorianCalendar now = new GregorianCalendar();
        int oldcatnum = -1; //utile per la separazione delle categorie

        me = menuDiLocale(idLocale);

        if (me == null) {
            return "*** NESSUN MENU' TROVATO ***";
        }

        //controllo che sia valido
        if (me.getValidita().compareTo(now.getTime()) < 0) {
            ret += "*** MENU' NON AGGIORNATO ***\n\n";
        }
        //ottengo e scorro la lista dei piatti del menù    
        lp = me.getListaPiatti();
        for (int j = 0; j < lp.size(); j++) {

            pi = lp.get(j);
            //inserisco un separatore tra le categorie di piatti(GIA'ORDINATI!)
            if (pi.getCategoria().ordinal() > oldcatnum) {
                ret += "---- " + pi.getCategoria() + " ----\n";
                oldcatnum = pi.getCategoria().ordinal();
            }

            ret += pi.getNome() + "___€" + pi.getPrezzo() + "\n";
        }
        return ret;
    }


    /**dato un locale, restituisce una stringa con tutte le sue combo e i prezzi
     * @deprecated
     * @param idLocale l'id del locale di cui si vogliono visualizzare le combinazioni dei piatti
     * @return una stringa contenente la lista delle combinazioni formattata come sopra
     */
    public String mostraCombo(int idLocale) {
        String ret = "";
        List<PiattoCombo> pc = piattoComboFacade.findAll();

        for (int i = 0; i < pc.size(); i++) {
            if (pc.get(i).getIdLocale() == idLocale) {
                ret += pc.get(i).getDescr() + "_______€" + pc.get(i).getPrezzo() + "\n";
            }
        }

        if (ret.equals("")) {
            return "*** nessuna combinazione trovata ***";
        }
        return ret;

    }

    //ordinamento della lista di piatti secondo la propria categoria
    //necessario per una corretta visualizzazione del menù.
    //implementazione dell'insertion sort
    //verificare se con "set" si ha comportamento simile ad array
    
    private void ordinaCat(List<Piatto> lp) {

        int j;
        Piatto temp;

        for (int i = 1; i < lp.size(); i++) {

            j = i;
            temp = lp.get(i);

            while (j > 0 && lp.get(j - 1).getCategoria().ordinal() > temp.getCategoria().ordinal()) {
                lp.set(j, lp.get(j - 1));
                j--;
            }

            lp.set(j, temp);
        }
    }

    /** Permette di ottenere le categorie di piatti disponbili nel DB
     * 
     * @return una lista contenente la rappresentazione testuale delle categorie
     */
    public List<String> listaCat() {

        List<String> ret = new ArrayList<String>();

        for (Categoria cat : Categoria.values()) {
            ret.add(cat.toString());
        }
        return ret;

    }

    /**Restituisce un oggetto di tipo Locale da una richiesta http
     * utilizza una mappa per mappare gli oggetti con le request
     * @param req una richiesta http
     */
    @Override
    public void localeDaReq(HttpServletRequest req) {
        Locale loc = new Locale();
        Util.riempi(req, loc);
        localeFacade.create(loc);
    }

    /**Restituisce una stringa contenente tutti i locali presenti nel DB
     * @deprecated 
     * @return una stringa di tutti i locali
     */
    @Override
    public String locali() {
        String ret = "";
        List<Locale> ll = localeFacade.findAll();
        System.err.println("ci sono: " + ll.size() + " elementi");
        for (int i = 0; i < ll.size(); i++) {
            ret += ll.get(i).getNome() + "<br>";
        }
        return ret;
    }

    /**Restituisce una lista di locali data una posizione lat ,lon e un raggio di ricerca
     * 
     * @param lat latitudine
     * @param lon longitudine
     * @param dist il raggio massimo entro il quale devono trovarsi i locali restituiti
     * @return una lista di tutti i locali che si trovano entro un raggio dist dal punto (lat,lon)
     */
    public List<Locale> trovaLocali(double lat, double lon, double dist) {

        List<Locale> ll = localeFacade.findAll();
        List<Locale> ret = new ArrayList<Locale>();

        for (Locale loc : ll) {
            if (vicino(loc, lat, lon, dist)) {
                ret.add(loc);
            }
        }

        return ret;
    }

    /* Metodo privato per capire se un locale è vicino o meno
     */
    private boolean vicino(Locale loc, double lat, double lon, double dist) {
        int R = 6371;
        double a, c, d;
        double dlat = Math.toRadians(lat - loc.getLatitudine());
        double dlon = Math.toRadians(lon - loc.getLongitudine());
        lat = Math.toRadians(lat);
        lon = Math.toRadians(lon);
        a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
                + Math.sin(dlon / 2) * Math.sin(dlon / 2) * Math.cos(lat) * Math.cos(lon);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        d = (double) R * c;

        System.out.println("la distanza è: " + d);

        if (d <= dist) {
            return true;
        } else {
            return false;
        }
    }

    //lista booleani in ordine corretto:CARNE,PESCE,VEGETARIANO,VEGANO,CELIACO,ALCOLICO
    //FLAGS contrario: 0.0.ALCOLICO.CELIACO.VEGANO.VEGETARIANO.PESCE.CARNE
    private byte generaFlags(boolean[] bools) {
        byte flags = 0;

        for (int i = 0; i < NUMFLAGS; i++) {
            if (bools[i]) {
                flags = (byte) (flags | (1 << i));
            }
        }
        return flags;
    }

    private boolean[] generaBool(byte flags) {
        boolean[] bools = new boolean[NUMFLAGS];

        for (int i = 0; i < NUMFLAGS; i++) {
            if ((flags & (1 << i)) == (byte) Math.pow(2, i)) {
                bools[i] = true;
            } else {
                bools[i] = false;
            }
        }
        return bools;
    }

    /**Restituisce true se il menu del locale è valido (la data odierna è inferiore o uguale a quella di validità del menu)
     * 
     * @param idLocale l'id del locale di cui si vuole sapere se il menu è valido
     * @return un booleano
     */
    public boolean menuValido(int idLocale) {

        List<Menu> lm = menuFacade.findAll();
        GregorianCalendar now = new GregorianCalendar();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.AM_PM, Calendar.AM);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        for (Menu menu : lm) {
            if (menu.getIdLocale() == idLocale) {
                System.err.println("menu:" + menu.getValidita() + " now:" + now.getTime());
                if (menu.getValidita().before(now.getTime())) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**Permette di ottenere una lista di tutti i locali associati  ad un utente
     * 
     * @param idUtente l'id utente di cui si vuole conoscere la lista di locali associati
     * @return
     */
    @Override
    public Locale getLocali(long idUtente) {
        return localeFacade.findByUtente(idUtente);
    }

    /**Crea una lista contenente tutti i locali presenti sul DB
     * 
     * @return una lista con tutti i locali presenti nel DB
     */
    @Override
    public List<Locale> getTuttiLocali() {
        List<Locale> loc = localeFacade.findAll();
        System.err.println("[ControlloreLocale.java] la lista ha dimensioni : " + loc.size());
        return loc;
    }

    /**Aggiunge un locale su DB
     * 
     * @param locale il locale da aggiungere
     */
    @Override
    public void addLocale(Locale locale) {
        localeFacade.create(locale);
    }

    /**Permette di trovare l'istanza di un locale dato il suo id univoco
     * 
     * @param idLocale l'id del locale
     * @return il locale cercato
     */
    @Override
    public Locale findById(int idLocale) {
        return localeFacade.find(idLocale);
    }

    /**Permete di ottenere una lista contenente tutti gli eventi associati ad un locale
     * 
     * @param idLocale l'id del locale di cui si vuole conoscere la lista degli eventi
     * @return la lista di tutti gli eventi associati al locale
     */
    @Override
    public List<Evento> getEventi(int idLocale) {
        return eventoFacade.findByLocale(idLocale);
    }

    /**Permete di ottenere una lista contenente tutti le news associate ad un locale
     * 
     * @param idLocale l'id del locale di cui si vuole conoscere la lista di tutte le news
     * @return la lista di tutti le news associate al locale
     */
    @Override
    public List<News> getNews(int idLocale) {
        return newsFacade.findByLocale(idLocale);
    }
}
