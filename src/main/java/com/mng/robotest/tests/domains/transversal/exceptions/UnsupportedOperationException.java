package com.mng.robotest.tests.domains.transversal.exceptions;

public class UnsupportedOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public UnsupportedOperationException() {
        super();
    }

    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedOperationException(Throwable cause) {
        super(cause);
    }

}
