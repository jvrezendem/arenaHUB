package com.arenahub.arenahub.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.arenahub.arenahub.dto.response.ErrorResponseDto;
import com.arenahub.arenahub.exception.EmailJaCadastradoException;
import com.arenahub.arenahub.exception.GestorJaCadastradoException;
import com.arenahub.arenahub.exception.QuadraJaExisteException;
import com.arenahub.arenahub.exception.QuadraNaoEncontradaException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuadraNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDto> handleQuadraNaoEncontrada(QuadraNaoEncontradaException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailJaCadastrado(EmailJaCadastradoException e) {
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(GestorJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDto> handleGestorJaCadastrado(GestorJaCadastradoException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(QuadraJaExisteException.class)
    public ResponseEntity<ErrorResponseDto> handleQuadraJaExiste(QuadraJaExisteException e) {
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        "Credenciais Inválidas"),
                status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String mensagem = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> String.format("%s: %s", fe.getField(), fe.getDefaultMessage()))
                .orElse("Erro de validação");

        return new ResponseEntity<>(
                new ErrorResponseDto(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensagem),
                status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String mensagem = "Corpo da requisição inválido";

        String msg = e.getMostSpecificCause().getMessage();
        if (msg != null) {
            // Extrai tipo esperado: `java.time.LocalTime` -> LocalTime
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("`([^`]+)`").matcher(msg);
            if (m.find()) {
                String tipo = m.group(1).substring(m.group(1).lastIndexOf('.') + 1);

                // Mapeia tipo para dica de formato legível
                String dica = switch (tipo) {
                    case "LocalTime" -> "formato HH:mm (ex: 08:30)";
                    case "LocalDate" -> "formato yyyy-MM-dd (ex: 2026-11-19)";
                    case "LocalDateTime" -> "formato ISO 8601 (ex: 2026-11-19T08:30:00)";
                    case "BigDecimal", "Double", "Float" -> "número válido (ex: 10.50)";
                    case "Integer", "Long" -> "número inteiro válido";
                    case "Boolean" -> "true ou false";
                    case "String" -> "texto válido";
                    default -> "valor compatível com " + tipo;
                };

                mensagem = String.format("Campo com formato inválido. %s", dica);
            }
            // JSON malformado (chaves, vírgulas, aspas)
            else if (msg.contains("JSON parse error") || msg.contains("Malformed")) {
                mensagem = "JSON malformado. Verifique chaves, vírgulas e aspas";
            }
        }

        return new ResponseEntity<>(
                new ErrorResponseDto(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensagem),
                status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleInternalServerError(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        "Erro interno do servidor"),
                status);
    }

}