package com.gmrnd.picpay.modules.transaction.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.gmrnd.picpay.modules.transaction.infra.Transaction;

@FeignClient(name = "NotificationClient", url = "${client.notification-service.url}")
public interface NotificationClient {
  @PostMapping
  public ResponseEntity<Void> sendNotification(Transaction transaction);
}
