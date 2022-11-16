package com.example.finalproject.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders httpHeaders,
            HttpStatus httpStatus,
            WebRequest webRequest) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        return new ResponseEntity<>(
                MethodArgumentNotValidExceptionDetails.builder()
                        .title("Parâmetros inválidos")
                        .message("Os campos estão inválidos")
                        .fields(errors.stream().map(FieldError::getField)
                                .collect(Collectors.joining(";")))
                        .fieldsMessage(errors.stream().map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining(";")))
                        .timeStamp(LocalDateTime.now())
                        .build()
                ,
                httpStatus);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerNotFoundException(NotFoundException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("Object not found")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTemperatureException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidTemperatureException(InvalidTemperatureException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("Invalid temperature")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VolumeNotAvailableException.class)
    public ResponseEntity<ExceptionDetails> handlerVolumeNotAvailableException(VolumeNotAvailableException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("Volume not available")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuantityNotAvailableException.class)
    public ResponseEntity<ExceptionDetails> handlerQuantityNotAvailableException(QuantityNotAvailableException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("Quantity not available")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PurchaseFailureException.class)
    public ResponseEntity<ExceptionDetails> handlerPurchaseFailureException(PurchaseFailureException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("Purchase failed")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
