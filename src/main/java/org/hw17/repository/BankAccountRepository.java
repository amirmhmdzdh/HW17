package org.hw17.repository;

import org.hw17.base.repository.BaseRepository;
import org.hw17.model.BankAccount;

import java.util.Optional;

public interface BankAccountRepository extends BaseRepository<BankAccount, Long> {

    Optional<BankAccount> findByCardNumber(String cardNumber);


}
