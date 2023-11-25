package com.mng.robotest.tests.domains.availability.exceptions;

public class CatalogsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CatalogsNotFoundException(String message, Exception e) {
        super(message, e);
    }
	
	public CatalogsNotFoundException(String message) {
        super(message);
    }
	
}
