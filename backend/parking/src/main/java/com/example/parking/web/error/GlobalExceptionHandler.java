package com.example.parking.web.error;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;
import com.example.parking.web.dto.error.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manejo centralizado de todas las excepciones custom (extienden BaseException).
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseException(BaseException ex, 
    		HttpServletRequest request) {
    	
        ErrorCatalog error = ex.getErrorCatalog();

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(error.getHttpStatus().value())
                .codigo(error.getCodigo())
                .mensaje(error.getMensaje())
                .detalles(List.of(ex.getMessage())) // Detalle adicional si lo enviamos en el constructor
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }

    /**
     * Manejo de errores de validación (@Valid / @Validated).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex, 
    		HttpServletRequest request) {
    	
        ErrorCatalog error = ErrorCatalog.VALIDATION_ERROR;

        List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(error.getHttpStatus().value())
                .codigo(error.getCodigo())
                .mensaje(error.getMensaje())
                .detalles(detalles)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }

    /**
     * Manejo de acceso denegado (Spring Security).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex, 
    		HttpServletRequest request) {
    	
        ErrorCatalog error = ErrorCatalog.ERROR_INTERNO;
        
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(error.getHttpStatus().value())
                .codigo(error.getCodigo())
                .mensaje("Acceso denegado")
                .detalles(List.of(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }

    /**
     * Manejo genérico de RuntimeException y errores inesperados.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException ex, 
    		HttpServletRequest request) {
    	
        ErrorCatalog error = ErrorCatalog.ERROR_INTERNO;

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(error.getHttpStatus().value())
                .codigo(error.getCodigo())
                .mensaje(error.getMensaje())
                .detalles(List.of(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }
}

