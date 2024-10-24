package com.mng.robotest.tests.domains.cookiesallowed.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.cookiesallowed.entities.Cookie;
import com.mng.robotest.tests.domains.cookiesallowed.services.mangoauditor.CookiesFinderMangoAuditor;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class CookiesChecker extends PageBase {

	private final Optional<List<Cookie>> allowedCookies;
	private final Optional<List<Pattern>> whiteList; 
	
	public CookiesChecker() {
		allowedCookies = new CookiesFinderMangoAuditor(inputParamsSuite.getUrlBase(), app)
				.getAllowedCookies();
		
		whiteList = Optional.of(
			new ArrayList<>(
				Arrays.asList(
						Pattern.compile("_ga_.*"),
						Pattern.compile("MangoHQ"),
						Pattern.compile("_dc_gtm_.*"),
						Pattern.compile("cloudtest-name"),
						Pattern.compile("JSESSIONID"),
						Pattern.compile("JSESSIONID2"),
						Pattern.compile("JSESSIONIDPRE"),
						Pattern.compile("JSESSIONIDPREPRE"),
						
						//Cookies del dominio www.mangofashiongroup.com que aparecen cuando se selecciona EMPRESAS del footer
						Pattern.compile("GUEST_LANGUAGE_ID"),
						Pattern.compile("LFR_SESSION_STATE_20102"),
						Pattern.compile("COOKIE_SUPPORT"),
						Pattern.compile("X-Robotest"),
						
						//Sólo aparece en Cloud con development=enabled
						Pattern.compile("disable-s3-microfrontend") 
				)));
		
		replaceAllowedCookies("_hjIncludedInSessionSample", "_hjIncludedInSessionSample*");
		replaceAllowedCookies("_hjSessionUser", "_hjSessionUser*");
		replaceAllowedCookies("_hjSession", "_hjSession*");
		
		if (UtilsTest.todayBeforeDate("2025-03-01")) {
			whiteList.get().add(Pattern.compile("AWSALBTGCORS"));
			whiteList.get().add(Pattern.compile("AWSALBTG"));
		}
		if (UtilsTest.todayBeforeDate("2025-03-01")) {
			whiteList.get().add(Pattern.compile("FPGSID"));
		}
	}
	
	//For UnitTest purposes
	public CookiesChecker(List<Cookie> allowedCookies, List<Pattern> whiteList) {
		this.allowedCookies = Optional.of(allowedCookies);
		this.whiteList = Optional.of(whiteList);
	}
	
	public Pair<Boolean, List<org.openqa.selenium.Cookie>> check() {
		var pageCookies = driver.manage().getCookies();
		var listNotAllowedCookies = getNotAllowedCookies(pageCookies);
		return Pair.of(listNotAllowedCookies.isEmpty(), listNotAllowedCookies);
	}
	
	List<org.openqa.selenium.Cookie> getNotAllowedCookies(
			Set<org.openqa.selenium.Cookie> pageCookies) {
		return pageCookies.stream()
		        .filter(c -> !isAllowed(c))
		        .toList();
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
				.anyMatch(w -> isCookieInPattern(cookie, w));
	}
	
	private boolean isCookieInListAllowed(org.openqa.selenium.Cookie cookie) {
		return allowedCookies.get().stream()
				.anyMatch(c -> 
					isSameCookie(cookie, c) || 
					isCookieInPattern(cookie, Pattern.compile(c.getCookieName().replace("*", ".*"))));		
	}
	
	private boolean isSameCookie(org.openqa.selenium.Cookie cookie, Cookie cookieAllowed) {
		return cookie.getName().compareTo(cookieAllowed.getCookieName())==0;
	}
	
	private boolean isCookieInPattern(org.openqa.selenium.Cookie cookie, Pattern whiteItem) {
		Matcher matcher = whiteItem.matcher(cookie.getName());
		return matcher.matches();
	}
	
	private void replaceAllowedCookies(String oldName, String newName) {
		if (allowedCookies.isPresent()) {
			allowedCookies.get().stream()
				.filter(c -> c.getCookieName().compareTo(oldName)==0)
				.forEach(c -> c.setCookieName(newName));
		}
	}
}
