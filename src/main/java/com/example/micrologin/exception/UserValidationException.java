package com.example.micrologin.exception;

import java.util.List;

public class UserValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> errors;

	public UserValidationException(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrorList() {
		return errors;
	}
}
