package luncharoundpkg;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import luncharoundpkg.Locale;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-01-12T17:32:05")
@StaticMetamodel(Utente.class)
public class Utente_ { 

    public static volatile SingularAttribute<Utente, Long> id;
    public static volatile SingularAttribute<Utente, String> mail;
    public static volatile SingularAttribute<Utente, String> home;
    public static volatile SingularAttribute<Utente, Boolean> eventi;
    public static volatile SingularAttribute<Utente, String> username;
    public static volatile SingularAttribute<Utente, Boolean> news;
    public static volatile SingularAttribute<Utente, Integer> tipo;
    public static volatile SingularAttribute<Utente, String> foto;
    public static volatile SingularAttribute<Utente, String> password;
    public static volatile ListAttribute<Utente, Locale> preferiti;

}