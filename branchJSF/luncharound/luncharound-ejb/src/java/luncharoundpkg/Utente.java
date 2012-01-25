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


/**
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
     * 
     * @return
     */
    public boolean isEventi() {
        return eventi;
    }

    /**
     * 
     * @param eventi
     */
    public void setEventi(boolean eventi) {
        this.eventi = eventi;
    }

    /**
     * 
     * @return
     */
    public String getFoto() {
        return foto;
    }

    /**
     * 
     * @param foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * 
     * @return
     */
    public String getHome() {
        return home;
    }

    /**
     * 
     * @param home
     */
    public void setHome(String home) {
        this.home = home;
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
    public String getMail() {
        return mail;
    }

    /**
     * 
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 
     * @return
     */
    public boolean isNews() {
        return news;
    }

    /**
     * 
     * @param news
     */
    public void setNews(boolean news) {
        this.news = news;
    }

    /**
     * 
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     */
    public List<Locale> getPreferiti() {
        return preferiti;
    }

    /**
     * 
     * @param preferiti
     */
    public void setPreferiti(List<Locale> preferiti) {
        this.preferiti = preferiti;
    }

    /**
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * 
     * @param tipo
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
