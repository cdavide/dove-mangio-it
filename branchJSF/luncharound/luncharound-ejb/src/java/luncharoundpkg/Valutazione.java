/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    private @Temporal(TemporalType.DATE) Date dataVal;
    private int pulizia;
    private int qualita;
    private int velocita;
    private int affollamento;
    private int quantita;
    private int cortesia;
    private int descrizione;

    public Valutazione() {
    }

    public int getAffollamento() {
        return affollamento;
    }

    public void setAffollamento(int affollamento) {
        this.affollamento = affollamento;
    }

    public int getCortesia() {
        return cortesia;
    }

    public void setCortesia(int cortesia) {
        this.cortesia = cortesia;
    }

    public Date getDataVal() {
        return dataVal;
    }

    public void setDataVal(Date dataVal) {
        this.dataVal = dataVal;
    }

    public int getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(int descrizione) {
        this.descrizione = descrizione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPulizia() {
        return pulizia;
    }

    public void setPulizia(int pulizia) {
        this.pulizia = pulizia;
    }

    public int getQualita() {
        return qualita;
    }

    public void setQualita(int qualita) {
        this.qualita = qualita;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getVelocita() {
        return velocita;
    }

    public void setVelocita(int velocita) {
        this.velocita = velocita;
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
