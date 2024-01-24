package com.mng.robotest.tests.domains.cookiesallowed.services;

import java.util.List;

import com.mng.robotest.tests.domains.cookiesallowed.entities.Cookie;

public interface CookiesRepository {
	public List<Cookie> retrieveCookies() throws Exception;
	
}
