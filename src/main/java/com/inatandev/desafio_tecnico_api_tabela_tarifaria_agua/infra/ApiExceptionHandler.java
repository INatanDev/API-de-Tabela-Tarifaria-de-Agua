package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.infra;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(IllegalArgumentException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Regra de negócio inválida", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> campos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() == null ? "Valor inválido" : fieldError.getDefaultMessage(),
                        (mensagem1, mensagem2) -> mensagem1,
                        LinkedHashMap::new
                ));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("erro", "Dados inválidos");
        body.put("mensagem", "Um ou mais campos estão inválidos");
        body.put("path", request.getRequestURI());
        body.put("campos", campos);

        return ResponseEntity.badRequest().body(body);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String erro,
            String mensagem,
            String path
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", status.value());
        body.put("erro", erro);
        body.put("mensagem", mensagem);
        body.put("path", path);

        return ResponseEntity.status(status).body(body);
    }
}