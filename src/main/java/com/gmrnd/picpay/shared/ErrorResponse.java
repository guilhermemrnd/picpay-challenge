package com.gmrnd.picpay.shared;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;

@Getter
public class ErrorResponse {
  private final int status;
  private final String message;
  private final String path;
  private final String timestamp;
  private final Map<String, Object> details;

  public ErrorResponse(int status, String message, String path, Map<String, Object> details) {
    this.timestamp = LocalDateTime.now().toString();
    this.status = status;
    this.message = message;
    this.path = path;
    this.details = details;
  }
}
