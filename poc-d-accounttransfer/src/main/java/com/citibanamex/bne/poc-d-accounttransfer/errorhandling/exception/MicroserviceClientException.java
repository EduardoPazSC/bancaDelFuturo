package com.citibanamex.bne.poc-d-accounttransfer.errorhandling.exception;

public class MicroserviceClientException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1243243559832908014L;

	public MicroserviceClientException() {
		super();
	}

	public MicroserviceClientException(String message) {
		super(message);
	}

	public MicroserviceClientException(Throwable throwable) {
		super(throwable);
	}

}

