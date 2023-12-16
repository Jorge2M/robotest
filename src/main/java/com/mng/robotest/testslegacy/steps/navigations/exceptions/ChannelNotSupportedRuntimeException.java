package com.mng.robotest.testslegacy.steps.navigations.exceptions;

public class ChannelNotSupportedRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public ChannelNotSupportedRuntimeException(String message) {
        super(message);
    }

    public ChannelNotSupportedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
