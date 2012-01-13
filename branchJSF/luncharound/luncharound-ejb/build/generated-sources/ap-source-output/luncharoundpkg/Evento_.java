package luncharoundpkg;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-01-12T17:32:05")
@StaticMetamodel(Evento.class)
public class Evento_ { 

    public static volatile SingularAttribute<Evento, Long> id;
    public static volatile SingularAttribute<Evento, Date> dataFine;
    public static volatile SingularAttribute<Evento, Date> dataInizio;
    public static volatile SingularAttribute<Evento, String> titolo;
    public static volatile SingularAttribute<Evento, Integer> idLocale;
    public static volatile SingularAttribute<Evento, String> descr;

}