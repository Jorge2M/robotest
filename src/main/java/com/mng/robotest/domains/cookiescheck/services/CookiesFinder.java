package com.mng.robotest.domains.cookiescheck.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;

public class CookiesFinder {

	private static final Logger logger = Log4jTM.getLogger();
	
	private static final int SECONDS_PERSISTENCE = 3600; 
	private static Optional<List<Cookie>> listCookies = Optional.empty();
	private static LocalDateTime timeCapturedCookies;

	
    public synchronized Optional<List<Cookie>> getAllowedCookies() {
    	if (isNeededRefreshDataCookies(SECONDS_PERSISTENCE)) {
   			listCookies = getCookiesFromHttp();
   			timeCapturedCookies = LocalDateTime.now();
    	}
    	return listCookies;
    }
    
    Optional<List<Cookie>> getCookiesFromHttp() {
    	try {
		    CookiesRepository cookiesRepo = new HttpCookiesFinder();
		    List<Cookie> cookies;
		    try {
		    	cookies = cookiesRepo.retrieveCookies();
		    } catch (Exception e) {
		    	return Optional.empty(); 
		    }
		    if (cookies==null || cookies.size()==0) {
		    	logger.warn("Problem retrieving cookies from http service");
		    	return Optional.empty();
		    }
		    return Optional.of(cookies);
    	} catch (Exception e) {
    		logger.warn("Problem retrieving cookies from http service", e);
            return Optional.empty();
    	}
    }
    
    boolean isNeededRefreshDataCookies(int secondsPersistence) {
    	if (listCookies.isEmpty()) {
    		return true;
    	}
    	LocalDateTime timeToCaptureCookies = timeCapturedCookies.plusSeconds(secondsPersistence);
    	return (LocalDateTime.now().compareTo(timeToCaptureCookies)>0);
    }	
	
}
