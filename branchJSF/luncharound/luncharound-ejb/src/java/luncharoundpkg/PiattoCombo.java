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

/**
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
    //Bisogna aggiungere un campo flag???

    /**
     * 
     */
    public PiattoCombo() {
    }

    /**
     * 
     * @return
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 
     * @param descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * 
     * @return
     */
    public int getIdLocale() {
        return idLocale;
    }

    /**
     * 
     * @param idLocale
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    /**
     * 
     * @return
     */
    public float getPrezzo() {
        return prezzo;
    }

    /**
     * 
     * @param prezzo
     */
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
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
