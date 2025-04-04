package com.company.test.exception;

import com.company.test.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Обработка исключения CardNotFoundException
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCardNotFoundException(CardNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Обработка исключения TransactionLimitException
    @ExceptionHandler(TransactionLimitException.class)
    public ResponseEntity<ErrorResponseDto> handleTransactionLimitException(TransactionLimitException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Обработка других исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
