/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luncharoundpkg;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
public class Util {
    
    /**
     * 
     * @param req
     * @param o
     */
    public static void riempi(HttpServletRequest req, Object o){

        HashMap map = new HashMap();
        
        Enumeration names = req.getParameterNames();
        while(names.hasMoreElements()){
            String name= (String)names.nextElement();
            map.put(name,req.getParameterValues(name));
        }
        
        names = req.getAttributeNames();
        while(names.hasMoreElements()){
            String name= (String)names.nextElement();
            map.put(name,req.getAttribute(name));
        }
        
        try {
            BeanUtils.populate(o, map);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControlloreLocale.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ControlloreLocale.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
