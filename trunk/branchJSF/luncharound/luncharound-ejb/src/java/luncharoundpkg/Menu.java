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


/**Entity EJB che mantiene le informazioni relative al menù del giorno
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

    /** costruttore vuoto della classe menù
     * 
     */
    public Menu() {
    }
    
    /** restituisce l'id del menù
     * 
     * @return id del menù
     */
    public int getId() {
        return id;
    }

    /** set dell'id del menù
     * 
     * @param id l'id della news
     */
    public void setId(int id) {
        this.id = id;
    }

    /** restituisce l'id del locale
     * 
     * @return id del locale
     */
    public int getIdLocale() {
        return idLocale;
    }
 
    /** set dell'id del locale
     * 
     * @param idLocale l'id del locale
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    /** restituisce un ArrayList contenente oggetti Piatto, il menù del giorno
     * 
     * @return la lista di piatti
     */
    public List<Piatto> getListaPiatti() {
        return listaPiatti;
    }

    /** set della lista di piatti, deve essere un ArrayList
     * 
     * @param listaPiatti la lista dei piatti del menù del giorno
     */
    public void setListaPiatti(List<Piatto> listaPiatti) {
        this.listaPiatti = listaPiatti;
    }

    /** restituise la data oltre la quale il menù non è più valido
     * 
     * @return la data di validità
     */
    public Date getValidita() {
        return validita;
    }

    /** set della data di validità
     * 
     * @param validita la data di validità
     */
    public void setValidita(Date validita) {
        this.validita = validita;
    }

    /** restituisce l'hashcode
     * 
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    /** confronta due istanze di menù
     * 
     * @param object l'oggetto da confrontare
     * @return true se uguali, false se diversi
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

    /** descrizione testuale dell'oggetto
     * 
     * @return una descrizione dell'oggetto menù
     */
    @Override
    public String toString() {
        return "luncharoundpkg.Menu[ id=" + id + " ]";
    }
    
}
