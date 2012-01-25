/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**Classe che descrive le combinazioni di piatti
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity
public class PiattoCombo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descr;
    private float prezzo;
    private int idLocale;


    /**Costruttore vuoto
     * 
     */
    public PiattoCombo() {
    }

    /**Restituisce la descrizione della combinazione piatti
     * 
     * @return la descrizione della combinazione piatti
     */
    public String getDescr() {
        return descr;
    }

    /**Imposta la descrizione di una combinazione piatti
     * 
     * @param descr una stringa contenente la descrizione della combinazione
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**Restituisce l'id del locale a cui la combinazione è associata
     * 
     * @return l'id del locale a cui la combinazione è associata
     */
    public int getIdLocale() {
        return idLocale;
    }

    /**Imposta l'id del locale a cui la combinazione è associata
     * 
     * @param idLocale l'id del locale a cui la combinazione è associata
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    /** Restituisce il prezzo della combinazione
     * 
     * @return il prezzo della combinazione
     */
    public float getPrezzo() {
        return prezzo;
    }

    /**Imposta il prezzo della combinazione
     * 
     * @param prezzo il prezzo della combinazione
     */
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    /**Restituisce l'id della combinazione
     * 
     * @return l'id della combinazione
     */
    public Long getId() {
        return id;
    }

    /**Imposta l'id di una combinazione
     * 
     * @param id l'id della combinazione
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**Restituisce l'hashcode dell'oggetto
     * 
     * @return l'hashcode dell'oggetto
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**Confronta un oggetto di tipo piattocombo
     * 
     * @param object l'oggetto con cui effettuare il confronto
     * @return true se gli oggetti sono uguali o false altrimenti
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiattoCombo)) {
            return false;
        }
        PiattoCombo other = (PiattoCombo) object;
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
        return "luncharoundpkg.PiattoCombo[ id=" + id + " ]";
    }
    
}
