/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**Entity EJB rappresentante un utente
 *
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
@Entity
@Table(name="tblUtente",uniqueConstraints ={@UniqueConstraint(columnNames={"mail"})})
public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @Column(nullable=false)
    private String mail;
    private String home;
    private String password;
    @OneToMany
    private List<Locale> preferiti;
    private boolean news;
    private boolean eventi;
    private String foto;
    //tipo di utente: 1 nostro, 2 facebook, 3 twitter, 4 google
    private int tipo;


    /**
     * 
     */
    public Utente() {
    }

    /**
     * @deprecated 
     * @return
     */
    public boolean isEventi() {
        return eventi;
    }

    /**
     * @deprecated 
     * @param eventi
     */
    public void setEventi(boolean eventi) {
        this.eventi = eventi;
    }

    /**Ritorna l'url della foto
     * 
     * @return l'url
     */
    public String getFoto() {
        return foto;
    }

    /**Imposta la foto
     * 
     * @param foto foto in formato url
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**Ritorna l'indirizzo di un utente
     * 
     * @return l'indirizzo
     */
    public String getHome() {
        return home;
    }

    /**Imposta l'indirizzo
     * 
     * @param home l'indirizzo
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**Imposta l'id dell'utente
     * 
     * @return l'id da impostare
     */
    public Long getId() {
        return id;
    }

    /**Imposta l'id
     * 
     * @param id l'id da impostare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**Ritorna l'email
     * 
     * @return l'email
     */
    public String getMail() {
        return mail;
    }

    /**Imposta l'email
     * 
     * @param mail l'eamil
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @deprecated
     * @return
     */
    public boolean isNews() {
        return news;
    }

    /**
     * @deprecated 
     * @param news
     */
    public void setNews(boolean news) {
        this.news = news;
    }

    /**Ritorna la password dell'utente
     * 
     * @return la password
     */
    public String getPassword() {
        return password;
    }

    /**Imposta la password
     * 
     * @param password la password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @deprecated 
     * @return
     */
    public List<Locale> getPreferiti() {
        return preferiti;
    }

    /**
     * @deprecated 
     * @param preferiti
     */
    public void setPreferiti(List<Locale> preferiti) {
        this.preferiti = preferiti;
    }

    /**Ritorna lo username
     * 
     * @return lo username
     */
    public String getUsername() {
        return username;
    }

    /**Imposta lo username
     * 
     * @param username lo username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**Ritorna il tipo dell'utente
     * 
     * @return 1 nostro, 2 facebook, 3 twitter, 4 google
     */
    public int getTipo() {
        return tipo;
    }

    /**Imposta il tipo dell'utente
     * 
     * @param tipo 1 nostro, 2 facebook, 3 twitter, 4 google
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Utente)) {
            return false;
        }
        Utente other = (Utente) object;
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
        return "luncharoundpkg.Utente[ id=" + id + " ]";
    }
}
