package com.shantanu.demo.exception;

//import org.hibernate.exception.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception) {
			ApiError apiError = new ApiError();
			apiError.setStatus(HttpStatus.BAD_REQUEST.value());
			apiError.setError(HttpStatus.BAD_REQUEST);
			apiError.setMessage("Invalid Path Variable");
			apiError.setTimestamp(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiError> handleHttpClientErrorException(HttpClientErrorException exception) {
		System.out.println(exception.getStatusCode());
		ApiError apiError = new ApiError();
		apiError.setStatus(HttpStatus.NOT_FOUND.value());
		apiError.setMessage("Resource with the given id not found");
		apiError.setError(HttpStatus.NOT_FOUND);
		apiError.setTimestamp(LocalDateTime.now());;
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
//		System.out.println(exception.getStatusCode());
		ApiError apiError = new ApiError();
		apiError.setStatus(HttpStatus.CONFLICT.value());
		apiError.setMessage(exception.getMessage());
		apiError.setError(HttpStatus.CONFLICT);
		apiError.setTimestamp(LocalDateTime.now());;
		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}
}
