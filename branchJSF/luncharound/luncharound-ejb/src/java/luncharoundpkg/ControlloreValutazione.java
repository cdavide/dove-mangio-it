/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import luncharoundpkg.Locale;
import luncharoundpkg.Valutazione;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Stateless
public class ControlloreValutazione implements ControlloreValutazioneLocal {
    @EJB
    private ValutazioneFacadeLocal valutazioneFacade;

     /*Mostra valutazione locale
     * @param idLocale  l'identificativo unico del locale
     * @param idLocale  l'identificativo unico dell'utente
     * @return  una stringa contenente le valutazioni
     */
    
    @Override
    public String mostraValutazioni(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int idLocale = (Integer) session.getAttribute("idlocale");
        long idUtente;
        try{
         idUtente = (Long) session.getAttribute("idUtente");
        }
        catch(NullPointerException e){
            System.err.println("Il parametro id utente è nullo!");
            idUtente = -1;
        }
        Boolean mioLocale = false;
        
        long pulizia = 0;
        long qualita = 0;
        long velocita = 0;
        long affollamento = 0;
        long quantita = 0;
        long cortesia = 0;
        long myPulizia = 0;
        long myQualita = 0;
        long myVelocita = 0;
        long myAffollamento = 0;
        long myQuantita = 0;
        long myCortesia = 0;
        List<Locale> ll = (List<Locale>) session.getAttribute("localipersonali");
        Boolean valido = false;
        if(ll != null){
            for(Locale loc : ll){
                if(loc.getId() == idLocale) mioLocale = true;
            }
        }
        String ret="";
        List<Valutazione>   vals =   valutazioneFacade.findByLocale(idLocale);
        int numValutazioni = vals.size();
        //if(numValutazioni == 0) return "</br>Non ci sono valutazioni</br>";
        for(Valutazione val : vals){
            pulizia+=(long) val.getPulizia();
            qualita+=(long) val.getQualita();
            velocita+=(long) val.getVelocita();
            affollamento+=(long) val.getAffollamento();
            quantita+=(long) val.getQuantita();
            cortesia+=(long) val.getCortesia();
            if((idUtente > 0 && val.getIdUtente() == idUtente)){
                myPulizia = val.getPulizia();
                myQualita = val.getQualita();
                myVelocita = val.getVelocita();
                myAffollamento = val.getAffollamento();
                myQuantita = val.getQuantita();
                myCortesia = val.getCortesia();
            }
        }
        if(numValutazioni > 0){
            pulizia= pulizia/numValutazioni;
            qualita = qualita / numValutazioni;
            velocita = velocita / numValutazioni;
            affollamento = affollamento / numValutazioni;
            quantita = quantita / numValutazioni;
            cortesia = cortesia / numValutazioni;
        }
        ret += "</br>Media valutazioni ("+ numValutazioni+ "):"
                + "</br>Pulizia: "+createRatingStars("pul", pulizia,"disabled=\"disabled\"")
                + "</br>Qualita: " +createRatingStars("qual", qualita,"disabled=\"disabled\"")
                + "</br>Velocita: "+createRatingStars("vel", velocita,"disabled=\"disabled\"")
                + "</br>Affollamento: "+createRatingStars("aff", affollamento,"disabled=\"disabled\"")
                + "</br>Quantita: "+createRatingStars("quant", quantita,"disabled=\"disabled\"")
                + "</br>Cortesia: "+createRatingStars("cor", cortesia,"disabled=\"disabled\"");
                
        if (idUtente > 0 && !mioLocale){
            ret+="</br>Le tue valutazioni: "
                +"<form name=\"valForm\" id=\"val\"action=\"LocaliServlet\" method=\"POST\">"
                + "</br>Pulizia: "+createRatingStars("mypulizia", myPulizia,"")
                + "</br>Qualita: "+createRatingStars("myqualita", myQualita,"")
                + "</br>Velocita: "+createRatingStars("myvelocita", myVelocita,"")
                + "</br>Affollamento: "+createRatingStars("myaffollamento", myAffollamento,"")
                + "</br>Quantita: "+createRatingStars("myquantita", myQuantita,"")
                + "</br>Cortesia: "+createRatingStars("mycortesia", myCortesia,"")
                
                +"<input type=\"hidden\" id=\"azione\" name=\"azione\" value=\"aggiungi_valutazione\">"
                +"<input type=\"hidden\" id=\"pulizia\" name=\"pulizia\">"
                +"<input type=\"hidden\" id=\"qualita\" name=\"qualita\">"
                +"<input type=\"hidden\" id=\"velocita\" name=\"velocita\">"
                +"<input type=\"hidden\" id=\"affollamento\" name=\"affollamento\">"
                +"<input type=\"hidden\" id=\"quantita\" name=\"quantita\">"
                +"<input type=\"hidden\" id=\"cortesia\" name=\"cortesia\">"
                + "</br><input type=\"button\" value=\"Submit\" onClick=\"submitValutazione()\"/>"
                +"</form>";
        }
        if (mioLocale) {
            ret+= "</br><a href=\"Statistiche\">Visualizza Statistiche locale</a>";
            
        }
        return ret;
    }
    
    /* Metodo di appoggio che crea il form per visualizzare il rating
     * @param par il parametro di cui si vuole ottenere il form del rating
     * @param val il valore medio del rating  del parametro di valutazione
     * @param opt opzioni varie disabled ecc, guardare help jquery rating plugin
     * @comment non è molto bello ma funziona :-p
     */
    
    @Override
    public String createRatingStars(String par, long val,String opt){
        String ret = ""; 
        if (val == 0) {
            for(int j=0;j<5;j++)
            ret +="<input "+opt+" name=\""+par+"\" type=\"radio\" class=\"star\"/>";
            return ret;
        }
        int i=1;
        for(; i<val; i++)
            ret +="<input "+opt+"  name=\""+par+"\" type=\"radio\" class=\"star\"/>";
        ret +="<input "+opt+"  name=\""+par+"\" type=\"radio\" class=\"star\" checked=\"checked\"/>";
        for(;i<5;i++)
            ret +="<input " +opt+" name=\""+par+"\" type=\"radio\" class=\"star\"/>";
        return ret;
    }
        
    @Override
    public void valutazioneDaReq(HttpServletRequest req){
        /*get valutazione precedente
         update valutazione */
        HttpSession sessione = req.getSession();
        Long idUtente = (Long) sessione.getAttribute("idUtente");
        int idLocale = (Integer) sessione.getAttribute("idlocale");
        Valutazione val= valutazioneFacade.findValutazioneLocFromUtente(idLocale, idUtente);
        if(val == null) val = new Valutazione();
        Util.riempi(req,val);
        val.setIdUtente(idUtente);
        val.setIdLocale(idLocale); 
        val.setDataVal(new Date());
        valutazioneFacade.create(val);

    }
    @Override
    public List<Valutazione> valutazioniSettimana(int idlocale, int week){
        List<Valutazione> ll = valutazioneFacade.findByLocaleWeek(idlocale, week);
        return ll;
    }

}
