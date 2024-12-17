package com.gmrnd.picpay.modules.transaction.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gmrnd.picpay.modules.transaction.dtos.TransactionDto;
import com.gmrnd.picpay.modules.transaction.exceptions.TransferUnauthorizedException;
import com.gmrnd.picpay.modules.transaction.infra.ITransactionRepository;
import com.gmrnd.picpay.modules.transaction.infra.Transaction;
import com.gmrnd.picpay.modules.transaction.services.IAuthorizationService;
import com.gmrnd.picpay.modules.transaction.services.INotificationService;
import com.gmrnd.picpay.modules.wallet.exceptions.WalletNotFoundException;
import com.gmrnd.picpay.modules.wallet.infra.IWalletRepository;
import com.gmrnd.picpay.modules.wallet.infra.Wallet;
import com.gmrnd.picpay.modules.wallet.infra.WalletType;

@ExtendWith(MockitoExtension.class)
public class TransferUseCaseTest {
  @Mock
  private ITransactionRepository transactionRepo;

  @Mock
  private IAuthorizationService authorizationService;

  @Mock
  private INotificationService notificationService;

  @Mock
  private IWalletRepository walletRepo;

  private TransferUseCase transferUseCase;

  @BeforeEach
  void setUp() {
    transferUseCase = new TransferUseCase(transactionRepo, authorizationService, notificationService, walletRepo);
  }

  @Test
  public void shouldTransferSuccessfully() {
    var dto = mockTransactionDto();

    var payerWallet = mockWallet(WalletType.Enum.USER);
    var payeeWallet = mockWallet(WalletType.Enum.MERCHANT);

    when(walletRepo.findById(dto.payer())).thenReturn(Optional.of(payerWallet));
    when(walletRepo.findById(dto.payee())).thenReturn(Optional.of(payeeWallet));
    when(authorizationService.isAuthorized(any(Transaction.class))).thenReturn(true);

    var result = transferUseCase.execute(dto);

    assertNotNull(result);
    assertEquals(dto.value(), result.getValue());
    assertEquals(dto.payer(), dto.payer());
    assertEquals(dto.payee(), dto.payee());

    verify(notificationService).sendNotification(any(Transaction.class));
  }

  @Test
  public void shouldThrowWhenPayerWalletNotFound() {
    var dto = mockTransactionDto();

    when(walletRepo.findById(dto.payer())).thenReturn(Optional.empty());

    assertThrows(WalletNotFoundException.class, () -> transferUseCase.execute(dto));
  }

  @Test
  public void shouldThrowWhenPayeeWalletNotFound() {
    var dto = mockTransactionDto();

    var payerWallet = mockWallet(WalletType.Enum.USER);

    when(walletRepo.findById(dto.payer())).thenReturn(Optional.of(payerWallet));
    when(walletRepo.findById(dto.payee())).thenReturn(Optional.empty());

    assertThrows(WalletNotFoundException.class, () -> transferUseCase.execute(dto));
  }

  @Test
  public void shouldThrowIfAuthorizationFails() {
    var dto = mockTransactionDto();

    var payerWallet = mockWallet(WalletType.Enum.USER);
    var payeeWallet = mockWallet(WalletType.Enum.MERCHANT);

    when(walletRepo.findById(dto.payer())).thenReturn(Optional.of(payerWallet));
    when(walletRepo.findById(dto.payee())).thenReturn(Optional.of(payeeWallet));
    when(authorizationService.isAuthorized(any(Transaction.class))).thenReturn(false);

    assertThrows(TransferUnauthorizedException.class, () -> transferUseCase.execute(dto));
  }

  private TransactionDto mockTransactionDto() {
    return new TransactionDto(new BigDecimal("100.00"), UUID.randomUUID(), UUID.randomUUID());
  }

  private Wallet mockWallet(WalletType.Enum walletType) {
    var wallet = new Wallet("Test Wallet", "12345678910", "test@test.com", "P@ssw0rd", walletType.get());
    wallet.credit(new BigDecimal("1000.00"));
    return wallet;
  }
}
