package com.gmrnd.picpay.modules.wallet.infra;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "wallet_type")
public class WalletType {
  @Id
  private Long id;

  @Column(name = "description")
  private String description;

  public enum Enum {
    USER(1L, "user"),
    MERCHANT(2L, "merchant");

    Enum(Long id, String description) {
      this.id = id;
      this.description = description;
    }

    private final Long id;
    private final String description;

    public WalletType get() {
      return new WalletType(id, description);
    }
  }
}
