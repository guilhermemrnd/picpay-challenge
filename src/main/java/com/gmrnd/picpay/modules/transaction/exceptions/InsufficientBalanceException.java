package com.gmrnd.picpay.modules.transaction.exceptions;

import com.gmrnd.picpay.shared.AppException;
import com.gmrnd.picpay.shared.HttpStatus;

public class InsufficientBalanceException extends AppException {
  public static final int STATUS = HttpStatus.BAD_REQUEST.getCode();

  public InsufficientBalanceException() {
    super("The amount to be transferred cannot be greater than the balance");
  }
}
