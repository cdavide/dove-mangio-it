/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.util.Date;
import javax.ejb.Local;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Local
public interface ControlloreLocaleLocal {
    
    /** Aggiunge un piatto nel DB
     * 
     * @param nome Il nome del piatto
     * @param categoria La categoria a cui appartiene il piatto
     * @param prezzo Il prezzo assegnato al piatto
     * @param idLocale L'id del locale che ha aggiunto il piatto
     * @param flags I flags relativi alle tolleranze alimentari
     */
    public void addPiatto(String nome, Categoria categoria, float prezzo,int idLocale, boolean [] flags);
    /**Permette di rimuovere un piatto dal DB
     * 
     * @param id L'id del piatto da rimuovere
     */
    public void remPiatto(long id);
    
    /** Aggiunge una combinazione di piatti ad un locale
     * 
     * @param descr la descrizione della combinazione
     * @param prezzo Il prezzo della combinazione dei piatti
     * @param idLocale L'id del locale a cui aggiungere la combinazione
     */
    public void addPiattoCombo(String descr, float prezzo, int idLocale);
    /** Rimuove una combinazione di piatti dal DB
     * 
     * @param id l'id della combinazione di piatti da rimuovere
     */
    public void remPiattoCombo(long id);
    /** Aggiunge un oggetto menu ad un locale
     * 
     * @param idLocale L'id del locale a cui aggiungere il menu
     * @param listaPiatti La lista dei piatti presenti nel menu
     * @param validita La data entro la quale il menu è da ritenersi valido
     */
    public void addMenu(int idLocale, List<Piatto> listaPiatti, Date validita);
    /** Permette di modificare un menu esistente nel DB
     * 
     * @param idLocale L'id del locale a cui il menu è associato
     * @param listaPiatti La lista dei piatti che si vuole associare al menu
     * @param validita La data di validità entro il quale il menu è da ritenersi valido
     */
    public void editMenu(int idLocale, List<Piatto> listaPiatti, Date validita);
    
    /** Aggiunge una news ad un locale
     * 
     * @param idLocale il locale a cui aggiungere la news
     * @param dataInizio La data di inizio della news da cui la news sarà valida
     * @param titolo Il titolo della news
     * @param descr La descrizione dettagliata della news
     */
    public void addNews(int idLocale, Date dataInizio, String titolo, String descr);
    /** Aggiunge un evento al locale 
     * 
     * @param idLocale L'id del locale a cui l'evento fa riferimento
     * @param dataInizio La data di inizio dell'evento
     * @param dataFine La data di fine dell'evento
     * @param titolo Il titolo dell'evento da visualizzare nella homepage
     * @param descr La descrizione nel dettaglio dell'evento
     */
    public void addEvento(int idLocale, Date dataInizio, Date dataFine, String titolo, String descr);
    
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
    public void addLocale(String nome, String indirizzo,long idUtente, double longitudine, double latitudine, String proprietario, String pIVA,String descrizione);
    
    /**Aggiunge un locale su DB
     * 
     * @param locale il locale da aggiungere
     */
    public void addLocale(Locale locale);
    /**restituisce il menù di un dato locale, NULL se non esiste
     * 
     * @param idLocale l'id del locale di cui si vuole reperire il menu
     * @return il menu del locale, NULL se non esiste
     */
    public Menu menuDiLocale(int idLocale);
    /**Restituisce le combinazioni di piatti di un locale
     * 
     * @param idLocale l'id del locale di cui si vogliono ottenere le combinazioni
     * @return La lista delle combinazioni di piatti offerte dal locale
     */
    public List<PiattoCombo> getMenuCombo(int idLocale);
    
    /**metodo grezzo, adatto al test.Visualizza il menù del giorno
     * per un dato locale.Se non valido, la stringa ritornata inizierà 
     * con "*"
     * @deprecated 
     * @param idLocale L'id del locale di cui si vuole visualizzare il menu
     * @return una strunga contenente il menu formattato come descritto sopra
     */
    public String mostraMenu(int idLocale);
    /**dato un locale, restituisce una stringa con tutte le sue combo e i prezzi
     * @deprecated
     * @param idLocale l'id del locale di cui si vogliono visualizzare le combinazioni dei piatti
     * @return una stringa contenente la lista delle combinazioni formattata come sopra
     */
    public String mostraCombo(int  idLocale);
    
    /** Permette di ottenere le categorie di piatti disponbili nel DB
     * 
     * @return una lista contenente la rappresentazione testuale delle categorie
     */
    public List<String> listaCat();
    
    /**Restituisce un oggetto di tipo Locale da una richiesta http
     * utilizza una mappa per mappare gli oggetti con le request
     * @param req una richiesta http
     */
    public void localeDaReq(HttpServletRequest req);
    
    /**Restituisce una stringa contenente tutti i locali presenti nel DB
     * @deprecated 
     * @return una stringa di tutti i locali
     */
    public String locali();
    
    /**Restituisce una lista di locali data una posizione lat ,lon e un raggio di ricerca
     * 
     * @param lat latitudine
     * @param lon longitudine
     * @param dist il raggio massimo entro il quale devono trovarsi i locali restituiti
     * @return una lista di tutti i locali che si trovano entro un raggio dist dal punto (lat,lon)
     */
    public List<Locale> trovaLocali(double lat, double lon, double dist);
    /**Restituisce true se il menu del locale è valido (la data odierna è inferiore o uguale a quella di validità del menu)
     * 
     * @param idLocale l'id del locale di cui si vuole sapere se il menu è valido
     * @return un booleano
     */
    public boolean menuValido(int idLocale);

    /**Permette di ottenere una lista di tutti i locali associati  ad un utente
     * 
     * @param idUtente l'id utente di cui si vuole conoscere la lista di locali associati
     * @return
     */
    public Locale getLocali(long idUtente);
    
    /**Crea una lista contenente tutti i locali presenti sul DB
     * 
     * @return una lista con tutti i locali presenti nel DB
     */
    public List<Locale> getTuttiLocali();
    /**Permette di trovare l'istanza di un locale dato il suo id univoco
     * 
     * @param idLocale l'id del locale
     * @return il locale cercato
     */
    public Locale findById(int idLocale);

    /**Permette di ottenere tutti i menu presenti nel DB
     * 
     * @return La lista completa dei menu presenti sul DB
     */
    public java.util.List<luncharoundpkg.Menu> allMenu();

    /** Premette di ottenere tutti i piatti nel DB
     * 
     * @return la lista completa dei menu presenti sul DB
     */
    public java.util.List<luncharoundpkg.Piatto> allPiatti();
    
    /**Permete di ottenere una lista contenente tutti gli eventi associati ad un locale
     * 
     * @param idLocale l'id del locale di cui si vuole conoscere la lista degli eventi
     * @return la lista di tutti gli eventi associati al locale
     */
    public List<Evento> getEventi(int idLocale);
    
    /**Permete di ottenere una lista contenente tutti le news associate ad un locale
     * 
     * @param idLocale l'id del locale di cui si vuole conoscere la lista di tutte le news
     * @return la lista di tutti le news associate al locale
     */
    public List<News> getNews(int idLocale);
    
}
