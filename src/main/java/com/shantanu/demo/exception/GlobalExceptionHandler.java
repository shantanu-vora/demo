package com.shantanu.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public void constraintViolationException(ConstraintViolationException exception) {

		CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
				customErrorResponse.setError(HttpStatus.BAD_REQUEST.toString());
        customErrorResponse.setMessage(exception.getMessage());
        customErrorResponse.setTimestamp(System.currentTimeMillis());
	}
}
