package com.gmrnd.picpay.modules.wallet.usecases.errors;

import com.gmrnd.picpay.shared.AppException;
import com.gmrnd.picpay.shared.HttpStatus;

public class CreateWalletErrors {
  public static class WalletAlreadyExists extends AppException {
    public static final int STATUS = HttpStatus.CONFLICT.getCode();

    public WalletAlreadyExists() {
      super("Wallet already exists for the given email or CPF/CNPJ");
    }
  }
}
