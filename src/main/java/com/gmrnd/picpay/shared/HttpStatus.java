package com.gmrnd.picpay.shared;

public enum HttpStatus {
  OK(200),
  CREATED(201),
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  FORBIDDEN(403),
  NOT_FOUND(404),
  CONFLICT(409),
  INTERNAL_SERVER_ERROR(500);

  private final int code;

  HttpStatus(int value) {
    this.code = value;
  }

  public int getCode() {
    return code;
  }
}
