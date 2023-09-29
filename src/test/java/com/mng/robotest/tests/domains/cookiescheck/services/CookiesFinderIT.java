package com.mng.robotest.tests.domains.cookiescheck.services;

import org.junit.Test;

import com.mng.robotest.tests.domains.cookiescheck.services.mangoauditor.CookiesFinderMangoAuditor;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static org.junit.jupiter.api.Assertions.*;

public class CookiesFinderIT {

	@Test
	public void testGetCookies() throws Exception {
		var cookieFinder = new CookiesFinderMangoAuditor("https://shop.mango.com", shop);
		var cookiesOpt = cookieFinder.getAllowedCookies();
		
		assertTrue(cookiesOpt.isPresent() && cookiesOpt.get().size()>0);
		assertTrue(!CookiesFinder.isNeededRefreshDataCookies(5));
		Thread.sleep(1001);
		assertTrue(CookiesFinder.isNeededRefreshDataCookies(1));
	}

}
