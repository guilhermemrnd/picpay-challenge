package com.gmrnd.picpay.modules.wallet.infra;

import java.math.BigDecimal;
import java.util.UUID;

import com.gmrnd.picpay.modules.transaction.exceptions.InsufficientBalanceException;
import com.gmrnd.picpay.modules.transaction.exceptions.InvalidPayerException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wallet")
@NoArgsConstructor
public class Wallet {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "cpf_cnpj", unique = true)
  private String cpfCnpj;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "balance")
  private BigDecimal balance = BigDecimal.ZERO;

  @ManyToOne
  @JoinColumn(name = "wallet_type_id")
  private WalletType walletType;

  public Wallet(String fullName, String cpfCnpj, String email, String password, WalletType walletType) {
    this.fullName = fullName;
    this.cpfCnpj = cpfCnpj;
    this.email = email;
    this.password = password;
    this.walletType = walletType;
  }

  public void debit(BigDecimal value) {
    if (this.walletType.isMerchant()) {
      throw new InvalidPayerException();
    }

    if (this.balance.compareTo(value) < 0) {
      throw new InsufficientBalanceException();
    }

    this.balance = this.balance.subtract(value);
  }

  public void credit(BigDecimal value) {
    this.balance = this.balance.add(value);
  }
}
