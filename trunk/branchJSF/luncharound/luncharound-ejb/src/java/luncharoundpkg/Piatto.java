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

/** Entity EJB che descrive la singola pietanza
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */

@Entity
public class Piatto implements Serializable {
    

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private Categoria categoria;
    private float prezzo;
    private boolean corrente;
    private boolean carne;
    private boolean pesce;
    private boolean vegetariano;
    private boolean vegano;
    private boolean celiaco;
    private boolean alcolico;
    private int idLocale; 
    
    /** Costruttore vuoto
     * 
     */
    public Piatto() {
    }

    /** set dell'id del locale di appartenza
     * 
     * @param idLocale l'id del locale
     */
    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    
    /** restutisce l'id del piatto
     * 
     * @return id del piatto
     */
    public Long getId() {
        return id;
    }

    /** set dell'id del locale
     * 
     * @param id l'id del locale
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** restituisce l'hashcode
     * 
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
 
    /** confronta oggetti di tipo Piatto
     * 
     * @param object il piatto da confrontare
     * @return true se uguali, false se diversi
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Piatto)) {
            return false;
        }
        Piatto other = (Piatto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /** la descrizione del piatto in forma testuale
     * 
     * @return stringa contenente la descrizione del piatto
     */
    @Override
    public String toString() {
        return "luncharoundpkg.Piatto[ id=" + id + " ]";
    }

    /** restituisce la categoria del piatto
     * 
     * @return la categoria 
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /** set della categoria
     * 
     * @param categoria
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /** indica se il piatto è attualmente esposto 
     * 
     * @return true se piatto corrente, false se non utilizzato
     */
    public boolean isCorrente() {
        return corrente;
    }

    /** set se il piatto è attualmente esposto oppure no 
     * 
     * @param corrente booleano che indica se espososto
     */
    public void setCorrente(boolean corrente) {
        this.corrente = corrente;
    }

    /**@deprecated 
     * 
     * @return
     */
    public boolean isAlcolico() {
        return alcolico;
    }

    /**@deprecated 
     * 
     * @param alcolico
     */
    public void setAlcolico(boolean alcolico) {
        this.alcolico = alcolico;
    }

    /** se il piatto contiene carne
     * 
     * @return se contiene carne
     */
    public boolean isCarne() {
        return carne;
    }

    /** imposta se il piatto contiene
     * 
     * @param carne se contiene carne
     */
    public void setCarne(boolean carne) {
        this.carne = carne;
    }

    /** se il piatto è adatto per celiaci
     * 
     * @return se adatto a celiai
     */
    public boolean isCeliaco() {
        return celiaco;
    }

    /** imposta se adatto  celiaci
     * 
     * @param celiaco se adatto a celiaci
     */
    public void setCeliaco(boolean celiaco) {
        this.celiaco = celiaco;
    }

    /** se contiene pesce
     * 
     * @return se contiene pesce
     */
    public boolean isPesce() {
        return pesce;
    }

    /** imposta se contiene pesce
     * 
     * @param pesce se contiene pesce
     */
    public void setPesce(boolean pesce) {
        this.pesce = pesce;
    }

    /** se adatto a vegani
     * 
     * @return se adatto a vegani
     */
    public boolean isVegano() {
        return vegano;
    }

    /** set se adatto a vegani
     * 
     * @param vegano se adatto a vegani
     */
    public void setVegano(boolean vegano) {
        this.vegano = vegano;
    }

    /** se adatto a vegetariani
     * 
     * @return se adatto a vegetariani
     */
    public boolean isVegetariano() {
        return vegetariano;
    }

    /** set se adatto a vegetariani
     * 
     * @param vegetariano se adatto a vegani
     */
    public void setVegetariano(boolean vegetariano) {
        this.vegetariano = vegetariano;
    }



    /** restituisce il nome
     * 
     * @return il nome del piatto
     */
    public String getNome() {
        return nome;
    }

    /** set del nome del piatto
     * 
     * @param nome il nome del piatto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** get del prezzo
     * 
     * @return il prezzo del piatto
     */
    public float getPrezzo() {
        return prezzo;
    }

    /** set prezzo del piatto
     * 
     * @param prezzo del piatto
     */
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
    
    
}
