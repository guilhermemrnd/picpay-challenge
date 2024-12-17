package com.gmrnd.picpay.modules.wallet.exceptions;

import com.gmrnd.picpay.shared.AppException;
import com.gmrnd.picpay.shared.HttpStatus;

public class WalletAlreadyExistsException extends AppException {
  public static final int STATUS = HttpStatus.CONFLICT.getCode();

  public WalletAlreadyExistsException() {
    super("Wallet already exists for the given email or CPF/CNPJ");
  }

}
