package com.gmrnd.picpay.modules.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gmrnd.picpay.modules.transaction.dtos.TransactionDto;
import com.gmrnd.picpay.modules.transaction.infra.Transaction;
import com.gmrnd.picpay.modules.transaction.usecases.TransferUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transfers")
public class TransactionController {

  private final TransferUseCase transferUseCase;

  public TransactionController(TransferUseCase transferUseCase) {
    this.transferUseCase = transferUseCase;
  }

  @ResponseStatus
  @PostMapping
  public ResponseEntity<Transaction> transfer(@RequestBody @Valid TransactionDto dto) {
    return ResponseEntity.ok(transferUseCase.execute(dto));
  }
}
