package com.example.micrologin.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.micrologin.model.CustomApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomApiError error = new CustomApiError(HttpStatus.BAD_REQUEST.value(), "Malformed JSON request");

		return buildResponseEntity(Arrays.asList(error), status);
	}

	@ExceptionHandler(UserValidationException.class)
	protected ResponseEntity<Object> handleUserValidationException(UserValidationException ex) {
		List<CustomApiError> errors = ex.getErrorList().stream()
				.map(errorMessage -> new CustomApiError(HttpStatus.BAD_REQUEST.value(), errorMessage))
				.collect(Collectors.toList());
		
		return buildResponseEntity(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	protected ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

		CustomApiError error = new CustomApiError(HttpStatus.CONFLICT.value(), ex.getMessage());

		return buildResponseEntity(Arrays.asList(error), HttpStatus.CONFLICT);
	}

	private ResponseEntity<Object> buildResponseEntity(List<CustomApiError> errors, HttpStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", errors);
		
		return new ResponseEntity<Object>(map, status);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex) {
		CustomApiError error = new CustomApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
		
		return buildResponseEntity(Arrays.asList(error), HttpStatus.INTERNAL_SERVER_ERROR);		
	}

}
