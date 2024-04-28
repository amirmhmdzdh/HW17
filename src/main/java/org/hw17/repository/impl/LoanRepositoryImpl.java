package org.hw17.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hw17.base.repository.impel.BaseRepositoryImpel;
import org.hw17.connection.SessionFactorySingleton;
import org.hw17.model.Loan;
import org.hw17.model.Student;
import org.hw17.repository.LoanRepository;

import java.util.List;

public class LoanRepositoryImpl extends BaseRepositoryImpel<Loan, Long>
        implements LoanRepository {
    public LoanRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Loan> getEntityClass() {
        return Loan.class;
    }

    @Override
    public List<Loan> getLoansOf(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "select l from Loan l where borrower =:s ";
        Query<Loan> query = session.createQuery(hql, Loan.class);
        query.setParameter("s", student);

        return query.getResultList();
    }
}
