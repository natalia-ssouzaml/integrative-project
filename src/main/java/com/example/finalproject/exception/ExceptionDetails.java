package com.example.finalproject.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionDetails {
    private String title;
    private int status;
    private String message;
    private LocalDateTime timeStamp;
}
