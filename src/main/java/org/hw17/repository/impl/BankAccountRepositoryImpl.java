package org.hw17.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hw17.base.repository.impel.BaseRepositoryImpel;
import org.hw17.connection.SessionFactorySingleton;
import org.hw17.model.BankAccount;
import org.hw17.repository.BankAccountRepository;

import java.util.Optional;

public class BankAccountRepositoryImpl extends BaseRepositoryImpel<BankAccount, Long>
        implements BankAccountRepository {
    public BankAccountRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<BankAccount> getEntityClass() {
        return BankAccount.class;
    }

    @Override
    public Optional<BankAccount> findByCardNumber(String cardNumber) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "select b from BankAccount b where cardNumber=:c";

        Query<BankAccount> query = session.createQuery(hql, BankAccount.class);
        query.setParameter("c", cardNumber);

        return Optional.ofNullable(query.getSingleResult());
    }
}
