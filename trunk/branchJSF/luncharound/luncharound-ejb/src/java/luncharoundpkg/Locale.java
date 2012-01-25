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
import javax.persistence.OneToOne;

/**Entity EJB che descrive il locale
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity

public class Locale implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long idUtente; // l'id del proprietario del locale
    private String nome;
    //private String foto;  Come salviamo la foto???
    private String indirizzo;
    private double longitudine;
    private double latitudine;
    private String proprietario;
    private String descrizione;
    private String pIVA;
    //Menù e combo piatti non sono da mettere perchè si ricavano dagli ID

    /**
     * 
     */
    public Locale() {
    }

    /** get della descrizione del locale
     * 
     * @return la descrizione del locale
     */
    public String getDescrizione() {
        return descrizione;
    }

    /** set della descrizione nel locale
     * 
     * @param descrizione la descrizione del locale
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    
    /** set dell'id del proprietario del locale
     * 
     * @param idUtente l'id dell'utente
     */
    public void setIdUtente(long idUtente){
        this.idUtente = idUtente;
    }
    
    /** get del proprietario del locale
     * 
     * @return il proprietario del locale
     */
    public long getIdUtente(){
        return this.idUtente;
    }
    
    
    /** get dell'indirizzo
     * 
     * @return l'indirizzo del locale
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**set dell'indirizzo del locale
     * 
     * @param indirizzo l'indirizzo del locale
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /** det della latitudine del locale
     * 
     * @return latitudine del locale
     */
    public double getLatitudine() {
        return latitudine;
    }

    /** se latitudine del locale
     * 
     * @param latitudine la latitudine del locale
     */
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    /** get longitudine del locale
     * 
     * @return longitudine del locale
     */
    public double getLongitudine() {
        return longitudine;
    }

    /** set longitudine del locale
     * 
     * @param longitudine la longitudine del locale
     */
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    /** get del nome del locale
     * 
     * @return il nome del locale
     */
    public String getNome() {
        return nome;
    }

    /** set del nome del locale
     * 
     * @param nome il nome del locale
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** get partita iva
     * 
     * @return parita iva
     */
    public String getpIVA() {
        return pIVA;
    }

    /** set paritita iva
     * 
     * @param pIVA la partita iva
     */
    public void setpIVA(String pIVA) {
        this.pIVA = pIVA;
    }

    /** il nome del proprietario del locale
     * 
     * @return il nome del proprietario
     */
    public String getProprietario() {
        return proprietario;
    }

    /** set del nome del proprietario del locale
     * 
     * @param proprietario il nome del propietario
     */
    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    /** get dell'id del locale
     * 
     * @return id del locale
     */
    public int getId() {
        return id;
    }

    /** set dell'id del locale
     * 
     * @param id l'id del locale
     */
    public void setId(int id) {
        this.id = id;
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

    /** confronta due oggetti di tipo locale
     * 
     * @param object l'oggetto da confrontare
     * @return true se uguali, false se diversi
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locale)) {
            return false;
        }
        Locale other = (Locale) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /** Restituisce una descrizione del locale sottoforma di stringa
     * 
     * @return la stringa con informzioni sul locale
     */
    @Override
    public String toString() {
        return "luncharoundpkg.Locale[ id=" + id + " ]";
    }
    
}
