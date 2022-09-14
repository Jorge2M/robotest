package com.mng.robotest.domains.cookiescheck.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.cookiescheck.entities.Cookie;

public class CookiesChecker {

	private final Optional<List<Cookie>> allowedCookies;
	private final Optional<List<Pattern>> whiteList; 
	
	public CookiesChecker() {
		allowedCookies = new CookiesFinder().getAllowedCookies();
		whiteList = Optional.of(
				Arrays.asList(
						//Pattern.compile("_ga_NOTFORGA4TRACKING"), 
						//Pattern.compile("_ga_NC306DXESG"),
						Pattern.compile("_ga_.*"),
						Pattern.compile("MangoHQ"),
						//Pattern.compile("_dc_gtm_UA-855910-34"),
						Pattern.compile("_dc_gtm_.*"),
						Pattern.compile("JSESSIONIDPRE")));
	}
	
	//For UnitTest purposes
	public CookiesChecker(List<Cookie> allowedCookies, List<Pattern> whiteList) {
		this.allowedCookies = Optional.of(allowedCookies);
		this.whiteList = Optional.of(whiteList);
	}
	
	public Pair<Boolean, List<org.openqa.selenium.Cookie>> check(WebDriver driver) {
		Set<org.openqa.selenium.Cookie> pageCookies = driver.manage().getCookies();
		List<org.openqa.selenium.Cookie> listNotAllowedCookies = getNotAllowedCookies(pageCookies);
		return Pair.of(listNotAllowedCookies.isEmpty(), listNotAllowedCookies);
	}
	
	List<org.openqa.selenium.Cookie> getNotAllowedCookies(
			Set<org.openqa.selenium.Cookie> pageCookies) {
		return pageCookies.stream()
		        .filter(c -> !isAllowed(c))
		        .collect(Collectors.toList());
	}
	
	private boolean isAllowed(org.openqa.selenium.Cookie cookie) {
		if (allowedCookies.isEmpty()) {
			return true;
		}
		if (isCookieInWhiteList(cookie)) {
			return true;
		}
		return isCookieInListAllowed(cookie);		
	}
	
	private boolean isCookieInWhiteList(org.openqa.selenium.Cookie cookie) {
		return whiteList.get().stream()
				.filter(w -> cookieInPattern(cookie, w))
				.findAny().isPresent();
	}
	
	private boolean isCookieInListAllowed(org.openqa.selenium.Cookie cookie) {
		return allowedCookies.get().stream()
		        .filter(c -> c.getCookieName().compareTo(cookie.getName())==0)
		        .findAny().isPresent();		
	}
	
	private boolean cookieInPattern(org.openqa.selenium.Cookie cookie, Pattern whiteItem) {
		Matcher matcher = whiteItem.matcher(cookie.getName());
		return matcher.matches();
	}
}
