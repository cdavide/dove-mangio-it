package luncharoundpkg;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-01-08T16:22:52")
@StaticMetamodel(Valutazione.class)
public class Valutazione_ { 

    public static volatile SingularAttribute<Valutazione, Long> id;
    public static volatile SingularAttribute<Valutazione, Integer> velocita;
    public static volatile SingularAttribute<Valutazione, Integer> qualita;
    public static volatile SingularAttribute<Valutazione, Integer> cortesia;
    public static volatile SingularAttribute<Valutazione, Integer> descrizione;
    public static volatile SingularAttribute<Valutazione, Integer> affollamento;
    public static volatile SingularAttribute<Valutazione, Long> idUtente;
    public static volatile SingularAttribute<Valutazione, Date> dataVal;
    public static volatile SingularAttribute<Valutazione, Integer> idLocale;
    public static volatile SingularAttribute<Valutazione, Integer> pulizia;
    public static volatile SingularAttribute<Valutazione, Integer> quantita;

}