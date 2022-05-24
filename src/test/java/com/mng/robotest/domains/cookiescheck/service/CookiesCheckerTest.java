package com.mng.robotest.domains.cookiescheck.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.mng.robotest.domains.cookiescheck.entities.Cookie;

public class CookiesCheckerTest {

	@Test
	public void testExistsCookiesNotAllowed() {
		var allowedCookies = mockAllowedCookies(Arrays.asList(
				"Cookie1", "Cookie2", "Cookie3"));
		var pageCookies = mockPageCookies(Arrays.asList(
				"Cookie1", "Cookie4", "Cookie5"));
		
		var checker = new CookiesChecker(allowedCookies);
		var notAllowedCookies = checker.getNotAllowedCookies(pageCookies);
		
		assertTrue(notAllowedCookies.size()==2);
		
		List<String> notAllowedNames = Arrays.asList(
				notAllowedCookies.get(0).getName(),
				notAllowedCookies.get(1).getName());
		
		assertTrue(notAllowedNames.contains("Cookie4"));
		assertTrue(notAllowedNames.contains("Cookie5"));
	}
	
	@Test
	public void testNotExistsCookiesNotAllowed() {
		var allowedCookies = mockAllowedCookies(Arrays.asList(
				"Cookie1", "Cookie2", "Cookie3"));
		var pageCookies = mockPageCookies(Arrays.asList(
				"Cookie1", "Cookie2"));
		
		var checker = new CookiesChecker(allowedCookies);
		var notAllowedCookies = checker.getNotAllowedCookies(pageCookies);
		
		assertTrue(notAllowedCookies.size()==0);
	}
	
	private List<Cookie> mockAllowedCookies(List<String> cookies) {
		return cookies.stream()
		        .flatMap(c -> Stream.of(Cookie.from(c)))
		        .collect(Collectors.toList());
	}
	
	private Set<org.openqa.selenium.Cookie> mockPageCookies(List<String> cookies) {
		return cookies.stream()
		        .flatMap(c -> Stream.of(new org.openqa.selenium.Cookie(c, "value", "path", Date.from(Instant.now()))))
		        .collect(Collectors.toSet());
	}
}
