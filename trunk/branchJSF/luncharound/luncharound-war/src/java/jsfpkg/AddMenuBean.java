/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.Categoria;
import luncharoundpkg.ControlloreLocaleLocal;
import luncharoundpkg.ControllorePiattiLocal;
import luncharoundpkg.ControllorePiattoComboLocal;
import luncharoundpkg.Menu;
import luncharoundpkg.Piatto;
import luncharoundpkg.PiattoCombo;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import javax.faces.context.ExternalContext;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "addMenuBean")
@ViewScoped
public class AddMenuBean {

    @EJB
    private ControlloreLocaleLocal controlloreLocale;
    @EJB
    private ControllorePiattiLocal controllorePiatto;
    @EJB
    private ControllorePiattoComboLocal controllorePiattoCombo;
    private Menu menu;
    private List<Piatto> newListPiatti;
    private List<Piatto> allPiattoNames;
    private Integer idLocale;
    private Long idUtente;
    private List<Piatto> primi;
    private List<Piatto> secondi;
    private List<Piatto> contorni;
    private List<Piatto> dolci;
    private List<Piatto> bevande;
    private List<Piatto> antipasti;
    private List<PiattoCombo> piattoCombo;
    private Piatto nuovoPrimo;
    private Piatto nuovoAntipasto;
    private Piatto nuovoSecondo;
    private Piatto nuovoDolce;
    private Piatto nuovaBevanda;
    private Piatto nuovoContorno;
    private PiattoCombo nuovoPiattoCombo;
    private Date date;
    // serve per eliminare i piatti dal menu
    // direttamente dalla datatable
    private Piatto currentPiatto;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB
    // il piatto nuovo viene reinizializzato
    //

    /**
     * 
     */
    public void AddMenuBean() {
        System.err.println("Costruttore inizio [addmenuBean]!");

    }

    /**
     * 
     */
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        idLocale = (Integer) session.getAttribute("mioLocale");
        idUtente = (Long) session.getAttribute("idUtente");
        if (idLocale == null || idUtente == null || !request.isRequestedSessionIdValid()) {
            System.out.println("[AddMenuBean] attenzione non hai effettuato il login, il locale non esiste, la sessione è scaduta");
            // devo impedire la visualizzazione del menu del locale
            //
        }

        try {
            menu = controlloreLocale.menuDiLocale(idLocale);
            newListPiatti = menu.getListaPiatti();
        } catch (Exception e) {
            // il locale non ha alcun menu
            // il menu non contiene piatti
            menu = new Menu();
            newListPiatti = new ArrayList<Piatto>();
        }
        try {
            piattoCombo = controlloreLocale.getMenuCombo(idLocale);
            if(piattoCombo == null){
                piattoCombo = new ArrayList<PiattoCombo>();
            }
        } catch (Exception e) {
            piattoCombo = new ArrayList<PiattoCombo>();
        }
        try {
            allPiattoNames = controlloreLocale.allPiatti(); // la predizione del
            // piatto viene fatta su tutti i piatti nel DB, non solo su quelli del locale
        } catch (Exception e) {
            // non son opresenti piatti nel DB
        }
        date = menu.getValidita();
        if (date == null || date.before(new Date())) {
            date = new Date();
        }

        /*divisione in tab in base alla categoria*/
        nuovoAntipasto = controllorePiatto.createTemplatePiatto(Categoria.ANTIPASTI, idLocale);

        nuovaBevanda = controllorePiatto.createTemplatePiatto(Categoria.BEVANDE, idLocale);

        nuovoDolce = controllorePiatto.createTemplatePiatto(Categoria.DOLCI, idLocale);

        nuovoSecondo = controllorePiatto.createTemplatePiatto(Categoria.SECONDI, idLocale);

        nuovoPrimo = controllorePiatto.createTemplatePiatto(Categoria.PRIMI, idLocale);

        nuovoContorno = controllorePiatto.createTemplatePiatto(Categoria.CONTORNI, idLocale);
        
        nuovoPiattoCombo = controllorePiattoCombo.createTemplatePiatto(idLocale); 

        try {
            primi = controllorePiatto.getCategoriaLocale(idLocale, Categoria.PRIMI);
            secondi = controllorePiatto.getCategoriaLocale(idLocale, Categoria.SECONDI);
            contorni = controllorePiatto.getCategoriaLocale(idLocale, Categoria.CONTORNI);
            antipasti = controllorePiatto.getCategoriaLocale(idLocale, Categoria.ANTIPASTI);
            bevande = controllorePiatto.getCategoriaLocale(idLocale, Categoria.BEVANDE);
            dolci = controllorePiatto.getCategoriaLocale(idLocale, Categoria.DOLCI);
        } catch (Exception e) {

            primi = new ArrayList<Piatto>();
            secondi = new ArrayList<Piatto>();
            contorni = new ArrayList<Piatto>();
            antipasti = new ArrayList<Piatto>();
            bevande = new ArrayList<Piatto>();
            dolci = new ArrayList<Piatto>();
        }

        /*Piatti vuoti per aggiunta nel menu*/
        primi.add(nuovoPrimo);
        secondi.add(nuovoSecondo);
        antipasti.add(nuovoAntipasto);
        contorni.add(nuovoContorno);
        bevande.add(nuovaBevanda);
        dolci.add(nuovoDolce);
        piattoCombo.add(nuovoPiattoCombo);
        System.err.println("Post constructor OK!");
    }

    /**
     * 
     */
    public void remove() {
    }

    /**
     * 
     * @param query
     * @return
     */
    public List<String> complete(String query) {
        List<String> results = new ArrayList<String>();
        // creare query nel controllore che chiama la facade per avere i risultati
        // in base al nome fornito
        // la signature del metodo sarà simile a:
        // controlloreLocale.completeQuery(nomeTabella, nome campo della tabella, stringa input)
        // solleva una eccezione 
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }

        return results;
    }

    /**
     * 
     */
    public void add() {
        FacesContext context = FacesContext.getCurrentInstance();
        //controllare id utente altrimenti non si puo procedere, oppure che la sessione sia valida
        if (!checkUserLogin()) { // l'utente non è padrone del locale
            addFacesMessage("Devi effettuare il login per registrare un nuovo menu.", "Errore!");
            return;
        }
        // prendo il parametro passato dalla jsf
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        int idOperation = Integer.parseInt(params.get("idOperation"));
        System.out.println("idOp: " + idOperation);

        switch (idOperation) {
            case 0:// antipasto
                System.out.println("aggiunta antipasto!");
                controllorePiatto.addPiatto(nuovoAntipasto); // aggiungo il piatto nel DB
                newListPiatti.add(nuovoAntipasto);// aggiungo il piatto alla lista
                nuovoAntipasto = controllorePiatto.createTemplatePiatto(Categoria.ANTIPASTI, idLocale);
                antipasti.add(nuovoAntipasto);
                break;
            case 1: // primo
                System.out.println("aggiunta primo!");
                controllorePiatto.addPiatto(nuovoPrimo); // aggingo il piatto nel DB;
                newListPiatti.add(nuovoPrimo);// aggiungo il piatto alla lista
                nuovoPrimo = controllorePiatto.createTemplatePiatto(Categoria.PRIMI, idLocale);
                primi.add(nuovoPrimo);
                break;
            case 2:   // secondo
                System.out.println("aggiunta secondo!");
                controllorePiatto.addPiatto(nuovoSecondo); // aggingo il piatto nel DB;
                newListPiatti.add(nuovoSecondo);// aggiungo il piatto alla lista
                nuovoSecondo = controllorePiatto.createTemplatePiatto(Categoria.SECONDI, idLocale);
                secondi.add(nuovoSecondo);
                break;
            case 3: // contorno
                System.out.println("aggiunta contorno!");
                controllorePiatto.addPiatto(nuovoContorno); // aggingo il piatto nel DB ;
                newListPiatti.add(nuovoContorno);// aggiungo il piatto alla lista
                nuovoContorno = controllorePiatto.createTemplatePiatto(Categoria.CONTORNI, idLocale);
                contorni.add(nuovoContorno);
                break;


            case 4: // dolce
                System.out.println("aggiunta dolce!");
                controllorePiatto.addPiatto(nuovoDolce); // aggingo il piatto nel DB;
                newListPiatti.add(nuovoDolce);// aggiungo il piatto alla lista
                nuovoDolce = controllorePiatto.createTemplatePiatto(Categoria.DOLCI, idLocale);
                dolci.add(nuovoDolce);
                break;
            case 5: // bevanda
                System.out.println("aggiunta bevanda!");
                controllorePiatto.addPiatto(nuovaBevanda); // aggingo il piatto nel DB;
                newListPiatti.add(nuovaBevanda);// aggiungo il piatto alla lista
                nuovaBevanda = controllorePiatto.createTemplatePiatto(Categoria.BEVANDE, idLocale);
                bevande.add(nuovaBevanda);
                break;
            case 6:
                System.out.println("aggiunto piattoCombo!");
                controllorePiattoCombo.addPiatto(nuovoPiattoCombo);
                nuovoPiattoCombo = controllorePiattoCombo.createTemplatePiatto(idLocale);
                piattoCombo.add(nuovoPiattoCombo);
                break;
        }
        // la lista è aggiornata, propago le modifice al DB

        editMenu();
    }


    /*Funzione richiamata quando viene cancellata una entry dalla tabella dei piatti
     */
    /**
     * 
     */
    public void delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        Piatto toRem;
        PiattoCombo toRemC;
        //controllare id utente altrimenti non si puo procedere, oppure che la sessione sia valida
        if (!checkUserLogin()) {
            addFacesMessage("Devi effettuare il login per registrare un nuovo menu.", "Errore!");
            return;
        }
        // prendo il parametro passato dalla jsf
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        long idPiatto = Integer.parseInt(params.get("idPiatto"));
        // prendo il parametro passato dalla jsf
        int idOperation = Integer.parseInt(params.get("idOperation"));
        System.out.println("idOp: " + idOperation);
        switch (idOperation) {
            case 0:// antipasto
                System.out.println("Rimozione antipasto!");
                try {
                    toRem = controllorePiatto.findById(idPiatto);
                    // inverto l'ordine delle remove per vincoli integrità db
                    newListPiatti.remove(toRem);// rimuovo il piatto dalla lista
                    editMenu();
                    antipasti.remove(toRem);
                    controllorePiatto.removePiatto(toRem); // rimuovo il piatto nel DB;
                    // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
                } catch (Exception e) {
                    System.out.println("[AddMenuBean.java]: non trovato piatto da cancellare, forse id errato?");
                }
                break;
            case 1: // primo
                System.out.println("Rimozione primo!");
                try {
                    toRem = controllorePiatto.findById(idPiatto);
                    // inverto l'ordine delle remove per vincoli integrità db
                    newListPiatti.remove(toRem);// rimuovo il piatto dalla lista
                    editMenu();
                    primi.remove(toRem);
                    controllorePiatto.removePiatto(toRem); // rimuovo il piatto nel DB;
                    // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
                } catch (Exception e) {
                    System.out.println("[AddMenuBean.java]: non trovato piatto da cancellare, forse id errato?");
                }
                break;
            case 2:   // secondo
                System.out.println("Rimozione secondo!");
                try {
                    toRem = controllorePiatto.findById(idPiatto);
                    // inverto l'ordine delle remove per vincoli integrità db
                    newListPiatti.remove(toRem);// rimuovo il piatto dalla lista
                    editMenu();
                    secondi.remove(toRem);
                    controllorePiatto.removePiatto(toRem); // rimuovo il piatto nel DB;
                    // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
                } catch (Exception e) {
                    System.out.println("[AddMenuBean.java]: non trovato piatto da cancellare, forse id errato?");
                }
                break;

            case 3: // contorno
                System.out.println("Rimozione contorno!");
                try {
                    toRem = controllorePiatto.findById(idPiatto);
                    // inverto l'ordine delle remove per vincoli integrità db
                    newListPiatti.remove(toRem);// rimuovo il piatto dalla lista
                    editMenu();
                    contorni.remove(toRem);
                    controllorePiatto.removePiatto(toRem); // rimuovo il piatto nel DB;
                    // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
                } catch (Exception e) {
                    System.out.println("[AddMenuBean.java]: non trovato piatto da cancellare, forse id errato?");
                }
                break;

            case 4: // dolce
                System.out.println("Rimozione dolce!");
                try {
                    toRem = controllorePiatto.findById(idPiatto);
                    // inverto l'ordine delle remove per vincoli integrità db
                    newListPiatti.remove(toRem);// rimuovo il piatto dalla lista
                    editMenu();
                    dolci.remove(toRem);
                    controllorePiatto.removePiatto(toRem); // rimuovo il piatto nel DB;
                    // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
                } catch (Exception e) {
                    System.out.println("[AddMenuBean.java]: non trovato piatto da cancellare, forse id errato?");
                }
                break;
            case 5: // bevanda
                System.out.println("Rimozione bevanda!");
                try {
                    toRem = controllorePiatto.findById(idPiatto);
                    // inverto l'ordine delle remove per vincoli integrità db
                    newListPiatti.remove(toRem);// rimuovo il piatto dalla lista
                    editMenu();
                    bevande.remove(toRem);
                    controllorePiatto.removePiatto(toRem); // rimuovo il piatto nel DB;
                    // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
                } catch (Exception e) {
                    System.out.println("[AddMenuBean.java]: non trovato piatto da cancellare, forse id errato?");
                }
                break;
            case 6:
                System.out.println("Rimozione piattoCombo!");
                try {
                    toRemC = controllorePiattoCombo.findById(idPiatto);
                    piattoCombo.remove(toRemC);
                    controllorePiattoCombo.removePiatto(toRemC); // rimuovo il piatto nel DB;
                    // speriamo non si inchiodi, altrimenti tocca fare il rollback a mano
                } catch (Exception e) {
                    System.out.println("[AddMenuBean.java]: non trovato piattoCombo da cancellare, forse id errato?");
                }
                break;
        }
        // la lista è aggiornata, propago le modifice al DB

        //editMenu();


    }

    /**
     * 
     */
    public void editMenu() {
        try {
            controllorePiatto.editListaPiatti(newListPiatti);
            //controllorePiattoCombo.editListaPiatti(piattoCombo);
            controlloreLocale.editMenu(idLocale, newListPiatti, date);
        } // aggiorno il menu
        catch (Exception e) {
            addFacesMessage("Modifiche non eseguite ", "Failure!");
            return;
        }
        addFacesMessage("Modifiche menu effettuate correttamente", "Success!");
    }

    /**
     * 
     * @param str
     * @param severity
     */
    public void addFacesMessage(String str, String severity) {
        FacesMessage msg = null;
        System.out.println(str);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, severity, str);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    /**
     * 
     * @return
     */
    public boolean checkUserLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        idUtente = (Long) httpSession.getAttribute("idUtente");
        idLocale = (Integer) httpSession.getAttribute("mioLocale");

        return (idUtente == null || // user not logged in
                !request.isRequestedSessionIdValid() || // sessione scaduta
                idLocale != controlloreLocale.getLocali(idUtente).getIdUtente()); // l'utente non è padrone del locale
    }

    /**
     * 
     * @throws DocumentException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void printPdf() throws DocumentException, FileNotFoundException, IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.setResponseHeader("Content-Type", "application/pdf");
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "Menu" + ".pdf" + "\"");

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, ec.getResponseOutputStream());
        document.open();
        Paragraph ant = new Paragraph("Antipasti");
        Paragraph pr = new Paragraph("Primi");
        Paragraph co = new Paragraph("Contorni");
        Paragraph sc = new Paragraph("Secondi");
        Paragraph dol = new Paragraph("Dolci");
        Paragraph be = new Paragraph("Bevande");
        Paragraph pc = new Paragraph("Combinazioni");
        Font font = new Font(Font.COURIER, 20, Font.BOLD); // 1
        font.setColor(new Color(0x92, 0x90, 0x83));

        Chunk chunk = new Chunk("Antipasti", font);
        Paragraph par = new Paragraph(chunk);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        par.setSpacingBefore(50);
        par.setSpacingAfter(50);
        PdfPTable table = new PdfPTable(2);
        document.add(par);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        for (Piatto p : antipasti) {
            if (null != p.getNome() && !p.getNome().equals("")) {
                table.addCell(p.getNome());
                table.addCell(String.valueOf(p.getPrezzo()) + "  €");
            }
        }
        document.add(table);
        chunk = new Chunk("Primi", font);
        par = new Paragraph(chunk);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        par.setSpacingBefore(50);
        par.setSpacingAfter(50);
        document.add(par);
        table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        for (Piatto p : primi) {
            if (null != p.getNome() && !p.getNome().equals("")) {
                table.addCell(p.getNome());
                table.addCell(String.valueOf(p.getPrezzo()) + "  €");
            }
        }
        document.add(table);
        chunk = new Chunk("Contorni", font);
        par = new Paragraph(chunk);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        par.setSpacingBefore(50);
        par.setSpacingAfter(50);
        document.add(par);
        table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        for (Piatto p : contorni) {
            if (null != p.getNome() && !p.getNome().equals("")) {
                table.addCell(p.getNome());
                table.addCell(String.valueOf(p.getPrezzo()) + "  €");
            }
        }
        document.add(table);

        chunk = new Chunk("Secondi", font);
        par = new Paragraph(chunk);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        par.setSpacingBefore(50);
        par.setSpacingAfter(50);
        document.add(par);
        table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        for (Piatto p : secondi) {
            if (null != p.getNome() && !p.getNome().equals("")) {
                table.addCell(p.getNome());
                table.addCell(String.valueOf(p.getPrezzo()) + "  €");
            }
        }
        document.add(table);

        chunk = new Chunk("Dolci", font);
        par = new Paragraph(chunk);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        par.setSpacingBefore(50);
        par.setSpacingAfter(50);
        document.add(par);
        table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        for (Piatto p : dolci) {
            if (null != p.getNome() && !p.getNome().equals("")) {
                table.addCell(p.getNome());
                table.addCell(String.valueOf(p.getPrezzo()) + "  €");
            }
        }
        document.add(table);
        chunk = new Chunk("Bevande", font);
        par = new Paragraph(chunk);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        par.setSpacingBefore(50);
        par.setSpacingAfter(50);
        document.add(par);
        table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        for (Piatto p : bevande) {
            if (null != p.getNome() && !p.getNome().equals("")) {
                table.addCell(p.getNome());
                table.addCell(String.valueOf(p.getPrezzo()) + "  €");
            }
        }
        document.add(table);
        
        chunk = new Chunk("Combinazioni", font);
        par = new Paragraph(chunk);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        par.setSpacingBefore(50);
        par.setSpacingAfter(50);
        document.add(par);
        table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        for (PiattoCombo p : piattoCombo) {
            if (null != p.getDescr() && !p.getDescr().equals("")) {
                table.addCell(p.getDescr());
                table.addCell(String.valueOf(p.getPrezzo()) + "  €");
            }
        }
        document.add(table);
        document.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    /**
     * 
     * @return
     */
    public List<Piatto> getAllPiattoNames() {
        return allPiattoNames;
    }

    /**
     * 
     * @return
     */
    public Piatto getNuovaBevanda() {
        return nuovaBevanda;
    }

    /**
     * 
     * @return
     */
    public Piatto getCurrentPiatto() {
        return currentPiatto;
    }

    /**
     * 
     * @param currentPiatto
     */
    public void setCurrentPiatto(Piatto currentPiatto) {
        this.currentPiatto = currentPiatto;
    }

    /**
     * 
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * 
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 
     * @param nuovaBevanda
     */
    public void setNuovaBevanda(Piatto nuovaBevanda) {
        this.nuovaBevanda = nuovaBevanda;
    }

    /**
     * 
     * @return
     */
    public Piatto getNuovoAntipasto() {
        return nuovoAntipasto;
    }

    /**
     * 
     * @param nuovoAntipasto
     */
    public void setNuovoAntipasto(Piatto nuovoAntipasto) {
        this.nuovoAntipasto = nuovoAntipasto;
    }

    /**
     * 
     * @return
     */
    public Piatto getNuovoContorno() {
        return nuovoContorno;
    }

    /**
     * 
     * @param nuovoContorno
     */
    public void setNuovoContorno(Piatto nuovoContorno) {
        this.nuovoContorno = nuovoContorno;
    }

    /**
     * 
     * @return
     */
    public Piatto getNuovoDolce() {
        return nuovoDolce;
    }

    /**
     * 
     * @param nuovoDolce
     */
    public void setNuovoDolce(Piatto nuovoDolce) {
        this.nuovoDolce = nuovoDolce;
    }

    /**
     * 
     * @return
     */
    public Piatto getNuovoPrimo() {
        return nuovoPrimo;
    }

    /**
     * 
     * @param nuovoPrimo
     */
    public void setNuovoPrimo(Piatto nuovoPrimo) {
        this.nuovoPrimo = nuovoPrimo;
    }

    /**
     * 
     * @return
     */
    public Piatto getNuovoSecondo() {
        return nuovoSecondo;
    }

    /**
     * 
     * @param nuovoSecondo
     */
    public void setNuovoSecondo(Piatto nuovoSecondo) {
        this.nuovoSecondo = nuovoSecondo;
    }

    /**
     * 
     * @param allPiattoNames
     */
    public void setAllPiattoNames(List<Piatto> allPiattoNames) {
        this.allPiattoNames = allPiattoNames;
    }

    /**
     * 
     * @return
     */
    public Integer getIdLocale() {
        return idLocale;
    }

    /**
     * 
     * @param idLocale
     */
    public void setIdLocale(Integer idLocale) {
        this.idLocale = idLocale;
    }

    /**
     * 
     * @return
     */
    public List<Piatto> getAntipasti() {
        return antipasti;
    }

    /**
     * 
     * @param antipasti
     */
    public void setAntipasti(List<Piatto> antipasti) {
        this.antipasti = antipasti;
    }

    /**
     * 
     * @return
     */
    public List<Piatto> getBevande() {
        return bevande;
    }

    /**
     * 
     * @param bevande
     */
    public void setBevande(List<Piatto> bevande) {
        this.bevande = bevande;
    }

    /**
     * 
     * @return
     */
    public List<Piatto> getContorni() {
        return contorni;
    }

    /**
     * 
     * @param contorni
     */
    public void setContorni(List<Piatto> contorni) {
        this.contorni = contorni;
    }

    /**
     * 
     * @return
     */
    public List<Piatto> getDolci() {
        return dolci;
    }

    /**
     * 
     * @param dolci
     */
    public void setDolci(List<Piatto> dolci) {
        this.dolci = dolci;
    }

    /**
     * 
     * @return
     */
    public List<Piatto> getPrimi() {
        return primi;
    }

    /**
     * 
     * @param primi
     */
    public void setPrimi(List<Piatto> primi) {
        this.primi = primi;
    }

    /**
     * 
     * @return
     */
    public List<Piatto> getSecondi() {
        return secondi;
    }

    /**
     * 
     * @param secondi
     */
    public void setSecondi(List<Piatto> secondi) {
        this.secondi = secondi;
    }

    /**
     * 
     * @return
     */
    public Long getIdUtente() {
        return idUtente;
    }

    /**
     * 
     * @param idUtente
     */
    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * 
     * @return
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * 
     * @param menu
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * 
     * @return
     */
    public List<Piatto> getNewListPiatti() {
        return newListPiatti;
    }

    /**
     * 
     * @param newListPiatti
     */
    public void setNewListPiatti(List<Piatto> newListPiatti) {
        this.newListPiatti = newListPiatti;
    }
    
    
    

    /**
     * 
     * @return
     */
    public PiattoCombo getNuovoPiattoCombo() {
        return nuovoPiattoCombo;
    }

    /**
     * 
     * @param nuovoPiattoCombo
     */
    public void setNuovoPiattoCombo(PiattoCombo nuovoPiattoCombo) {
        this.nuovoPiattoCombo = nuovoPiattoCombo;
    }

    /**
     * 
     * @return
     */
    public List<PiattoCombo> getPiattoCombo() {
        return piattoCombo;
    }

    /**
     * 
     * @param piattoCombo
     */
    public void setPiattoCombo(List<PiattoCombo> piattoCombo) {
        this.piattoCombo = piattoCombo;
    }
    
    //</editor-fold>
}
