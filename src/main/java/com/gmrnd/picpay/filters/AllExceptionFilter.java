package com.gmrnd.picpay.filters;

import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmrnd.picpay.shared.AppException;
import com.gmrnd.picpay.shared.ErrorResponse;
import com.gmrnd.picpay.shared.HttpStatus;

@ControllerAdvice
public class AllExceptionFilter {
  private final static Logger logger = LoggerFactory.getLogger(AllExceptionFilter.class);
  private final ObjectMapper objectMapper;

  public AllExceptionFilter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ErrorResponse> handleAppError(AppException appEx, WebRequest request) {
    var details = new HashMap<String, Object>();
    details.put("errorType", appEx.getErrorType());
    details.put("context", appEx.getContext());

    var errResponse = new ErrorResponse(
        appEx.getStatusCode(),
        appEx.getMessage(),
        getPath(request),
        details);

    logException(appEx.getStatusCode(), appEx.getMessage(), request, appEx);
    return ResponseEntity.status(appEx.getStatusCode()).body(errResponse);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
    int status = ex.getStatusCode().value();
    String message = getMessage(ex, status);

    var errResponse = new ErrorResponse(
        status,
        message,
        getPath(request),
        new HashMap<>());

    logException(status, message, request, ex);
    return ResponseEntity.status(status).body(errResponse);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
    int status = HttpStatus.INTERNAL_SERVER_ERROR.getCode();
    String message = "Internal server error";

    var errResponse = new ErrorResponse(
        status,
        message,
        getPath(request),
        new HashMap<>());

    logException(status, message, request, ex);
    return ResponseEntity.status(status).body(errResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
      WebRequest request) {
    var fieldErrors = ex.getFieldErrors().stream()
        .map((f) -> new InvalidField(f.getField(), f.getDefaultMessage()))
        .toList();

    int status = HttpStatus.BAD_REQUEST.getCode();
    String message = "Your request parameters didn't validate";
    var details = new HashMap<String, Object>();
    details.put("invalid-params", fieldErrors);

    var errResponse = new ErrorResponse(
        status,
        message,
        getPath(request),
        details);

    logException(status, message, request, ex);
    return ResponseEntity.status(status).body(errResponse);
  }

  private void logException(int status, String message, WebRequest request, Exception ex) {
    try {
      var logEntry = new HashMap<>();
      logEntry.put("timestamp", getCurrentTimestamp());
      logEntry.put("ip", getClientIp(request));
      logEntry.put("userAgent", getUserAgent(request));
      logEntry.put("description", String.format(
          "Incoming request on %s %s failed with status %s",
          request.getHeader("method"),
          getPath(request),
          status));
      logEntry.put("message", message);

      String logLevel = getLogLevel(status);
      String jsonLog = objectMapper.writeValueAsString(logEntry);

      if (logLevel.equals("WARN")) {
        logger.warn(jsonLog);
      } else {
        logger.error(ex.getMessage(), ex);
        logger.error(jsonLog);
      }
    } catch (Exception e) {
      logger.error("Failed to convert log to JSON", e);
    }
  }

  private String getLogLevel(int status) {
    return status >= HttpStatus.INTERNAL_SERVER_ERROR.getCode() ? "ERROR" : "WARN";
  }

  private String getMessage(Exception ex, int status) {
    return status >= HttpStatus.INTERNAL_SERVER_ERROR.getCode() ? "Internal server error" : ex.getMessage();
  }

  private String getClientIp(WebRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isEmpty()) {
      ip = request.getHeader("Remote-Addr");
    }
    return ip != null ? ip : "UNKNOWN";
  }

  private String getUserAgent(WebRequest request) {
    return Objects.requireNonNullElse(
        request.getHeader("User-Agent"), "UNKNOWN");
  }

  private String getPath(WebRequest request) {
    return request.getDescription(false).replace("uri=", "");
  }

  private String getCurrentTimestamp() {
    return Instant.now().toString();
  }

  private record InvalidField(String field, String message) {
  }
}
