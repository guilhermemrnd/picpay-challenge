package com.gmrnd.picpay.modules.transaction.infra;

public interface ITransactionRepository {
  Transaction save(Transaction transaction);
}
