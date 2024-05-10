package org.hw17.service.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hw17.base.service.impel.BaseServiceImpel;
import org.hw17.model.Loan;
import org.hw17.model.Student;
import org.hw17.repository.LoanRepository;
import org.hw17.service.LoanService;
import org.hw17.utility.ApplicationContext;

import java.util.List;

public class LoanServiceImpl extends BaseServiceImpel<Loan, Long, LoanRepository>
        implements LoanService {

    public LoanServiceImpl(LoanRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);

    }

    @Override
    public Loan chooseLoan(Loan loan) {
        loan.setRegistrationDate(ApplicationContext.currentDate);
        return loan;
    }

    @Override
    public List<Loan> getLoansOf(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            List<Loan> loansOf = repository.getLoansOf(student);
            transaction.commit();
            session.close();
            return loansOf;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }
    }
}