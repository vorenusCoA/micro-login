package com.example.micrologin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

	private static MessageSource messageSource;

	public PasswordValidator(MessageSource messageSource) {
		PasswordValidator.messageSource = messageSource;
	}

	public static List<String> validatePassword(String password) {

		List<String> errors = new ArrayList<>();

		if (password == null || password.trim().isEmpty()) {
			errors.add(messageSource.getMessage("error.password.is.blank", null, Locale.ENGLISH));
			return errors;
		}

		if (password.length() < 8 || password.length() > 12) {
			errors.add(messageSource.getMessage("error.password.length.is.wrong", null, Locale.ENGLISH));
		}

		// TODO implement more validations with regex
		
		return errors;
	}

}
