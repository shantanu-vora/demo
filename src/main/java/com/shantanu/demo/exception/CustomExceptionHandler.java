//package com.shantanu.demo.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler
//    public ResponseEntity<CustomErrorResponse> handleException(Exception exception) {
//        System.out.println("Violation:" + exception.getClass());
//        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
//        customErrorResponse.setStatus(HttpStatus.FORBIDDEN.value());
//        customErrorResponse.setMessage(exception.getMessage());
//        customErrorResponse.setTimestamp(System.currentTimeMillis());
//        return new ResponseEntity<>(customErrorResponse, HttpStatus.FORBIDDEN);
//    }
//}
