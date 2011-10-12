/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

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
    public String mostraValutazioni(long idLocale,long idUtente) {
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
        List<Valutazione>   vals =   valutazioneFacade.findByLocale(idLocale);
        int numValutazioni = vals.size();
        //if(numValutazioni == 0) return "</br>Non ci sono valutazioni</br>";
        for(Valutazione val : vals){
            pulizia+=(long) val.getAffollamento();
            qualita+=(long) val.getQualita();
            velocita+=(long) val.getVelocita();
            affollamento+=(long) val.getAffollamento();
            quantita+=(long) val.getQuantita();
            cortesia+=(long) val.getCortesia();
            if(val.getIdUtente() == idUtente){
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
        return "</br>Valutazioni:"
                + "</br>"+createRatingStars("pulizia", pulizia,"disabled=\"disabled\"")+pulizia
                + "</br>"+createRatingStars("qualita", qualita,"disabled=\"disabled\"")+qualita
                + "</br>"+createRatingStars("velocita", velocita,"disabled=\"disabled\"")+velocita
                + "</br>"+createRatingStars("affollamento", affollamento,"disabled=\"disabled\"")+affollamento
                + "</br>"+createRatingStars("quantita", quantita,"disabled=\"disabled\"")+quantita
                + "</br>"+createRatingStars("cortesia", cortesia,"disabled=\"disabled\"")+cortesia
             +"</br>Le tue valutazioni: "
                +"<form name=\"valForm\" id=\"val\" method=\"POST\">"
                + "</br>"+createRatingStars("mypulizia", myPulizia,"")+myPulizia
                + "</br>"+createRatingStars("myqualita", myQualita,"")+myQualita
                + "</br>"+createRatingStars("myvelocita", myVelocita,"")+myVelocita
                + "</br>"+createRatingStars("myaffollamento", myAffollamento,"")+myAffollamento
                + "</br>"+createRatingStars("myquantita", myQuantita,"")+myQuantita
                + "</br>"+createRatingStars("mycortesia", myCortesia,"")+myCortesia
                
                +"<input type=\"hidden\" id=\"azione\" name=\"azione \" value=\"aggiungi_valutazione\">"
                +"<input type=\"hidden\" id=\"pul\" name=\"pul\">"
                +"<input type=\"hidden\" id=\"qual\" name=\"qual\">"
                +"<input type=\"hidden\" id=\"vel\" name=\"vel\">"
                +"<input type=\"hidden\" id=\"aff\" name=\"aff\">"
                +"<input type=\"hidden\" id=\"quant\" name=\"quant\">"
                +"<input type=\"hidden\" id=\"cor\" name=\"cor\">"
                + "</br><input type=\"button\" value=\"Submit\" onClick=\"submitValutazione()\"/>"
                +"</form>";
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
            ret +="<input "+opt+" id=\""+par+"\" name=\""+par+"\" type=\"radio\" class=\"star\"/>";
            return ret;
        }
        int i=1;
        for(; i<val; i++)
            ret +="<input "+opt+" id=\""+par+"\" name=\""+par+"\" type=\"radio\" class=\"star\"/>";
        ret +="<input "+opt+" id=\""+par+"\" name=\""+par+"\" type=\"radio\" class=\"star\" checked=\"checked\"/>";
        for(;i<5;i++)
            ret +="<input " +opt+" id=\""+par+"\" name=\""+par+"\" type=\"radio\" class=\"star\"/>";
        return ret;
    }
        
    @Override
    public void valutazioneDaReq(HttpServletRequest req){
        Valutazione val= new Valutazione();
        Util.riempi(req,val);
        valutazioneFacade.create(val);
    }
    
}
