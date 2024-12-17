package com.gmrnd.picpay.modules.transaction.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.gmrnd.picpay.modules.transaction.clients.dtos.AuthorizationResponse;

@FeignClient(name = "AuthorizationClient", url = "${client.authorization-service.url}")
public interface AuthorizationClient {

  @GetMapping
  public ResponseEntity<AuthorizationResponse> isAuthorized();
}
