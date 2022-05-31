package com.mng.robotest.domains.cookiescheck.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.mng.robotest.domains.cookiescheck.entities.Cookie;
import com.mng.robotest.domains.cookiescheck.services.CookiesFinder;

public class CookiesFinderIT {

	@Test
	public void testGetCookies() throws Exception {
		CookiesFinder cookieFinder = new CookiesFinder();
		List<Cookie> cookies = cookieFinder.getAllowedCookies();
		
		assertTrue(cookies!=null && cookies.size()>0);
		assertTrue(!cookieFinder.isNeededRefreshDataCookies(5));
		Thread.sleep(1001);
		assertTrue(cookieFinder.isNeededRefreshDataCookies(1));
	}

}
