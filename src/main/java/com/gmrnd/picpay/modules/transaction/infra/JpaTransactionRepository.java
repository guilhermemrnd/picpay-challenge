package com.gmrnd.picpay.modules.transaction.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTransactionRepository extends JpaRepository<Transaction, UUID> {
}
