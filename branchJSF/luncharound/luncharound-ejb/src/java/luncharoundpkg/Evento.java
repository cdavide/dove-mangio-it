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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int idLocale;
    private @Temporal(TemporalType.DATE) Date dataInizio;
    private @Temporal(TemporalType.DATE) Date dataFine;
    private String titolo;
    private String descr;

    public Evento() {
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getIdLocale() {
        return idLocale;
    }

    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "luncharoundpkg.Evento[ id=" + id + " ]";
    }
    
}




/*Cose da fare e come farle(più o meno uguale per le news):
 *  -creare il controlloreEvento
 *  -Fare la selezione di tutti gli eventi ordinati dal più vicino in poi(per le new ordinate dalla più nuova indietro)
 *  -Fare la selezione degli eventi di un locale ordinati dal più vicino(per le new ordinate dalla più nuova indietro)
 *  -Fare l'eventoBean per le jsf.
 *  -Capire come fare per prendere tutti quelli dei preferiti e ordinarli dal più vicino(news dal più nuovo)
 *  -Soluzione per la riga di sopra: fare una query con la lista degli id dei locali e li si ordina veloce.
 *  -Lista degli eventi è mostrata. quando si clicca sopra si apre un pop up con la descrizione dell'evento(uguale per la news)
 */