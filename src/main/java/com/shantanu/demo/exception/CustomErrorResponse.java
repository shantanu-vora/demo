package com.shantanu.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class CustomErrorResponse {

    private Long timestamp;
    private int status;
    private HttpStatus error;
    private String message;

}
