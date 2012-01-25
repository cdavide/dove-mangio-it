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

/**
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

    /**
     * 
     * @return
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * 
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    
    /**
     * 
     * @param idUtente
     */
    public void setIdUtente(long idUtente){
        this.idUtente = idUtente;
    }
    
    /**
     * 
     * @return
     */
    public long getIdUtente(){
        return this.idUtente;
    }
    
    
    /**
     * 
     * @return
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * 
     * @param indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * 
     * @return
     */
    public double getLatitudine() {
        return latitudine;
    }

    /**
     * 
     * @param latitudine
     */
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    /**
     * 
     * @return
     */
    public double getLongitudine() {
        return longitudine;
    }

    /**
     * 
     * @param longitudine
     */
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    /**
     * 
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * 
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * 
     * @return
     */
    public String getpIVA() {
        return pIVA;
    }

    /**
     * 
     * @param pIVA
     */
    public void setpIVA(String pIVA) {
        this.pIVA = pIVA;
    }

    /**
     * 
     * @return
     */
    public String getProprietario() {
        return proprietario;
    }

    /**
     * 
     * @param proprietario
     */
    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
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
        if (!(object instanceof Locale)) {
            return false;
        }
        Locale other = (Locale) object;
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
        return "luncharoundpkg.Locale[ id=" + id + " ]";
    }
    
}
