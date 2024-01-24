package com.mng.robotest.tests.domains.cookiesallowed.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.mng.robotest.tests.domains.cookiesallowed.entities.Cookie;

public abstract class CookiesFinder {

	protected static final int SECONDS_PERSISTENCE = 3600; 
	protected static Optional<List<Cookie>> listCookies = Optional.empty();
	protected static LocalDateTime timeCapturedCookies;
	
	protected abstract Optional<List<Cookie>> obtainAllowedCookies();
	
	public Optional<List<Cookie>> getAllowedCookies() {
		updateAllowedCookiesIfNeeded();
		return listCookies;
	}
	
	private synchronized void updateAllowedCookiesIfNeeded() {
		if (isNeededRefreshDataCookies(SECONDS_PERSISTENCE)) {
			listCookies = obtainAllowedCookies();
			timeCapturedCookies = LocalDateTime.now();
		}
	}
	
    protected static boolean isNeededRefreshDataCookies(int secondsPersistence) {
    	if (listCookies.isEmpty()) {
    		return true;
    	}
    	var timeToCaptureCookies = timeCapturedCookies.plusSeconds(secondsPersistence);
    	return (LocalDateTime.now().compareTo(timeToCaptureCookies)>0);
    }
	
}
