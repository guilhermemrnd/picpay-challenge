package com.gmrnd.picpay.modules.transaction.services;

import org.springframework.stereotype.Service;

import com.gmrnd.picpay.modules.transaction.clients.AuthorizationClient;
import com.gmrnd.picpay.modules.transaction.infra.Transaction;

@Service
public class AuthorizationService implements IAuthorizationService {
  private final AuthorizationClient authorizationClient;

  public AuthorizationService(AuthorizationClient authorizationClient) {
    this.authorizationClient = authorizationClient;
  }

  public boolean isAuthorized(Transaction transaction) {
    var resp = authorizationClient.isAuthorized();

    if (resp.getStatusCode().isError()) {
      throw new RuntimeException("Error on authorization service");
    }

    return resp.getBody().authorized();
  }
}
