package com.gmrnd.picpay.modules.wallet;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmrnd.picpay.PostgresTestContainer;
import com.gmrnd.picpay.modules.wallet.dtos.CreateWalletDto;
import com.gmrnd.picpay.modules.wallet.infra.WalletRepository;
import com.gmrnd.picpay.modules.wallet.infra.WalletType;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest implements PostgresTestContainer {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WalletRepository walletRepo;

  @Test
  public void shouldCreateWalletSuccessfully() throws Exception {
    var dto = new CreateWalletDto("Test Test", "12345678912", "test@test.com", "p@ssw0rd", WalletType.Enum.USER);

    mockMvc.perform(post("/wallets")
        .contentType("application/json")
        .content(toJson(dto)))
        .andExpect(status().isCreated());

    assertTrue(walletRepo.exists(dto.email(), dto.cpfCnpj()));
  }

  private static String toJson(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
