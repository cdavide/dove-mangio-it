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

/** Entity EBJ che descrive le news del locale, quindi notizie con 
 *  interesse duratura nel tempo
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

    /** costruttore vuoto della classe News
     * 
     */
    public News() {
    }

    /** restituisce il titolo della news
     * 
     * @return il titolo della news
     */
    public String getTitolo() {
        return titolo;
    }

    /** set del titolo della news
     * 
     * @param titolo il titolo della news
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /** restitisce il giorno in cui è stata resa nota la news 
     * 
     * @return data di inizio della news
     */
    public Date getDataInizio() {
        return dataInizio;
    }
    
    /** restituisce una stringa formatta DD-MM-YY con la data di inizio della news
     * 
     * @return la stringa contenete la data di inizio
     */
    public String getDataInizioShort() {
        String temp = dataInizio.toString();
        return temp.substring(0, 10);
    }

    /** set della data di inizio della news
     * 
     * @param dataInizio la data di inizio validità della news
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /** restituisce la descrizione testuale della news
     * 
     * @return la descrizione della news
     */
    public String getDescr() {
        return descr;
    }

    /** set della descrizione della news
     * 
     * @param descr la descrizione della news
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /** restituisce l'id del locale a cui la news appartiene
     * 
     * @return l'ide del locale di appartenenza
     */
    public int getIdLocale() {
        return idLocale;
    }

    /** set dell'id del locale di appartenenza
     * 
     * @param idLocale l'id del locale
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }
    
    /** restutisce l'id della news
     * 
     * @return l'id del locale
     */
    public Long getId() {
        return id;
    }

    /** set dell'id del locale
     * 
     * @param id l'id della news
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** restituisce l'hashcode
     * 
     * @return hashcode della news
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**confronta due oggetti di tipo news
     * 
     * @param object l'oggetto da confrontare
     * @return true se l'oggetto è uguale, false se l'oggetto è diverso
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

    /** descrizione testuale della news
     * 
     * @return la descrizione dell'oggetto news
     */
    @Override
    public String toString() {
        return "luncharoundpkg.News[ id=" + id + " ]";
    }
    
}
