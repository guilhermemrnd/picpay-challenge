package com.gmrnd.picpay.modules.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gmrnd.picpay.modules.wallet.infra.WalletRepository;
import com.gmrnd.picpay.modules.wallet.usecases.CreateWalletUseCase;

@Configuration
public class WalletConfig {
  private final WalletRepository walletRepository;

  public WalletConfig(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  @Bean
  CreateWalletUseCase createWalletUseCase() {
    return new CreateWalletUseCase(walletRepository);
  }
}
