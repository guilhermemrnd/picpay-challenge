package com.gmrnd.picpay.modules.transaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmrnd.picpay.PostgresTestContainer;
import com.gmrnd.picpay.modules.transaction.dtos.TransactionDto;
import com.gmrnd.picpay.modules.transaction.infra.Transaction;
import com.gmrnd.picpay.modules.transaction.infra.TransactionRepository;
import com.gmrnd.picpay.modules.wallet.infra.Wallet;
import com.gmrnd.picpay.modules.wallet.infra.WalletRepository;
import com.gmrnd.picpay.modules.wallet.infra.WalletType;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest implements PostgresTestContainer {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TransactionRepository transactionRepo;

  @Autowired
  private WalletRepository walletRepo;

  @Test
  void shouldCreateTransactionSuccessfully() throws Exception {
    var payerWallet = new Wallet("Payer Wallet", "12345678910", "payer@test.com", "P@ssw0rd",
        WalletType.Enum.USER.get());
    payerWallet.credit(new BigDecimal("1000.00"));

    var payeeWallet = new Wallet("Payee Wallet", "12345678911", "payee@test.com", "P@ssw0rd",
        WalletType.Enum.MERCHANT.get());
    payeeWallet.credit(new BigDecimal("1000.00"));

    walletRepo.save(payerWallet);
    walletRepo.save(payeeWallet);

    var dto = new TransactionDto(
        new BigDecimal("100.00"), payerWallet.getId(), payeeWallet.getId());

    var result = mockMvc.perform(post("/transfers")
        .contentType("application/json")
        .content(toJson(dto)))
        .andExpect(status().isOk())
        .andReturn();

    var resContent = result.getResponse().getContentAsString();
    var newTransaction = new ObjectMapper().readValue(resContent, Transaction.class);

    assertTrue(transactionRepo.findById(newTransaction.getId()).isPresent());
  }

  private static String toJson(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException("Error converting object to JSON", e);
    }
  }
}
