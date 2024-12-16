package com.gmrnd.picpay.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.gmrnd.picpay.controller.dtos.CreateWalletDto;
import com.gmrnd.picpay.infra.IWalletRepository;
import com.gmrnd.picpay.infra.Wallet;
import com.gmrnd.picpay.infra.WalletType;
import com.gmrnd.picpay.usecases.errors.CreateWalletErrors;

public class CreateWalletUseCaseTest {
  private IWalletRepository walletRepo;
  private CreateWalletUseCase createWalletUseCase;

  @BeforeEach
  public void setUp() {
    walletRepo = Mockito.mock(IWalletRepository.class);
    createWalletUseCase = new CreateWalletUseCase(walletRepo);
  }

  @Test
  public void shouldThrowExceptionWhenWalletAlreadyExists() {
    var props = new CreateWalletDto("Test Test", "12345678912", "test@test.com", "p@ssw0rd", WalletType.Enum.USER);

    when(walletRepo.exists(props.email(), props.cpfCnpj())).thenReturn(true);

    assertThrows(CreateWalletErrors.WalletAlreadyExists.class,
        () -> createWalletUseCase.execute(props));
    verify(walletRepo, never()).save(any());
  }

  @Test
  public void shouldCreateWalletSuccessfully() {
    var props = new CreateWalletDto("Test Test", "12345678912", "test@test.com", "p@ssw0rd", WalletType.Enum.USER);

    when(walletRepo.exists(props.email(), props.cpfCnpj())).thenReturn(false);
    when(walletRepo.save(any())).thenReturn(new Wallet());

    createWalletUseCase.execute(props);

    verify(walletRepo, times(1)).save(any());
  }
}
