package luncharoundpkg;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import luncharoundpkg.Categoria;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-09-24T09:44:50")
@StaticMetamodel(Piatto.class)
public class Piatto_ { 

    public static volatile SingularAttribute<Piatto, Long> id;
    public static volatile SingularAttribute<Piatto, Categoria> categoria;
    public static volatile SingularAttribute<Piatto, Byte> flags;
    public static volatile SingularAttribute<Piatto, Float> prezzo;
    public static volatile SingularAttribute<Piatto, Boolean> corrente;
    public static volatile SingularAttribute<Piatto, String> nome;
    public static volatile SingularAttribute<Piatto, Integer> idLocale;

}