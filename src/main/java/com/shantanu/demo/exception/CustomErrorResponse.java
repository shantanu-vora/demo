package com.shantanu.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomErrorResponse {

    private Long timestamp;
    private int status;
    private String error;
    private String message;

}
