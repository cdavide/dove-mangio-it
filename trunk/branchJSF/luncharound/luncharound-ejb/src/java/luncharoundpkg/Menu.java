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
//necessarie
import java.util.List;
import java.util.GregorianCalendar;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;


/**
 *
 * @author Bovio Lerenzo, Bronzino Francesco, Concas Davide
 */


@Entity
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idLocale;
    @OneToMany
    private List<Piatto> listaPiatti;
    @Temporal(javax.persistence.TemporalType.DATE)
    private  Date validita;

    /**
     * 
     */
    public Menu() {
    }
    
    /**
     * 
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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
    public List<Piatto> getListaPiatti() {
        return listaPiatti;
    }

    /**
     * 
     * @param listaPiatti
     */
    public void setListaPiatti(List<Piatto> listaPiatti) {
        this.listaPiatti = listaPiatti;
    }

    /**
     * 
     * @return
     */
    public Date getValidita() {
        return validita;
    }

    /**
     * 
     * @param validita
     */
    public void setValidita(Date validita) {
        this.validita = validita;
    }

    /**
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
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
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if (this.id != other.id) {
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
        return "luncharoundpkg.Menu[ id=" + id + " ]";
    }
    
}
