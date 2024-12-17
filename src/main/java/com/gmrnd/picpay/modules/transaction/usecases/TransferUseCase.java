package com.gmrnd.picpay.modules.transaction.usecases;

import java.util.concurrent.CompletableFuture;

import com.gmrnd.picpay.modules.transaction.dtos.TransactionDto;
import com.gmrnd.picpay.modules.transaction.exceptions.TransferUnauthorizedException;
import com.gmrnd.picpay.modules.transaction.infra.ITransactionRepository;
import com.gmrnd.picpay.modules.transaction.infra.Transaction;
import com.gmrnd.picpay.modules.transaction.services.IAuthorizationService;
import com.gmrnd.picpay.modules.transaction.services.INotificationService;
import com.gmrnd.picpay.modules.wallet.exceptions.WalletNotFoundException;
import com.gmrnd.picpay.modules.wallet.infra.IWalletRepository;

import jakarta.transaction.Transactional;

public class TransferUseCase {
  private final ITransactionRepository transactionRepo;
  private final IAuthorizationService authorizationService;
  private final INotificationService notificationService;
  private final IWalletRepository walletRepo;

  public TransferUseCase(
      ITransactionRepository transactionRepo,
      IAuthorizationService authorizationService,
      INotificationService notificationService,
      IWalletRepository walletRepo) {
    this.transactionRepo = transactionRepo;
    this.authorizationService = authorizationService;
    this.notificationService = notificationService;
    this.walletRepo = walletRepo;
  }

  @Transactional
  public Transaction execute(TransactionDto dto) {
    var payer = walletRepo.findById(dto.payer())
        .orElseThrow(() -> new WalletNotFoundException(dto.payer()));

    var payee = walletRepo.findById(dto.payee())
        .orElseThrow(() -> new WalletNotFoundException(dto.payee()));

    payer.debit(dto.value());
    payee.credit(dto.value());

    var transaction = new Transaction(dto.value(), dto.payer(), dto.payee());

    if (!authorizationService.isAuthorized(transaction)) {
      throw new TransferUnauthorizedException();
    }

    transactionRepo.save(transaction);
    walletRepo.save(payer);
    walletRepo.save(payee);

    CompletableFuture.runAsync(() -> {
      notificationService.sendNotification(transaction);
    });

    return transaction;
  }
}
