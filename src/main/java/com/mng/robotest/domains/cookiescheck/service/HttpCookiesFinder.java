package com.mng.robotest.domains.cookiescheck.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;
import com.mng.robotest.domains.cookiescheck.exceptions.IrretrievableCookies;
import com.mng.robotest.domains.cookiescheck.exceptions.UnparseableCookies;
import com.mng.robotest.domains.cookiescheck.idp.IdentityToken;

import static org.apache.http.impl.client.HttpClients.createDefault;

public class HttpCookiesFinder implements CookiesRepository {

	private static final Logger logger = Log4jTM.getLogger();
	
    private final IdentityToken identityToken;
    private final HttpClient httpClient;
    private final String url;
	
    public HttpCookiesFinder(IdentityToken identityToken, String url) {
        this.identityToken = identityToken;
        this.url = url;
        this.httpClient = createDefault();
    }
	
    @Override
    public List<Cookie> retrieveCookies() {
        try {
            HttpGet get = buildRequestWith(identityToken);

            HttpResponse response = httpClient.execute(get);
            ObjectMapper mapper = new ObjectMapper();
            List<Cookie> listCookies = mapper.readValue(response.getEntity().getContent(), 
            		new TypeReference<List<Cookie>>(){
                    });
            return listCookies;
        } catch (IOException io) {
            logger.error("Error parsing the cookies information: " + io);
            throw new UnparseableCookies(io);
        } catch (Exception e) {
            logger.error("Error retrieving the cookies information: " + e);
            throw new IrretrievableCookies(e);
        }
    }
    
    private HttpGet buildRequestWith(IdentityToken identityToken) {
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-Type", "application/json");
        get.addHeader("Authorization", "Bearer " + identityToken.getAccess_token());
        return get;
    }
	
}
