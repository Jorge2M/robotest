package com.mng.robotest.tests.domains.cookiescheck.services;

import java.util.List;

import com.mng.robotest.tests.domains.cookiescheck.entities.Cookie;

public interface CookiesRepository {
	public List<Cookie> retrieveCookies() throws Exception;
	
}
