package com.gmrnd.picpay.modules.transaction.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gmrnd.picpay.modules.transaction.clients.NotificationClient;
import com.gmrnd.picpay.modules.transaction.infra.Transaction;

@Service
public class NotificationService implements INotificationService {
  private final static Logger logger = LoggerFactory.getLogger(NotificationService.class);

  private final NotificationClient notificationClient;

  public NotificationService(NotificationClient notificationClient) {
    this.notificationClient = notificationClient;
  }

  public void sendNotification(Transaction transaction) {
    try {
      var resp = notificationClient.sendNotification(transaction);
      if (resp.getStatusCode().isError()) {
        logger.error("Failed to send notification");
      }
    } catch (Exception e) {
      logger.error("Failed to send notification", e);
    }
  }
}
