package com.mng.robotest.domains.cookiescheck.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.mng.robotest.domains.cookiescheck.entities.Cookie;
import com.mng.robotest.domains.cookiescheck.services.onetrust.CookiesFinderOneTrust;

public class CookiesFinderIT {

	@Test
	public void testGetCookies() throws Exception {
		CookiesFinderOneTrust cookieFinder = new CookiesFinderOneTrust();
		Optional<List<Cookie>> cookies = cookieFinder.getAllowedCookies();
		
		assertTrue(cookies.isPresent() && cookies.get().size()>0);
		assertTrue(!cookieFinder.isNeededRefreshDataCookies(5));
		Thread.sleep(1001);
		assertTrue(cookieFinder.isNeededRefreshDataCookies(1));
	}

}
