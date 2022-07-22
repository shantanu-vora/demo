package com.shantanu.demo.exception;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class CustomErrorResponse {

    private int status;
    private String message;
    private Long timestamp;

    public CustomErrorResponse(int status, String message, Long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
