/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/** Questo Bean serve esclusivamente per aggiungere i bottoni di facebook, twitter e Google plus, come scope
 * gli metto Session oppure application, in questo modo non vengono istanziati troppi oggetti
 *
 * @author Bovio Loernzo, Bronzino Francesco, Concas Davide
 */
@ManagedBean(name = "utilBean")
@ApplicationScoped
public class UtilBean {

    String plusOne = "</br><g:plusone size=\"small\"></g:plusone>"
            + "<script type=\"text/javascript\">"
            + "(function() { var po = document.createElement('script'); po.type"
            + " = 'text/javascript'; po.async = true; po.src = 'https://apis.google.com/js/plusone.js'; "
            + "var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);"
            + " })(); </script>";
    String twitter = "<a href=\"https://twitter.com/share\" class=\"twitter-share-button\""
            + " data-count=\"horizontal\">Tweet</a><script type=\"text/javascript\" src=\"//platform.twitter.com/widgets.js\"></script>";
    
    /** Creates a new instance of UtilBean */
    public UtilBean() {
    }

    public String getPlusOne() {
        return plusOne;
    }

    public void setPlusOne(String plusOne) {
        this.plusOne = plusOne;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
    
    
}
