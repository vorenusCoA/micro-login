package com.example.micrologin.utils;

import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressValidator {

	private static MessageSource messageSource;

	// https://owasp.org/www-community/OWASP_Validation_Regex_Repository
	private static final String OWASP_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	public EmailAddressValidator(MessageSource messageSource) {
		EmailAddressValidator.messageSource = messageSource;
	}

	public static String validateEmail(String email) {

		if (email == null || email.trim().isEmpty()) {
			return messageSource.getMessage("error.email.is.blank", null, Locale.ENGLISH);
		}
				
		if (!Pattern.compile(OWASP_REGEX).matcher(email).matches()) {
			return messageSource.getMessage("error.email.is.not.valid", null, Locale.ENGLISH);
		}
		
		return "";
	}

}
