package com.gmrnd.picpay.modules.wallet.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWalletRepository extends JpaRepository<Wallet, UUID> {
  Boolean existsByEmailOrCpfCnpj(String email, String cpfCnpj);
}
