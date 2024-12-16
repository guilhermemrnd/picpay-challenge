package com.gmrnd.picpay.shared;

import java.util.HashMap;
import java.util.Map;

public class AppException extends RuntimeException {
  private final int status;
  private final String errorType;
  private final Map<String, Object> context;

  public AppException(String message, Map<String, Object> context) {
    super(message);
    this.errorType = this.getClass().getSimpleName();
    this.status = this.getStatus();
    this.context = context != null ? context : new HashMap<>();
  }

  public AppException(String message) {
    this(message, new HashMap<>());
  }

  protected int getStatus() {
    try {
      return (int) this.getClass().getField("STATUS").get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return HttpStatus.INTERNAL_SERVER_ERROR.getCode();
    }
  }

  public int getStatusCode() {
    return this.status;
  }

  public String getErrorType() {
    return this.errorType;
  }

  public Map<String, Object> getContext() {
    return this.context;
  }
}
