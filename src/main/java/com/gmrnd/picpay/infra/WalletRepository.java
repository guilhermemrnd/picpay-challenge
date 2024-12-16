package com.gmrnd.picpay.infra;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class WalletRepository implements IWalletRepository {

  private final JpaWalletRepository jpaWalletRepo;

  public WalletRepository(JpaWalletRepository jpaWalletRepo) {
    this.jpaWalletRepo = jpaWalletRepo;
  }

  public Boolean exists(String email, String cpfCnpj) {
    return jpaWalletRepo.existsByEmailOrCpfCnpj(email, cpfCnpj);
  }

  @Transactional
  public Wallet save(Wallet wallet) {
    try {
      return jpaWalletRepo.save(wallet);
    } catch (Exception e) {
      throw new RuntimeException("Failed to save wallet", e);
    }
  }
}