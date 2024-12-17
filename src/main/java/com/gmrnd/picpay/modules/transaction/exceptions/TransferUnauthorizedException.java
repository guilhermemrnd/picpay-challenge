package com.gmrnd.picpay.modules.transaction.exceptions;

import com.gmrnd.picpay.shared.AppException;
import com.gmrnd.picpay.shared.HttpStatus;

public class TransferUnauthorizedException extends AppException {
  public static final int STATUS = HttpStatus.UNAUTHORIZED.getCode();

  public TransferUnauthorizedException() {
    super("The transaction was not authorized");
  }
}
