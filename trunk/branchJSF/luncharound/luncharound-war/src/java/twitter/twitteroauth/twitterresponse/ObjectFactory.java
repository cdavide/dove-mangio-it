//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.28 at 11:05:48 PM PST 
//


package twitter.twitteroauth.twitterresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the twitter.twitteroauth.twitterresponse package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Authorized_QNAME = new QName("", "authorized");
    private final static QName _Friends_QNAME = new QName("", "friends");
    private final static QName _Status_QNAME = new QName("", "status");
    private final static QName _Authenticated_QNAME = new QName("", "authenticated");
    private final static QName _Ok_QNAME = new QName("", "ok");
    private final static QName _User_QNAME = new QName("", "user");
    private final static QName _DirectMessage_QNAME = new QName("", "direct_message");
    private final static QName _HashError_QNAME = new QName("", "error");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: twitter.twitteroauth.twitterresponse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Hash.HourlyLimit }
     * 
     * @return 
     */
    public Hash.HourlyLimit createHashHourlyLimit() {
        return new Hash.HourlyLimit();
    }

    /**
     * Create an instance of {@link Nilclasses }
     * 
     * @return 
     */
    public Nilclasses createNilclasses() {
        return new Nilclasses();
    }

    /**
     * Create an instance of {@link UserType }
     * 
     * @return 
     */
    public UserType createUserType() {
        return new UserType();
    }

    /**
     * Create an instance of {@link DirectMessageType }
     * 
     * @return 
     */
    public DirectMessageType createDirectMessageType() {
        return new DirectMessageType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     * @return 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link Hash.ResetTime }
     * 
     * @return 
     */
    public Hash.ResetTime createHashResetTime() {
        return new Hash.ResetTime();
    }

    /**
     * Create an instance of {@link Hash }
     * 
     * @return 
     */
    public Hash createHash() {
        return new Hash();
    }

    /**
     * Create an instance of {@link DirectMessages }
     * 
     * @return 
     */
    public DirectMessages createDirectMessages() {
        return new DirectMessages();
    }

    /**
     * Create an instance of {@link Statuses }
     * 
     * @return 
     */
    public Statuses createStatuses() {
        return new Statuses();
    }

    /**
     * Create an instance of {@link Hash.RemainingHits }
     * 
     * @return 
     */
    public Hash.RemainingHits createHashRemainingHits() {
        return new Hash.RemainingHits();
    }

    /**
     * Create an instance of {@link Hash.ResetTimeInSeconds }
     * 
     * @return 
     */
    public Hash.ResetTimeInSeconds createHashResetTimeInSeconds() {
        return new Hash.ResetTimeInSeconds();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     * @param value
     * @return  
     */
    @XmlElementDecl(namespace = "", name = "authorized")
    public JAXBElement<Boolean> createAuthorized(Boolean value) {
        return new JAXBElement<Boolean>(_Authorized_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     * @param value
     * @return  
     */
    @XmlElementDecl(namespace = "", name = "friends")
    public JAXBElement<Boolean> createFriends(Boolean value) {
        return new JAXBElement<Boolean>(_Friends_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusType }{@code >}}
     * 
     * @param value 
     * @return 
     */
    @XmlElementDecl(namespace = "", name = "status")
    public JAXBElement<StatusType> createStatus(StatusType value) {
        return new JAXBElement<StatusType>(_Status_QNAME, StatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     * @param value
     * @return  
     */
    @XmlElementDecl(namespace = "", name = "authenticated")
    public JAXBElement<Boolean> createAuthenticated(Boolean value) {
        return new JAXBElement<Boolean>(_Authenticated_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     * @param value
     * @return  
     */
    @XmlElementDecl(namespace = "", name = "ok")
    public JAXBElement<Boolean> createOk(Boolean value) {
        return new JAXBElement<Boolean>(_Ok_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserType }{@code >}}
     * 
     * @param value
     * @return  
     */
    @XmlElementDecl(namespace = "", name = "user")
    public JAXBElement<UserType> createUser(UserType value) {
        return new JAXBElement<UserType>(_User_QNAME, UserType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectMessageType }{@code >}}
     * 
     * @param value 
     * @return 
     */
    @XmlElementDecl(namespace = "", name = "direct_message")
    public JAXBElement<DirectMessageType> createDirectMessage(DirectMessageType value) {
        return new JAXBElement<DirectMessageType>(_DirectMessage_QNAME, DirectMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     * @param value 
     * @return 
     */
    @XmlElementDecl(namespace = "", name = "error", scope = Hash.class)
    public JAXBElement<String> createHashError(String value) {
        return new JAXBElement<String>(_HashError_QNAME, String.class, Hash.class, value);
    }

}
