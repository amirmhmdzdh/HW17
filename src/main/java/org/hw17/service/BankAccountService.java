package org.hw17.service;

import org.hw17.base.service.BaseService;
import org.hw17.model.BankAccount;

public interface BankAccountService extends BaseService<BankAccount, Long> {

    BankAccount findByCardNumber(String cardNumber);

}
