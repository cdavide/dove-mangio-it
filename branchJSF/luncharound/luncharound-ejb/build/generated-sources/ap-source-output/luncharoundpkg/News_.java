package luncharoundpkg;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-01-12T17:32:05")
@StaticMetamodel(News.class)
public class News_ { 

    public static volatile SingularAttribute<News, Long> id;
    public static volatile SingularAttribute<News, Date> dataInizio;
    public static volatile SingularAttribute<News, String> titolo;
    public static volatile SingularAttribute<News, Integer> idLocale;
    public static volatile SingularAttribute<News, String> descr;

}