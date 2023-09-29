package com.mng.robotest.tests.domains.cookiescheck.exceptions;

public class IrretrievableCookies extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IrretrievableCookies(Exception e) {
        super("Cookies could not be retrieved: " + e);
    }
	
	public IrretrievableCookies(String message) {
        super(message);
    }
	
}
