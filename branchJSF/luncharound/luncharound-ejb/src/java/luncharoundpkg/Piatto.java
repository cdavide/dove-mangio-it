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
    private boolean carne;
    private boolean pesce;
    private boolean vegetariano;
    private boolean vegano;
    private boolean celiaco;
    private boolean alcolico;
    private int idLocale; 
    
    /**
     * 
     */
    public Piatto() {
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
        if (!(object instanceof Piatto)) {
            return false;
        }
        Piatto other = (Piatto) object;
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
        return "luncharoundpkg.Piatto[ id=" + id + " ]";
    }

    /**
     * 
     * @return
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * 
     * @param categoria
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * 
     * @return
     */
    public boolean isCorrente() {
        return corrente;
    }

    /**
     * 
     * @param corrente
     */
    public void setCorrente(boolean corrente) {
        this.corrente = corrente;
    }

    /**
     * 
     * @return
     */
    public boolean isAlcolico() {
        return alcolico;
    }

    /**
     * 
     * @param alcolico
     */
    public void setAlcolico(boolean alcolico) {
        this.alcolico = alcolico;
    }

    /**
     * 
     * @return
     */
    public boolean isCarne() {
        return carne;
    }

    /**
     * 
     * @param carne
     */
    public void setCarne(boolean carne) {
        this.carne = carne;
    }

    /**
     * 
     * @return
     */
    public boolean isCeliaco() {
        return celiaco;
    }

    /**
     * 
     * @param celiaco
     */
    public void setCeliaco(boolean celiaco) {
        this.celiaco = celiaco;
    }

    /**
     * 
     * @return
     */
    public boolean isPesce() {
        return pesce;
    }

    /**
     * 
     * @param pesce
     */
    public void setPesce(boolean pesce) {
        this.pesce = pesce;
    }

    /**
     * 
     * @return
     */
    public boolean isVegano() {
        return vegano;
    }

    /**
     * 
     * @param vegano
     */
    public void setVegano(boolean vegano) {
        this.vegano = vegano;
    }

    /**
     * 
     * @return
     */
    public boolean isVegetariano() {
        return vegetariano;
    }

    /**
     * 
     * @param vegetariano
     */
    public void setVegetariano(boolean vegetariano) {
        this.vegetariano = vegetariano;
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
    public float getPrezzo() {
        return prezzo;
    }

    /**
     * 
     * @param prezzo
     */
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
    
    
}
