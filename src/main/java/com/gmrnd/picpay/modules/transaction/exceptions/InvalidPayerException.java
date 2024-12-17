package com.gmrnd.picpay.modules.transaction.exceptions;

import com.gmrnd.picpay.shared.AppException;
import com.gmrnd.picpay.shared.HttpStatus;

public class InvalidPayerException extends AppException {
  public static final int STATUS = HttpStatus.BAD_REQUEST.getCode();

  public InvalidPayerException() {
    super("A merchant account cannot make transfers");
  }
}
