package com.arenahub.arenahub.handler;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.arenahub.arenahub.dto.response.ErrorResponseDto;
import com.arenahub.arenahub.exception.EmailJaCadastradoException;
import com.arenahub.arenahub.exception.QuadraJaExisteException;
import com.arenahub.arenahub.exception.QuadraNaoEncontradaException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Pattern TIPO_ESPERADO = Pattern.compile("`([^`]+)`");

    @ExceptionHandler(QuadraNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDto> handleQuadraNaoEncontrada(QuadraNaoEncontradaException e) {
        return montarResposta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailJaCadastrado(EmailJaCadastradoException e) {
        return montarResposta(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(QuadraJaExisteException.class)
    public ResponseEntity<ErrorResponseDto> handleQuadraJaExiste(QuadraJaExisteException e) {
        return montarResposta(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException e) {
        return montarResposta(HttpStatus.UNAUTHORIZED, "Credenciais Inválidas");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> String.format("%s: %s", fe.getField(), fe.getDefaultMessage()))
                .orElse("Erro de validação");

        return montarResposta(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return montarResposta(HttpStatus.BAD_REQUEST, descreverCorpoInvalido(e.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleInternalServerError(Exception e) {
        return montarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
    }

    private ResponseEntity<ErrorResponseDto> montarResposta(HttpStatus status, String mensagem) {
        return new ResponseEntity<>(
                new ErrorResponseDto(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensagem),
                status);
    }

    private String descreverCorpoInvalido(String causa) {
        if (causa == null) {
            return "Corpo da requisição inválido";
        }

        Matcher tipoEncontrado = TIPO_ESPERADO.matcher(causa);
        if (tipoEncontrado.find()) {
            String tipo = tipoEncontrado.group(1).substring(tipoEncontrado.group(1).lastIndexOf('.') + 1);
            return String.format("Campo com formato inválido. %s", dicaDeFormato(tipo));
        }

        if (causa.contains("JSON parse error") || causa.contains("Malformed")) {
            return "JSON malformado. Verifique chaves, vírgulas e aspas";
        }

        return "Corpo da requisição inválido";
    }

    private String dicaDeFormato(String tipo) {
        return switch (tipo) {
            case "LocalTime" -> "formato HH:mm (ex: 08:30)";
            case "LocalDate" -> "formato yyyy-MM-dd (ex: 2026-11-19)";
            case "LocalDateTime" -> "formato ISO 8601 (ex: 2026-11-19T08:30:00)";
            case "BigDecimal", "Double", "Float" -> "número válido (ex: 10.50)";
            case "Integer", "Long" -> "número inteiro válido";
            case "Boolean" -> "true ou false";
            case "String" -> "texto válido";
            default -> "valor compatível com " + tipo;
        };
    }
}
