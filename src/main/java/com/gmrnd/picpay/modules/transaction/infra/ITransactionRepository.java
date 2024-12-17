package com.gmrnd.picpay.modules.transaction.infra;

import java.util.Optional;
import java.util.UUID;

public interface ITransactionRepository {
  Optional<Transaction> findById(UUID id);
  Transaction save(Transaction transaction);
}
