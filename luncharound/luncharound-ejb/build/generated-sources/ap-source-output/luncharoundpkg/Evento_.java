package luncharoundpkg;

import java.util.GregorianCalendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-09-24T09:44:50")
@StaticMetamodel(Evento.class)
public class Evento_ { 

    public static volatile SingularAttribute<Evento, Long> id;
    public static volatile SingularAttribute<Evento, GregorianCalendar> dataFine;
    public static volatile SingularAttribute<Evento, GregorianCalendar> dataInizio;
    public static volatile SingularAttribute<Evento, Integer> idLocale;
    public static volatile SingularAttribute<Evento, String> descr;

}