package com.mng.robotest.domains.cookiescheck.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;

public class CookiesFinder {

	private static final Logger logger = Log4jTM.getLogger();
	
	private final static int SECONDS_PERSISTENCE = 3600; 
	private static Optional<List<Cookie>> listCookies;
	private static Calendar timeCapturedCookies;

	
    public synchronized Optional<List<Cookie>> getAllowedCookies() {
    	if (isNeededRefreshDataCookies(SECONDS_PERSISTENCE)) {
   			listCookies = getCookiesFromHttp();
   			timeCapturedCookies = Calendar.getInstance();
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
            return null;
    	}
    }
    
    boolean isNeededRefreshDataCookies(int secondsPersistence) {
    	if (listCookies==null) {
    		return true;
    	}
    	Calendar timeToCaptureCookies = (Calendar)timeCapturedCookies.clone();
    	timeToCaptureCookies.add(Calendar.SECOND, secondsPersistence);
    	return (Calendar.getInstance().after(timeToCaptureCookies));
    }	
	
}
