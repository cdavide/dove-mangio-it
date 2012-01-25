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
import java.util.GregorianCalendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity
public class News implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int idLocale;
    private @Temporal(TemporalType.DATE) Date dataInizio;
    private String descr;
    private String titolo;

    /**
     * 
     */
    public News() {
    }

    /**
     * 
     * @return
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * 
     * @param titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * 
     * @return
     */
    public Date getDataInizio() {
        return dataInizio;
    }
    
    /**
     * 
     * @return
     */
    public String getDataInizioShort() {
        String temp = dataInizio.toString();
        return temp.substring(0, 10);
    }

    /**
     * 
     * @param dataInizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
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
        if (!(object instanceof News)) {
            return false;
        }
        News other = (News) object;
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
        return "luncharoundpkg.News[ id=" + id + " ]";
    }
    
}
