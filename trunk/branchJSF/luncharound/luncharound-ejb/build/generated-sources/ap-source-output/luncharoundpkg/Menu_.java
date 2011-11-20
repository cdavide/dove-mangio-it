package luncharoundpkg;

import java.util.GregorianCalendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import luncharoundpkg.Piatto;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-09-24T09:44:50")
@StaticMetamodel(Menu.class)
public class Menu_ { 

    public static volatile SingularAttribute<Menu, Integer> id;
    public static volatile SingularAttribute<Menu, Integer> idLocale;
    public static volatile SingularAttribute<Menu, GregorianCalendar> validita;
    public static volatile ListAttribute<Menu, Piatto> listaPiatti;

}