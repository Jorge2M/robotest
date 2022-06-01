package com.mng.robotest.domains.cookiescheck.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.cookiescheck.entities.Cookie;

public class CookiesChecker {

	private final Optional<List<Cookie>> allowedCookies;
	
	public CookiesChecker() {
		CookiesFinder finder = new CookiesFinder();
		allowedCookies = finder.getAllowedCookies();
	}
	
	public CookiesChecker(List<Cookie> allowedCookies) {
		this.allowedCookies = Optional.of(allowedCookies);
	}
	
	public Pair<Boolean, List<org.openqa.selenium.Cookie>> check(WebDriver driver) {
		Set<org.openqa.selenium.Cookie> pageCookies = driver.manage().getCookies();
		List<org.openqa.selenium.Cookie> listNotAllowedCookies = getNotAllowedCookies(pageCookies);
		return Pair.of(listNotAllowedCookies.size()==0, listNotAllowedCookies);
	}
	
	List<org.openqa.selenium.Cookie> getNotAllowedCookies(
			Set<org.openqa.selenium.Cookie> pageCookies) {
		return pageCookies.stream()
		        .filter(s -> notAllowed(s))
		        .collect(Collectors.toList());
	}
	
	private boolean notAllowed(org.openqa.selenium.Cookie cookie) {
		if (allowedCookies.isEmpty()) {
			return false;
		}
		return allowedCookies.get().stream()
		        .filter(c -> c.getCookieName().compareTo(cookie.getName())==0)
		        .findAny().isEmpty();
	}
	
}
