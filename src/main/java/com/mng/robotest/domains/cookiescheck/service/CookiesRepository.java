package com.mng.robotest.domains.cookiescheck.service;

import java.util.List;
import com.mng.robotest.domains.cookiescheck.entities.Cookie;

public interface CookiesRepository {
	public List<Cookie> retrieveCookies();
	
}
