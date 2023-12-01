package com.mng.robotest.tests.domains.cookiescheck.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.mng.robotest.tests.domains.cookiescheck.entities.Cookie;

public class CookiesCheckerTest {

	@Test
	public void testAreCookiesNotAllowed() {
		var allowedCookies = mockAllowedCookies(Arrays.asList(
				"Cookie1", "Cookie2", "Cookie3"));
		var pageCookies = mockPageCookies(Arrays.asList(
				"Cookie1", "Cookie4", "Cookie5"));
		
		var checker = new CookiesChecker(allowedCookies, new ArrayList<Pattern>());
		var notAllowedCookies = checker.getNotAllowedCookies(pageCookies);
		
		assertEquals(2, notAllowedCookies.size());
		
		var notAllowedNames = Arrays.asList(
				notAllowedCookies.get(0).getName(),
				notAllowedCookies.get(1).getName());
		
		assertTrue(notAllowedNames.contains("Cookie4"));
		assertTrue(notAllowedNames.contains("Cookie5"));
	}
	
	@Test
	public void testCookieInWhiteList() {
		var allowedCookies = mockAllowedCookies(Arrays.asList(
				"Cookie1", "Cookie2", "Cookie3"));
		var whiteList = mockWhiteList(Arrays.asList(
				"Cookie4.*"));
		var pageCookies = mockPageCookies(Arrays.asList(
				"Cookie1", "Cookie41", "Cookie42", "Cookie52"));
		
		var checker = new CookiesChecker(allowedCookies, whiteList);
		var notAllowedCookies = checker.getNotAllowedCookies(pageCookies);
		
		assertEquals(1, notAllowedCookies.size());
		assertTrue(notAllowedCookies.get(0).getName().contains("Cookie52"));
	}	
	
	@Test
	public void testAllCookiesAllowed() {
		var allowedCookies = mockAllowedCookies(Arrays.asList(
				"Cookie1", "Cookie2", "Cookie3"));
		var pageCookies = mockPageCookies(Arrays.asList(
				"Cookie1", "Cookie2"));
		
		var checker = new CookiesChecker(allowedCookies, new ArrayList<Pattern>());
		var notAllowedCookies = checker.getNotAllowedCookies(pageCookies);
		
		assertEquals(0, notAllowedCookies.size());
	}
	
	@Test
	public void testCookiesByPattern() {
		var allowedCookies = mockAllowedCookies(Arrays.asList(
				"_hjSession_*"));
		var pageCookies = mockPageCookies(Arrays.asList(
				"_hjSession_1003639"));
		
		var checker = new CookiesChecker(allowedCookies, new ArrayList<Pattern>());
		var notAllowedCookies = checker.getNotAllowedCookies(pageCookies);
		
		assertEquals(0, notAllowedCookies.size());
	}	
	
	private List<Cookie> mockAllowedCookies(List<String> cookies) {
		return cookies.stream()
		        .flatMap(c -> Stream.of(Cookie.from(c)))
				.toList();
	}
	
	private List<Pattern> mockWhiteList(List<String> whiteList) {
		return whiteList.stream()
		        .flatMap(w -> Stream.of(Pattern.compile(w)))
		        .toList();
	}
	
	private Set<org.openqa.selenium.Cookie> mockPageCookies(List<String> cookies) {
		return cookies.stream()
		        .flatMap(c -> Stream.of(new org.openqa.selenium.Cookie(c, "value", "path", Date.from(Instant.now()))))
		        .collect(Collectors.toSet());
	}
}
