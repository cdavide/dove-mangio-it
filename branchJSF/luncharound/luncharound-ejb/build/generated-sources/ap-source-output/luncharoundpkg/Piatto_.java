package luncharoundpkg;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import luncharoundpkg.Categoria;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-01-12T17:32:05")
@StaticMetamodel(Piatto.class)
public class Piatto_ { 

    public static volatile SingularAttribute<Piatto, Long> id;
    public static volatile SingularAttribute<Piatto, Categoria> categoria;
    public static volatile SingularAttribute<Piatto, Boolean> vegano;
    public static volatile SingularAttribute<Piatto, Boolean> pesce;
    public static volatile SingularAttribute<Piatto, Float> prezzo;
    public static volatile SingularAttribute<Piatto, Boolean> celiaco;
    public static volatile SingularAttribute<Piatto, Boolean> carne;
    public static volatile SingularAttribute<Piatto, Boolean> corrente;
    public static volatile SingularAttribute<Piatto, String> nome;
    public static volatile SingularAttribute<Piatto, Integer> idLocale;
    public static volatile SingularAttribute<Piatto, Boolean> alcolico;
    public static volatile SingularAttribute<Piatto, Boolean> vegetariano;

}