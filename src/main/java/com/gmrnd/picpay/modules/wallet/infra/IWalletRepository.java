package com.gmrnd.picpay.modules.wallet.infra;

import java.util.Optional;
import java.util.UUID;

public interface IWalletRepository {
  Boolean exists(String email, String cpfCnpj);
  Optional<Wallet> findById(UUID id);
  Wallet save(Wallet wallet);
}
