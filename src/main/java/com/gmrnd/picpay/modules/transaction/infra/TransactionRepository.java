package com.gmrnd.picpay.modules.transaction.infra;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TransactionRepository implements ITransactionRepository {
  private final JpaTransactionRepository jpaTransactionRepo;

  public TransactionRepository(JpaTransactionRepository jpaTransactionRepo) {
    this.jpaTransactionRepo = jpaTransactionRepo;
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
