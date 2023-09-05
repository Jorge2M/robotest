package com.mng.robotest.domains.cookiescheck.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;
import com.mng.robotest.domains.cookiescheck.services.mangoauditor.CookiesFinderMangoAuditor;

public class CookiesFinderIT {

	@Test
	public void testGetCookies() throws Exception {
		CookiesFinderMangoAuditor cookieFinder = new CookiesFinderMangoAuditor(
				"https://shop.mango.com", AppEcom.shop);
		Optional<List<Cookie>> cookies = cookieFinder.getAllowedCookies();
		
		assertTrue(cookies.isPresent() && cookies.get().size()>0);
		assertTrue(!CookiesFinder.isNeededRefreshDataCookies(5));
		Thread.sleep(1001);
		assertTrue(CookiesFinder.isNeededRefreshDataCookies(1));
	}

}
