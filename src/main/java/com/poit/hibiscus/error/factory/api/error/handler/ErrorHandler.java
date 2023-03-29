package com.poit.hibiscus.error.factory.api.error.handler;

import com.poit.hibiscus.error.factory.api.error.dto.ErrorResponse;
import com.poit.hibiscus.error.factory.logic.operation.IErrorBuilder;
import com.poit.hibiscus.error.factory.model.CardException;
import com.poit.hibiscus.error.factory.model.SignInException;
import com.poit.hibiscus.error.factory.model.SignUpException;
import com.poit.hibiscus.error.factory.model.TransactionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {
    private final IErrorBuilder<? super RuntimeException> errorBuilder;

    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<ErrorResponse> handle(SignUpException e) {
        var error = errorBuilder.build(e);

        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(TransactionDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(TransactionDeniedException e) {
        var error = errorBuilder.build(e);

        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(SignInException.class)
    public ResponseEntity<ErrorResponse> handle(SignInException e) {
        var error = errorBuilder.build(e);

        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(CardException.class)
    public ResponseEntity<ErrorResponse> handle(CardException e) {
        var error = errorBuilder.build(e);

        return new ResponseEntity<>(error, error.getHttpStatus());
    }
}
