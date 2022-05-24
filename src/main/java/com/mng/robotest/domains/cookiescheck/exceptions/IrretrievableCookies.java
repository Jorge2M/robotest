package com.mng.robotest.domains.cookiescheck.exceptions;

public class IrretrievableCookies extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IrretrievableCookies(Exception e) {
        super("Cookies could not be retrieved: " + e);
    }
	
}
