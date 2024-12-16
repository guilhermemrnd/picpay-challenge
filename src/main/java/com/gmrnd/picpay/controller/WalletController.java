package com.gmrnd.picpay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gmrnd.picpay.controller.dtos.CreateWalletDto;
import com.gmrnd.picpay.usecases.CreateWalletUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallets")
public class WalletController {
  private final CreateWalletUseCase createWalletUseCase;

  public WalletController(CreateWalletUseCase createWalletUseCase) {
    this.createWalletUseCase = createWalletUseCase;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void createWallet(@RequestBody @Valid CreateWalletDto dto) {
    createWalletUseCase.execute(dto);
  }
}
