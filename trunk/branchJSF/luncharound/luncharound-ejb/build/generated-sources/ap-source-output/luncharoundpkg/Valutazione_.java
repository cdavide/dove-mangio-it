package luncharoundpkg;

import java.util.GregorianCalendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-09-24T09:44:50")
@StaticMetamodel(Valutazione.class)
public class Valutazione_ { 

    public static volatile SingularAttribute<Valutazione, Long> id;
    public static volatile SingularAttribute<Valutazione, Byte> velocita;
    public static volatile SingularAttribute<Valutazione, Byte> qualita;
    public static volatile SingularAttribute<Valutazione, Byte> cortesia;
    public static volatile SingularAttribute<Valutazione, Byte> descrizione;
    public static volatile SingularAttribute<Valutazione, Byte> affollamento;
    public static volatile SingularAttribute<Valutazione, Long> idUtente;
    public static volatile SingularAttribute<Valutazione, GregorianCalendar> dataVal;
    public static volatile SingularAttribute<Valutazione, Integer> idLocale;
    public static volatile SingularAttribute<Valutazione, Byte> pulizia;
    public static volatile SingularAttribute<Valutazione, Byte> quantita;

}