package com.gmrnd.picpay.modules.transaction.services;

import com.gmrnd.picpay.modules.transaction.infra.Transaction;

public interface IAuthorizationService {
  boolean isAuthorized(Transaction transaction);
}
