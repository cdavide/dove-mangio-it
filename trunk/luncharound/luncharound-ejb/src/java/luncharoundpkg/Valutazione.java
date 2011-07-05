/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity
public class Valutazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int idLocale;
    private Long idUtente;
    private GregorianCalendar dataVal;
    private byte pulizia;
    private byte qualita;
    private byte velocita;
    private byte affollamento;
    private byte quantità;
    private byte cortesia;
    private byte description;

    public Valutazione() {
    }

    public Valutazione(int idLocale, Long idUtente, GregorianCalendar dataVal, byte pulizia, byte qualita, byte velocita, byte affollamento, byte quantità, byte cortesia, byte description) {
        this.idLocale = idLocale;
        this.idUtente = idUtente;
        this.dataVal = dataVal;
        this.pulizia = pulizia;
        this.qualita = qualita;
        this.velocita = velocita;
        this.affollamento = affollamento;
        this.quantità = quantità;
        this.cortesia = cortesia;
        this.description = description;
    }

    public byte getAffollamento() {
        return affollamento;
    }

    public void setAffollamento(byte affollamento) {
        this.affollamento = affollamento;
    }

    public byte getCortesia() {
        return cortesia;
    }

    public void setCortesia(byte cortesia) {
        this.cortesia = cortesia;
    }

    public GregorianCalendar getDataVal() {
        return dataVal;
    }

    public void setDataVal(GregorianCalendar dataVal) {
        this.dataVal = dataVal;
    }

    public byte getDescription() {
        return description;
    }

    public void setDescription(byte description) {
        this.description = description;
    }

    public int getIdLocale() {
        return idLocale;
    }

    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public byte getPulizia() {
        return pulizia;
    }

    public void setPulizia(byte pulizia) {
        this.pulizia = pulizia;
    }

    public byte getQualita() {
        return qualita;
    }

    public void setQualita(byte qualita) {
        this.qualita = qualita;
    }

    public byte getQuantità() {
        return quantità;
    }

    public void setQuantità(byte quantità) {
        this.quantità = quantità;
    }

    public byte getVelocita() {
        return velocita;
    }

    public void setVelocita(byte velocita) {
        this.velocita = velocita;
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
        if (!(object instanceof Valutazione)) {
            return false;
        }
        Valutazione other = (Valutazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "luncharoundpkg.Valutazione[ id=" + id + " ]";
    }
    
}
