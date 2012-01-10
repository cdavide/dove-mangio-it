package luncharoundpkg;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import luncharoundpkg.Piatto;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-01-08T16:22:52")
@StaticMetamodel(Menu.class)
public class Menu_ { 

    public static volatile SingularAttribute<Menu, Integer> id;
    public static volatile SingularAttribute<Menu, Integer> idLocale;
    public static volatile SingularAttribute<Menu, Date> validita;
    public static volatile ListAttribute<Menu, Piatto> listaPiatti;

}