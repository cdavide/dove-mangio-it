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
public class Piatto implements Serializable {
    

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private Categoria categoria;
    private float prezzo;
    private boolean corrente;
    private byte flags; //maschera di bit,CARNE,PESCE,VEGETARIANO,VEGANO,CELIACO,ALCOLICO
    private int idLocale; 
    
    public Piatto() {
    }

    
    public Piatto(String nome, Categoria categoria, float prezzo, boolean corrente, byte flags) {
        this.nome = nome;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.corrente = corrente;
        this.flags = flags;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "luncharoundpkg.Piatto[ id=" + id + " ]";
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isCorrente() {
        return corrente;
    }

    public void setCorrente(boolean corrente) {
        this.corrente = corrente;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
    
}
