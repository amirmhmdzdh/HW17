package org.hw17.service.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hw17.base.service.impel.BaseServiceImpel;
import org.hw17.exception.NotFoundExeption;
import org.hw17.model.BankAccount;
import org.hw17.repository.BankAccountRepository;
import org.hw17.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class BankAccountServiceImpl extends BaseServiceImpel<BankAccount, Long, BankAccountRepository>
        implements BankAccountService {

    private final Logger logger;

    public BankAccountServiceImpl(BankAccountRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    }

    @Override
    public BankAccount findByCardNumber(String cardNumber, int cvv2) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<BankAccount> byCardNumber = repository.findByCardNumber(cardNumber, cvv2);
            transaction.commit();
            session.close();
            return byCardNumber.orElseThrow(() ->
                    new NotFoundExeption("No bank account found with this card number!"));
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error occurred while finding bank account by card number: " + e.getMessage(), e);
            System.out.println(e.getMessage());
            return null;
        }
    }
}