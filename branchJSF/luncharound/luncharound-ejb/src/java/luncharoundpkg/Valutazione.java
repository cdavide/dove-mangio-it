/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** Entity EJB che mantiene le valutazioni di un locale
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity
public class Valutazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int idLocale;
    private Long idUtente;
    private @Temporal(TemporalType.DATE) Date dataVal;
    private int pulizia;
    private int qualita;
    private int velocita;
    private int affollamento;
    private int quantita;
    private int cortesia;
    private int descrizione;

    /**costruttore vuoto
     * 
     */
    public Valutazione() {
    }

    /** restituisce la valutazione su  affollamento
     * 
     * @return la valutazione su affollamento
     */
    public int getAffollamento() {
        return affollamento;
    }

    /** set della valutazione su affolamento 
     * 
     * @param affollamento la valutazione su affollamento
     */
    public void setAffollamento(int affollamento) {
        this.affollamento = affollamento;
    }

    /** get della valutazione su cortesia
     * 
     * @return la valutazione su cortesia
     */
    public int getCortesia() {
        return cortesia;
    }

    /** set della valutazione su cortesia
     * 
     * @param cortesia la valutazione su cortesia
     */
    public void setCortesia(int cortesia) {
        this.cortesia = cortesia;
    }

    /** get della data di valutazione
     * 
     * @return data di valutazione 
     */
    public Date getDataVal() {
        return dataVal;
    }

    /** set della data di valutazione
     * 
     * @param dataVal la data di valutazione
     */
    public void setDataVal(Date dataVal) {
        this.dataVal = dataVal;
    }

    /** get della descrizione
     * 
     * @return la descizione
     */
    public int getDescrizione() {
        return descrizione;
    }

    /** set della descrizione
     * 
     * @param descrizione la descrizione del locale
     */
    public void setDescrizione(int descrizione) {
        this.descrizione = descrizione;
    }

    /** get id della valutazione 
     * 
     * @return id della valutazione
     */
    public Long getId() {
        return id;
    }

    /** set id della valutazione
     * 
     * @param id della valutazione
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** get dell'id del locale
     * 
     * @return id del locale
     */
    public int getIdLocale() {
        return idLocale;
    }

    /** set dell'id locale
     * 
     * @param idLocale l'id del locale
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    /** get dell'id dell'utente
     * 
     * @return l'id dell'utente
     */
    public Long getIdUtente() {
        return idUtente;
    }

    /** set id dell'utente
     * 
     * @param idUtente id dell'utente
     */
    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    /** get della valutazione su pulizia
     * 
     * @return la valutazione su pulizia
     */
    public int getPulizia() {
        return pulizia;
    }

    /** set della valutazione su pulizia
     * 
     * @param cortesia la valutazione su pulizia
     */
    public void setPulizia(int pulizia) {
        this.pulizia = pulizia;
    }

/** get della valutazione su qualità
     * 
     * @return la valutazione su qualità
     */
    public int getQualita() {
        return qualita;
    }

/** set della valutazione su pulizia
     * 
     * @param cortesia la valutazione su pulizia
     */
    public void setQualita(int qualita) {
        this.qualita = qualita;
    }

/** get della valutazione su quantità
     * 
     * @return la valutazione su quantità
     */
    public int getQuantita() {
        return quantita;
    }

/** set della valutazione su quantità
     * 
     * @param cortesia la valutazione su quantità
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

/** get della valutazione su velocità
     * 
     * @return la valutazione su velocità
     */
    public int getVelocita() {
        return velocita;
    }

/** set della valutazione su velocità
     * 
     * @param cortesia la valutazione su velocità
     */
    public void setVelocita(int velocita) {
        this.velocita = velocita;
    }


    /** get dell'hashcode
     * 
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /** confronto tra oggetti
     * 
     * @param object l'oggetto da confrontare
     * @return true se uguale, false se diversi
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valutazione)) {
            return false;
        }
        Valutazione other = (Valutazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /** descrizione testuale della valutazione
     * 
     * @return stringa di descrizione
     */
    @Override
    public String toString() {
        return "luncharoundpkg.Valutazione[ id=" + id + " ]";
    }
    
}
