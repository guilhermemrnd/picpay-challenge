package com.gmrnd.picpay.modules.wallet.usecases;

import com.gmrnd.picpay.modules.wallet.dtos.CreateWalletDto;
import com.gmrnd.picpay.modules.wallet.infra.IWalletRepository;
import com.gmrnd.picpay.modules.wallet.infra.Wallet;
import com.gmrnd.picpay.modules.wallet.usecases.errors.CreateWalletErrors;

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
