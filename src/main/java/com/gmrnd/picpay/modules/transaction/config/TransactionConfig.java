package com.gmrnd.picpay.modules.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gmrnd.picpay.modules.transaction.infra.TransactionRepository;
import com.gmrnd.picpay.modules.transaction.services.AuthorizationService;
import com.gmrnd.picpay.modules.transaction.services.NotificationService;
import com.gmrnd.picpay.modules.transaction.usecases.TransferUseCase;
import com.gmrnd.picpay.modules.wallet.infra.WalletRepository;

@Configuration
public class TransactionConfig {

  @Bean
  TransferUseCase transferUseCase(
      TransactionRepository transactionRepo,
      AuthorizationService authorizationService,
      NotificationService notificationService,
      WalletRepository walletRepo) {
    return new TransferUseCase(transactionRepo, authorizationService, notificationService, walletRepo);
  }
}
