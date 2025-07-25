package com.web.exception;

import com.web.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // 400
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // có thể lấy từ header nếu trace xuyên suốt

        log.warn("[{}] FAIL handleInvalidDataException | reason={} | path={}", traceId, ex.getMessage(), path);

        Map<String, String> errorResponse = Map.of("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Có thể lấy từ header/MDC nếu cần đồng bộ traceId

        log.warn("[{}] FAIL handleUnauthorizedException | reason={} | path={}", traceId, ex.getMessage(), path);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value()); // 401
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());
        body.put("path", path);
        body.put("traceId", traceId);

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }


    // 403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Bạn có thể thay thế bằng MDC.get("traceId") nếu dùng MDC

        log.warn("[{}] FAIL handleForbiddenException | reason={} | path={}", traceId, ex.getMessage(), path);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.FORBIDDEN.value()); // 403
        body.put("error", "Forbidden");
        body.put("message", ex.getMessage());
        body.put("path", path);
        body.put("traceId", traceId);

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }


    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Hoặc lấy từ MDC nếu bạn triển khai trace xuyên suốt

        log.warn("[{}] FAIL handleResourceNotFoundException | reason={} | path={}", traceId, ex.getMessage(), path);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", path);
        body.put("traceId", traceId);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    // ===== CÁC HANDLER CHUNG HƠN (LỚP CHA) =====
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Hoặc lấy từ MDC nếu bạn triển khai traceId toàn cục

        log.warn("[{}] FAIL handleEntityNotFound | reason={} | path={}", traceId, ex.getMessage(), path);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", path);
        body.put("traceId", traceId);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Invalid Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Void>> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Nếu có MDC thì dùng MDC.get("traceId")

        // Gộp tất cả lỗi field lại
        String errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.warn("[{}] FAIL handleValidationErrors | path={} | reason={}", traceId, path, errorMessages);

        return ResponseEntity
                .badRequest()
                .body(ResponseResult.failure(errorMessages));
    }


    // JSON Syntax
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseResult<Void>> handleInvalidJson(HttpMessageNotReadableException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Hoặc MDC.get("traceId") nếu bạn dùng MDC

        String message = "Invalid JSON syntax or wrong data type";

        log.warn("[{}] FAIL handleInvalidJson | path={} | reason={}", traceId, path, message);

        return ResponseEntity
                .badRequest()
                .body(ResponseResult.failure(message));
    }
    // Data Integrity Violation
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseResult<Void>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Hoặc lấy từ MDC nếu đã tích hợp MDC logging

        String message = "trung du lieu (Data Integrity Violation)";

        log.warn("[{}] Data integrity violation at path={} | message={}", traceId, path, ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseResult.failure(message));
    }


    // Already Exists Error
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseResult<Object>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // hoặc lấy từ MDC nếu bạn dùng traceId xuyên suốt

        log.warn("[{}] FAIL handleResourceAlreadyExists | path={} | reason={}", traceId, path, ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ResponseResult.failure(ex.getMessage()));
    }

    // All HANDLER Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String traceId = UUID.randomUUID().toString(); // Hoặc dùng MDC.get("traceId") nếu bạn có

        log.error("[{}] FAIL handleGeneralException | path={} | reason={}", traceId, path, ex.getMessage(), ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        body.put("error", "Internal Server Error");
        body.put("message", "Đã có lỗi không mong muốn xảy ra ở máy chủ.");
        body.put("path", path);
        body.put("traceId", traceId);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}