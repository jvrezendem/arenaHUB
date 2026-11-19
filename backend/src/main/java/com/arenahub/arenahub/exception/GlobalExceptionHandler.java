package com.arenahub.arenahub.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.arenahub.arenahub.dto.response.ErrorResponseDto;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(QuadraNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDto> handleQuadraNaoEncontrada(QuadraNaoEncontradaException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
            new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage()
            ), status);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailJaCadastrado(EmailJaCadastradoException e){
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
            new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage()
            ), status);
    }

    @ExceptionHandler(QuadraJaExisteException.class)
    public ResponseEntity<ErrorResponseDto> handleQuadraJaExiste(QuadraJaExisteException e){
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
            new ErrorResponseDto(
                LocalDateTime.now(), 
                status.value(), 
                status.getReasonPhrase(),
                e.getMessage()
            ), status);
    }
}