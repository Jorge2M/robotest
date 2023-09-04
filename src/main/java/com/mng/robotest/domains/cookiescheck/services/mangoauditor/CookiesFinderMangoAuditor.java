package com.mng.robotest.domains.cookiescheck.services.mangoauditor;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;
import com.mng.robotest.domains.cookiescheck.services.CookiesFinder;
import com.mng.robotest.domains.cookiescheck.services.CookiesRepository;

public class CookiesFinderMangoAuditor extends CookiesFinder {

	private static final Logger logger = Log4jTM.getLogger();
	
	private final String initialUrl;
	private final AppEcom app;
	
	public CookiesFinderMangoAuditor(String urlBase, AppEcom app) {
		this.initialUrl = urlBase;
		this.app = app;
	}
	
	@Override
	protected Optional<List<Cookie>> obtainAllowedCookies() {
		return getCookiesFromHttp();
	}
    
    Optional<List<Cookie>> getCookiesFromHttp() {
    	try {
		    CookiesRepository cookiesRepo = new HttpCookiesFinderMangoAuditor(initialUrl, app);
		    return getCookiesFromRepo(cookiesRepo);
    	} catch (Exception e) {
    		logger.warn("Problem retrieving cookies from http service", e);
            return Optional.empty();
    	}
    }

	private Optional<List<Cookie>> getCookiesFromRepo(CookiesRepository cookiesRepo) {
		List<Cookie> cookies;
		try {
			cookies = cookiesRepo.retrieveCookies();
		} catch (Exception e) {
			return Optional.empty(); 
		}
		if (cookies==null || cookies.isEmpty()) {
			logger.warn("Problem retrieving cookies from http service");
			return Optional.empty();
		}
		return Optional.of(cookies);
	}
    
}
