package com.example.finalproject.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class MethodArgumentNotValidExceptionDetails {

    private String title;
    private String message;
    private String fields;
    private String fieldsMessage;
    private LocalDateTime timeStamp;
}
