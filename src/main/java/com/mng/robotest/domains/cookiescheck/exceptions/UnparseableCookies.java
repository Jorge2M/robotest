package com.mng.robotest.domains.cookiescheck.exceptions;

import java.io.IOException;

public class UnparseableCookies extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnparseableCookies(IOException e) {
        super("Cookies information could not be parsed: " + e);
    }
	
}
