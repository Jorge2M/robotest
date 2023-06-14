package com.mng.robotest.domains.cookiescheck.services.onetrust;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;
import com.mng.robotest.domains.cookiescheck.services.CookiesFinder;
import com.mng.robotest.domains.cookiescheck.services.CookiesRepository;

public class CookiesFinderOneTrust extends CookiesFinder {

	private static final Logger logger = Log4jTM.getLogger();
	
	@Override
	protected Optional<List<Cookie>> obtainAllowedCookies() {
		return getCookiesFromHttp();
	}
	
    Optional<List<Cookie>> getCookiesFromHttp() {
    	try {
		    CookiesRepository cookiesRepo = new HttpCookiesFinderOneTrust();
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
