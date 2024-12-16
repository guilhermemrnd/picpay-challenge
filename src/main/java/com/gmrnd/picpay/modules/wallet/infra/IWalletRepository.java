package com.gmrnd.picpay.modules.wallet.infra;

public interface IWalletRepository {
  Boolean exists(String email, String cpfCnpj);
  Wallet save(Wallet wallet);
}
