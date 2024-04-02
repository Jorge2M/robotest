package com.mng.robotest.tests.domains.registro.exceptions;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String message, Exception e) {
        super(message, e);
    }
	
	public CustomerNotFoundException(String message) {
        super(message);
    }
	
}
