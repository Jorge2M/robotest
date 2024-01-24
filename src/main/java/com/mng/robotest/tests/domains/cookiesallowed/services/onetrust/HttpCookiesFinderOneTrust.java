package com.mng.robotest.tests.domains.cookiesallowed.services.onetrust;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.cookiesallowed.entities.Cookie;
import com.mng.robotest.tests.domains.cookiesallowed.entities.CookiesData;
import com.mng.robotest.tests.domains.cookiesallowed.exceptions.IrretrievableCookies;
import com.mng.robotest.tests.domains.cookiesallowed.exceptions.UnparseableCookies;
import com.mng.robotest.tests.domains.cookiesallowed.idp.IDPClientService;
import com.mng.robotest.tests.domains.cookiesallowed.idp.IdentityToken;
import com.mng.robotest.tests.domains.cookiesallowed.idp.IdpCredentials;
import com.mng.robotest.tests.domains.cookiesallowed.services.CookiesRepository;

import static org.apache.http.impl.client.HttpClients.createDefault;

public class HttpCookiesFinderOneTrust implements CookiesRepository {

	private static final Logger logger = Log4jTM.getLogger();
	
    private final IdentityToken identityToken;
    private final HttpClient httpClient;
    private final String url = "https://mango.my.onetrust.com/api/cookiemanager/v2/cookie-reports/search?language=en";
	
    public HttpCookiesFinderOneTrust() throws Exception {
        this.identityToken = getIdentityToken();
        this.httpClient = createDefault();
    }
	
    @Override
    public List<Cookie> retrieveCookies() throws IrretrievableCookies, IOException {
        try {
            HttpPost post = buildPostRequestWith(identityToken);
            HttpResponse response = httpClient.execute(post);
            int status = response.getStatusLine().getStatusCode(); 
            if (status != 200) {
            	String message = String.format("Error %s calling GTM Cookies Service", response.getStatusLine().getStatusCode()); 
                logger.error(message);
                throw new IrretrievableCookies(message);
            }
            
            CookiesData cookiesData = new ObjectMapper()
            		.readValue(response.getEntity().getContent(), CookiesData.class); 
            return cookiesData.getContent();
        } catch (IOException io) {
            logger.error("Error parsing the cookies information", io);
            throw new UnparseableCookies(io);
        } catch (Exception e) {
            logger.error("Error retrieving the cookies information", e);
            throw new IrretrievableCookies(e);
        }
    }
    
    private HttpPost buildPostRequestWith(IdentityToken identityToken) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + identityToken.getAccess_token());
        StringEntity entity = new StringEntity("{\"domains\": [\"shop.mango.com\"]}");
        post.setEntity(entity);
        return post;
    }
    
    private IdentityToken getIdentityToken() throws Exception {
    	//Credentials for obtain the READ Token for Cookie
    	var idpCredentials = new IdpCredentials(
    			"001ef8b226344441859f84dd913d0d5b",
    			"97bFu9YETKpjCu3dx8RMRd9GvMdLcYl0"); 
    	var idpClientService = new IDPClientService(
    			"https://mango.my.onetrust.com/api/access/v1/", 
    			idpCredentials);
    	
    	return idpClientService.clientCredentialsToken();
    }
}
