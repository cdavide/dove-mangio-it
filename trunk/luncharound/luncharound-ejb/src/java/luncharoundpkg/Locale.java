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
public class Locale implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nome;
    //private String foto;  Come salviamo la foto???
    private String indirizzo;
    private float longitudine;
    private float latitudine;
    private String proprietario;
    private String pIVA;
    //Menù e combo piatti non sono da mettere perchè si ricavano dagli ID

    public Locale() {
    }

    public Locale(String nome, String indirizzo, float longitudine, float latitudine, String proprietario, String pIVA) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
        this.proprietario = proprietario;
        this.pIVA = pIVA;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(float latitudine) {
        this.latitudine = latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(float longitudine) {
        this.longitudine = longitudine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getpIVA() {
        return pIVA;
    }

    public void setpIVA(String pIVA) {
        this.pIVA = pIVA;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (!(object instanceof Locale)) {
            return false;
        }
        Locale other = (Locale) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "luncharoundpkg.Locale[ id=" + id + " ]";
    }
    
}
