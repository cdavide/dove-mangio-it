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

    public Menu() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLocale() {
        return idLocale;
    }

    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    public List<Piatto> getListaPiatti() {
        return listaPiatti;
    }

    public void setListaPiatti(List<Piatto> listaPiatti) {
        this.listaPiatti = listaPiatti;
    }

    public Date getValidita() {
        return validita;
    }

    public void setValidita(Date validita) {
        this.validita = validita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

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

    @Override
    public String toString() {
        return "luncharoundpkg.Menu[ id=" + id + " ]";
    }
    
}
