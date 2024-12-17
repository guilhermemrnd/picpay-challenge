package com.gmrnd.picpay.modules.transaction.infra;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.gmrnd.picpay.PostgresTestContainer;

import jakarta.transaction.Transactional;

@DataJpaTest
@Import(TransactionRepository.class)
@Transactional
public class TransactionRepositoryTest implements PostgresTestContainer {
  @Autowired
  private TransactionRepository transactionRepo;

  @Autowired
  private JpaTransactionRepository jpaTransactionRepo;

  @Test
  void shouldSaveTransactionSuccessfully() {
    var transaction = new Transaction(
        new BigDecimal("100.0"), UUID.randomUUID(), UUID.randomUUID());
    transactionRepo.save(transaction);

    var savedTransaction = jpaTransactionRepo.findById(transaction.getId()).orElse(null);

    assertNotNull(savedTransaction);
    assertEquals(transaction.getId(), savedTransaction.getId());
    assertEquals(transaction.getValue(), savedTransaction.getValue());
    assertEquals(transaction.getSenderId(), savedTransaction.getSenderId());
    assertEquals(transaction.getReceiverId(), savedTransaction.getReceiverId());
  }
}
