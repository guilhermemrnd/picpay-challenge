package com.gmrnd.picpay.modules.wallet.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWalletTypeRepository extends JpaRepository<WalletType, Long> {
}
