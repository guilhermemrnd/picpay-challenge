package com.gmrnd.picpay.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWalletTypeRepository extends JpaRepository<WalletType, Long> {
}
