package com.gmrnd.picpay.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.gmrnd.picpay.infra.JpaWalletTypeRepository;
import com.gmrnd.picpay.infra.WalletType;

@Configuration
public class DataLoader implements CommandLineRunner {

  private final JpaWalletTypeRepository jpaWalleTypeRepo;

  public DataLoader(JpaWalletTypeRepository jpaWalleTypeRepo) {
    this.jpaWalleTypeRepo = jpaWalleTypeRepo;
  }

  @Override
  public void run(String... args) throws Exception {
    Arrays.stream(WalletType.Enum.values())
        .forEach(walletType -> jpaWalleTypeRepo.save(walletType.get()));
  }

}
