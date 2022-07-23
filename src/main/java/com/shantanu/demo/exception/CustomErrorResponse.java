package com.shantanu.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomErrorResponse {

    private int status;
    private String message;
    private Long timestamp;
}
