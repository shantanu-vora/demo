package com.shantanu.demo.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ApiError apiError;
	public static final String INVALID_PATH_VARIABLE = "Invalid Path Variable";
	public static final String RESOURCE_NOT_FOUND = "Resource with the given id not found";

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception) {
		apiError.setStatus(HttpStatus.BAD_REQUEST.value());
		apiError.setError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(INVALID_PATH_VARIABLE);
		apiError.setTimestamp(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiError> handleHttpClientErrorException(HttpClientErrorException exception) {
		apiError.setStatus(HttpStatus.NOT_FOUND.value());
		apiError.setMessage(RESOURCE_NOT_FOUND);
		apiError.setError(HttpStatus.NOT_FOUND);
		apiError.setTimestamp(LocalDateTime.now());;
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
		apiError.setStatus(HttpStatus.CONFLICT.value());
		apiError.setMessage(exception.getMessage());
		apiError.setError(HttpStatus.CONFLICT);
		apiError.setTimestamp(LocalDateTime.now());;
		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		apiError.setStatus(status.value());
		apiError.setMessage(ex.getMessage());
		apiError.setError(status);
		apiError.setTimestamp(LocalDateTime.now());
		return ResponseEntity.status(status).body(apiError);
	}
}
