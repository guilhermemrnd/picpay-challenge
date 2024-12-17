package com.gmrnd.picpay.modules.wallet.exceptions;

import java.util.UUID;

import com.gmrnd.picpay.shared.AppException;
import com.gmrnd.picpay.shared.HttpStatus;

public class WalletNotFoundException extends AppException {
  public static final int STATUS = HttpStatus.NOT_FOUND.getCode();

  public WalletNotFoundException(UUID walletId) {
    super(String.format("Wallet with id %s not found", walletId));
  }
}
