package com.gmrnd.picpay.infra;

public interface IWalletRepository {
  Boolean exists(String email, String cpfCnpj);
  Wallet save(Wallet wallet);
}
