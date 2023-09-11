package com.example.micrologin.exception;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public UserAlreadyExistsException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
