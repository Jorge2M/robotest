package com.mng.robotest.tests.domains.registro.exceptions;

public class CustomerNotCreatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotCreatedException(String message, Exception e) {
        super(message, e);
    }
	
	public CustomerNotCreatedException(String message) {
        super(message);
    }
	
}
