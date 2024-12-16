package com.gmrnd.picpay.infra;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gmrnd.picpay.PostgresTestContainer;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class WalletRepositoryTest implements PostgresTestContainer {
  @Autowired
  private WalletRepository walletRepo;

  @Autowired
  private JpaWalletRepository jpaWalletRepo;

  @Test
  public void shouldReturnTrueWhenWalletExists() {
    Wallet wallet = mockWallet();
    jpaWalletRepo.save(wallet);

    assertTrue(walletRepo.exists(
        wallet.getEmail(), wallet.getCpfCnpj()));
  }

  @Test
  public void shouldReturnFalseWhenWalletDoesNotExist() {
    assertFalse(walletRepo.exists("test@test.com", "88576887045"));
  }

  @Test
  public void shouldSaveWalletSuccessfully() {
    Wallet wallet = mockWallet();
    Wallet savedWallet = walletRepo.save(wallet);

    assertNotNull(savedWallet.getId());
    assertEquals(wallet.getFullName(), savedWallet.getFullName());
    assertEquals(wallet.getCpfCnpj(), savedWallet.getCpfCnpj());
    assertEquals(wallet.getEmail(), savedWallet.getEmail());
  }

  private Wallet mockWallet() {
    return new Wallet("Test Wallet", "88576887045", "test@test.com", "P@ssw0rd", WalletType.Enum.USER.get());
  }
}
