package com.gmrnd.picpay.modules.transaction.infra;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TransactionRepository implements ITransactionRepository {
  private final JpaTransactionRepository jpaTransactionRepo;

  public TransactionRepository(JpaTransactionRepository jpaTransactionRepo) {
    this.jpaTransactionRepo = jpaTransactionRepo;
  }

  public Optional<Transaction> findById(UUID id) {
    return jpaTransactionRepo.findById(id);
  }

  @Transactional
  public Transaction save(Transaction transaction) {
    try {
      return jpaTransactionRepo.save(transaction);
    } catch (Exception e) {
      throw new RuntimeException("Failed to save transaction", e);
    }
  }
}
