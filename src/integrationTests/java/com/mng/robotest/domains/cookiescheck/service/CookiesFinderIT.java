package com.mng.robotest.domains.cookiescheck.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mng.robotest.domains.cookiescheck.entities.Cookie;

class CookiesFinderIT {

	@Test
	void testGetCookies() throws Exception {
		CookiesFinder cookieFinder = new CookiesFinder();
		List<Cookie> cookies = cookieFinder.getCookies();
		
		assertTrue(cookies!=null && cookies.size()>0);
		assertTrue(!cookieFinder.isNeededRefreshDataCookies(5));
		Thread.sleep(1001);
		assertTrue(cookieFinder.isNeededRefreshDataCookies(1));
	}

}
