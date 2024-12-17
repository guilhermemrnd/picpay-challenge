package com.gmrnd.picpay.modules.transaction.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record TransactionDto(
    @NotNull @DecimalMin("0.01") BigDecimal value,
    @NotNull UUID payer,
    @NotNull UUID payee) {
}
