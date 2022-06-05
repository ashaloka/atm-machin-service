package com.atm.machin.exceptions;

public class AtmMachinException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AtmMachinException(String messageKey, Throwable t) {
		super(messageKey, t);
	}

	public AtmMachinException(String messageKey) {
		super(messageKey, null);
	}
}
