/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpkg;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

/** Jersey REST client generated for REST resource:Twitter OAuth [account/archive.{format}]<br>
 *  USAGE:<pre>
 *        TwitterClient client = new TwitterClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 *  </pre>
 * @author Bovio Lorenzo, Bronzino Francesco, Concas Davide
 */
public class TwitterClient {
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://twitter.com";
    private static final String OAUTH_BASE_URL = "http://twitter.com/oauth";
    /**
     * Please, specify the consumer_key string obtained from service API pages
     */
    private static final String CONSUMER_KEY = "8ZpcO0NuCYz047GGyA0cjQ";
    /**
     * Please, specify the consumer_secret string obtained from service API pages
     */
    private static final String CONSUMER_SECRET = "FUKoRDEm6JD2xhnASfBeVnaDarrpFoAXt6eJ8LX44";
    private OAuthParameters oauth_params;
    private OAuthSecrets oauth_secrets;
    private OAuthClientFilter oauth_filter;

    public TwitterClient(String format) {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        String resourcePath = java.text.MessageFormat.format("account/archive.{0}", new Object[]{format});
        webResource = client.resource(BASE_URI).path(resourcePath);
    }

    public void setResourcePath(String format) {
        String resourcePath = java.text.MessageFormat.format("account/archive.{0}", new Object[]{format});
        webResource = client.resource(BASE_URI).path(resourcePath);
    }

    /**
     * @param responseType Class representing the response
     * @param page query parameter
     * @return response object (instance of responseType class)
     */
    public <T> T getArchives(Class<T> responseType, String page) throws UniformInterfaceException {
        String[] queryParamNames = new String[]{"page"};
        String[] queryParamValues = new String[]{page};
        return webResource.queryParams(getQueryOrFormParams(queryParamNames, queryParamValues)).accept(javax.ws.rs.core.MediaType.TEXT_XML).get(responseType);
    }

    private MultivaluedMap getQueryOrFormParams(String[] paramNames, String[] paramValues) {
        MultivaluedMap<String, String> qParams = new com.sun.jersey.api.representation.Form();
        for (int i = 0; i < paramNames.length; i++) {
            if (paramValues[i] != null) {
                qParams.add(paramNames[i], paramValues[i]);
            }
        }
        return qParams;
    }

    public void close() {
        client.destroy();
    }

    private static Form getOAuthRequestToken() throws UniformInterfaceException {
        Client reqTokenClient = new Client();
        WebResource resource = reqTokenClient.resource(OAUTH_BASE_URL).path("request_token");
        OAuthParameters o_params = new OAuthParameters().consumerKey(CONSUMER_KEY).signatureMethod(com.sun.jersey.oauth.signature.HMAC_SHA1.NAME).version("1.0").nonce().timestamp();
        OAuthSecrets o_secrets = new OAuthSecrets().consumerSecret(CONSUMER_SECRET);
        OAuthClientFilter o_filter = new OAuthClientFilter(reqTokenClient.getProviders(), o_params, o_secrets);
        resource.addFilter(o_filter);
        return resource.get(Form.class);
    }

    private static Form getOAuthAccessToken(HttpSession session, String oauth_verifier) throws UniformInterfaceException {
        Client accessTokenClient = new Client();
        WebResource resource = accessTokenClient.resource(OAUTH_BASE_URL).path("access_token");
        OAuthParameters o_params = new OAuthParameters().consumerKey(CONSUMER_KEY).token((String) session.getAttribute("oauth_token")).signatureMethod(com.sun.jersey.oauth.signature.HMAC_SHA1.NAME).version("1.0").nonce().timestamp().verifier(oauth_verifier);
        OAuthSecrets o_secrets = new OAuthSecrets().consumerSecret(CONSUMER_SECRET).tokenSecret((String) session.getAttribute("oauth_token_secret"));
        OAuthClientFilter o_filter = new OAuthClientFilter(accessTokenClient.getProviders(), o_params, o_secrets);
        resource.addFilter(o_filter);
        return resource.get(Form.class);
    }

    /**
     * The method sets the OAuth parameters for webResource.
     */
    public void initOAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((String) session.getAttribute("oauth_token") == null) {
            response.sendRedirect(request.getContextPath() + "/OAuthLogin");
        } else {
            oauth_params = new OAuthParameters().consumerKey(CONSUMER_KEY).token((String) session.getAttribute("oauth_token")).signatureMethod(com.sun.jersey.oauth.signature.HMAC_SHA1.NAME).version("1.0").nonce().timestamp();
            oauth_secrets = new OAuthSecrets().consumerSecret(CONSUMER_SECRET).tokenSecret((String) session.getAttribute("oauth_token_secret"));
            oauth_filter = new OAuthClientFilter(client.getProviders(), oauth_params, oauth_secrets);
            webResource.addFilter(oauth_filter);
        }
    }

    /**
     * The method increases OAuth nonce and timestamp parameters to make each request unique.
     * The method should be called when repetitive requests are sent to service API provider:
     * <pre>
     * client.initOauth();
     * client.getXXX(...);
     * client.makeOAuthRequestUnique();
     * client.getYYY(...);
     * client.makeOAuthRequestUnique();
     * client.getZZZ(...);
     * </pre>
     */
    public void makeOAuthRequestUnique() {
        if (oauth_params != null) {
            oauth_params.nonce().timestamp();
        }
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("oauth_token");
        session.removeAttribute("oauth_token_secret");
    }

    public static class OAuthLoginServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            java.io.PrintWriter out = response.getWriter();
            try {
                Form requestTokenResponse = null;
                UniformInterfaceException uiEx = null;
                try {
                    requestTokenResponse = getOAuthRequestToken();
                    javax.servlet.http.HttpSession session = request.getSession(true);
                    session.setAttribute("oauth_token", requestTokenResponse.getFirst("oauth_token"));
                    session.setAttribute("oauth_token_secret", requestTokenResponse.getFirst("oauth_token_secret"));
                } catch (UniformInterfaceException ex) {
                    uiEx = ex;
                }
                if (uiEx == null) {
                    response.sendRedirect("http://twitter.com/oauth/login?oauth_token=" + requestTokenResponse.getFirst("oauth_token"));
                } else {
                    out.println("Problem to get request token: " + uiEx.getResponse() + ": " + uiEx.getResponse().getEntity(String.class));
                }
            } finally {
                out.close();
            }
        }
    }

    public static class OAuthCallbackServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            java.io.PrintWriter out = response.getWriter();
            try {
                String oauth_verifier = request.getParameter("oauth_verifier");
                Form accessTokenResponse = null;
                UniformInterfaceException uiEx = null;
                try {
                    javax.servlet.http.HttpSession session = request.getSession(true);
                    accessTokenResponse = getOAuthAccessToken(session, oauth_verifier);
                    session.setAttribute("oauth_token", accessTokenResponse.getFirst("oauth_token"));
                    session.setAttribute("oauth_token_secret", accessTokenResponse.getFirst("oauth_token_secret"));
                } catch (UniformInterfaceException ex) {
                    uiEx = ex;
                }
                if (uiEx == null) {
                    
                } else {
                    out.println("Problem to get access token: " + uiEx.getResponse() + ": " + uiEx.getResponse().getEntity(String.class));
                }
            } finally {
                out.close();
            }
        }
    }
    
}
