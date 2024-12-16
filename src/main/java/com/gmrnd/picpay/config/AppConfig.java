package com.gmrnd.picpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gmrnd.picpay.infra.WalletRepository;
import com.gmrnd.picpay.usecases.CreateWalletUseCase;

@Configuration
public class AppConfig {
  private final WalletRepository walletRepository;

  public AppConfig(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  @Bean
  CreateWalletUseCase createWalletUseCase() {
    return new CreateWalletUseCase(walletRepository);
  }
}
