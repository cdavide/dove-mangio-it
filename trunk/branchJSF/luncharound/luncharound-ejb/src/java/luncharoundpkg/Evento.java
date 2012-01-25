/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**Entity EJB che definisce gli eventi
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int idLocale;
    private @Temporal(TemporalType.DATE) Date dataInizio;
    private @Temporal(TemporalType.DATE) Date dataFine;
    private String titolo;
    private String descr;

    /**
     * 
     */
    public Evento() {
    }

    /**Get titolo
     * 
     * @return titolo dell'evento
     */
    public String getTitolo() {
        return titolo;
    }

    /**Set titolo
     * 
     * @param titolo imposto il titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**Get la data finale
     * 
     * @return la data
     */
    public Date getDataFine() {
        return dataFine;
    }
    
    /**Get una stringa semplificata rappresentante la data finale dell'evento
     * 
     * @return La stringa della data
     */
    public String getDataFineShort() {
        String temp = dataFine.toString();
        return temp.substring(0, 10);
    }

    /**Imposta la data finale di un evento
     * 
     * @param dataFine la data da impostare
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**Get la data iniziale di un evento
     * 
     * @return la data iniziale
     */
    public Date getDataInizio() {
        return dataInizio;
    }
    
    /**Get una stringa semplificata rappresentante la data iniziale dell'evento
     * 
     * @return la stringa rappresentante la data
     */
    public String getDataInizioShort() {
        String temp = dataInizio.toString();
        return temp.substring(0, 10);
    }

    /**Imposta la data iniziale
     * 
     * @param dataInizio La data da impostare
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**Get la descrizione
     * 
     * @return la descrizione
     */
    public String getDescr() {
        return descr;
    }

    /**Imposta la descrizione
     * 
     * @param descr La descrizione da impostare
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**Get l'id del locale associato
     * 
     * @return l'id
     */
    public int getIdLocale() {
        return idLocale;
    }

    /**Imposta l'id del locale
     * 
     * @param idLocale
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    /**Get l'id dell'evento
     * 
     * @return l'id
     */
    public Long getId() {
        return id;
    }

    /**Set l'id dell'evento
     * 
     * @param id L'id da impostare
     */
    public void setId(Long id) {
        this.id = id;
    }
    

    /**
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return "luncharoundpkg.Evento[ id=" + id + " ]";
    }
    
}