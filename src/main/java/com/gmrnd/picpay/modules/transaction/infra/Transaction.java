package com.gmrnd.picpay.modules.transaction.infra;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import com.gmrnd.picpay.modules.wallet.infra.Wallet;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "transaction")
@NoArgsConstructor
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "sender_wallet_id")
  private UUID senderId;

  @ManyToOne
  @JoinColumn(name = "sender_wallet_id", insertable = false, updatable = false)
  private Wallet sender;

  @Column(name = "receiver_wallet_id")
  private UUID receiverId;

  @ManyToOne
  @JoinColumn(name = "receiver_wallet_id", insertable = false, updatable = false)
  private Wallet receiver;

  @Column(name = "value")
  private BigDecimal value;

  public Transaction(BigDecimal value, UUID senderId, UUID receiverId) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.value = value;
  }

  public Optional<Wallet> getSender() {
    return Optional.ofNullable(sender);
  }

  public Optional<Wallet> getReceiver() {
    return Optional.ofNullable(receiver);
  }
}
