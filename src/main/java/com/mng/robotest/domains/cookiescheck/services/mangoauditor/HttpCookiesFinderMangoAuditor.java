package com.mng.robotest.domains.cookiescheck.services.mangoauditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;
import com.mng.robotest.domains.cookiescheck.exceptions.IrretrievableCookies;
import com.mng.robotest.domains.cookiescheck.exceptions.UnparseableCookies;
import com.mng.robotest.domains.cookiescheck.services.CookiesRepository;
import com.mng.robotest.domains.cookiescheck.services.mangoauditor.beans.CookiesMangoAuditor;
import com.mng.robotest.domains.cookiescheck.services.mangoauditor.beans.GroupsParty;
import com.mng.robotest.repository.UtilsData;

import static org.apache.http.impl.client.HttpClients.createDefault;

public class HttpCookiesFinderMangoAuditor implements CookiesRepository {

	private static final Logger logger = Log4jTM.getLogger();

    private final HttpClient httpClient;
    
    //TODO ajustar según parámetros urlBase y app 
    //private final String url = "https://cloudtest.dev.mango.com/ws-cookies-auditor/cookies?name=ahernandez-cookies-shop8&channel=shop";
    private final String url;
	
    public HttpCookiesFinderMangoAuditor(String initialUrl, AppEcom app) throws Exception {
        this.httpClient = createDefault();
        this.url = getUrlEndpoint(initialUrl, app);
    }
    
    private final String getUrlEndpoint(String initialUrl, AppEcom app) throws Exception {
    	String urlBase = UtilsData.getUrlBase(initialUrl);
    	String nameCloudTest = UtilsData.getNameCloudTest(initialUrl);
    	urlBase+="/ws-cookies-auditor/cookies";
    	urlBase+="?channel=" + app;
		if ("".compareTo(nameCloudTest)!=0) {
			urlBase+=("&name=" + nameCloudTest); 
		}
    	return urlBase;
    }
	
    @Override
    public List<Cookie> retrieveCookies() throws IrretrievableCookies, IOException {
        try {
            var get = buildGetRequest();
            var response = httpClient.execute(get);
            int status = response.getStatusLine().getStatusCode(); 
            if (status != 200) {
            	String message = String.format("Error %s calling GTM Cookies Service", response.getStatusLine().getStatusCode()); 
                logger.error(message);
                throw new IrretrievableCookies(message);
            }
            
            var cookiesMangoAuditor = new ObjectMapper()
            		.readValue(response.getEntity().getContent(), CookiesMangoAuditor.class);
            
            return getCookiesData(cookiesMangoAuditor); 
        } catch (IOException io) {
            logger.error("Error parsing the cookies information", io);
            throw new UnparseableCookies(io);
        } catch (Exception e) {
            logger.error("Error retrieving the cookies information", e);
            throw new IrretrievableCookies(e);
        }
    }
    
    private HttpGet buildGetRequest() {
        var get = new HttpGet(url);
        get.addHeader("Content-Type", "application/json");
        return get;
    }
    
    private List<Cookie> getCookiesData(CookiesMangoAuditor cookiesMangoAuditor) {
    	List<Cookie> listCookies = new ArrayList<>();
    	listCookies.addAll(getCookies(cookiesMangoAuditor.functional));
    	listCookies.addAll(getCookies(cookiesMangoAuditor.targeting));
    	listCookies.addAll(getCookies(cookiesMangoAuditor.performance));
    	listCookies.addAll(getCookies(cookiesMangoAuditor.necessary));
    	listCookies.addAll(getCookies(cookiesMangoAuditor.social));
    	return listCookies;
    }
    
    private List<Cookie> getCookies(GroupsParty groupsParty) {
    	List<Cookie> listCookies = new ArrayList<>();
    	for (var firstParty : groupsParty.firstParty) {
    		listCookies.add(Cookie.from(firstParty.name));
    	}
    	for (var thirdParty : groupsParty.thirdParty) {
    		for (var cookieTP : thirdParty.cookies) {
    			var cookie = Cookie.from(cookieTP.name);
    			cookie.setThirdParty(true);
    			cookie.setHost(thirdParty.domain);
    			listCookies.add(cookie);
    		}
    	}
    	return listCookies;
    }

}
