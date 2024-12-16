package com.gmrnd.picpay.usecases;

import com.gmrnd.picpay.controller.dtos.CreateWalletDto;
import com.gmrnd.picpay.infra.IWalletRepository;
import com.gmrnd.picpay.infra.Wallet;
import com.gmrnd.picpay.usecases.errors.CreateWalletErrors;

public class CreateWalletUseCase {
  private final IWalletRepository walletRepo;

  public CreateWalletUseCase(IWalletRepository walletRepository) {
    this.walletRepo = walletRepository;
  }

  public Wallet execute(CreateWalletDto dto) {
    var wallet = new Wallet(dto.fullName(), dto.cpfCnpj(), dto.email(), dto.password(), dto.walletType().get());

    boolean exists = walletRepo.exists(wallet.getEmail(), wallet.getCpfCnpj());
    if (exists) {
      throw new CreateWalletErrors.WalletAlreadyExists();
    }

    return walletRepo.save(wallet);
  }
}
