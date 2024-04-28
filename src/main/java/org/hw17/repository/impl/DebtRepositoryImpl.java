package org.hw17.repository.impl;

import com.github.mfathi91.time.PersianDate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hw17.base.repository.impel.BaseRepositoryImpel;
import org.hw17.connection.SessionFactorySingleton;
import org.hw17.model.Debt;
import org.hw17.model.Student;
import org.hw17.repository.DebtRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DebtRepositoryImpl extends BaseRepositoryImpel<Debt, Long>
        implements DebtRepository {
    public DebtRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Debt> getEntityClass() {
        return Debt.class;
    }

    @Override
    public Optional<List<Debt>> getPaidDebts(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "select d from Debt d JOIN Loan l on d.loan.id = l.id where l.borrower.id =:s and d.isPaid =:p";
        Query<Debt> query = session.createQuery(hql, Debt.class);
        query.setParameter("s", student.getId());
        query.setParameter("p", true);

        return Optional.ofNullable(query.getResultList());
    }

    @Override
    public Optional<List<Debt>> getUnpaidDebts(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "select d from Debt d join Loan l on d.loan.id = l.id where l.borrower.id =:s and d.isPaid =:p";
        Query<Debt> query = session.createQuery(hql, Debt.class);
        query.setParameter("s", student.getId());
        query.setParameter("p", false);
        return Optional.ofNullable(query.getResultList());
    }

    @Override
    public Optional<List<Debt>> getMonthlyUnpaidDebts(Student student, int year, int month) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        LocalDate localDate = PersianDate.of(year, month, 1).toGregorian();

        String hql = "select d from Debt d join Loan l on d.loan.id = l.id where l.borrower.id =:s and " +
                "d.isPaid =:p and year(d.dueDate) =:y and month(d.dueDate) =:m";

        Query<Debt> query = session.createQuery(hql, Debt.class);
        query.setParameter("s", student.getId());
        query.setParameter("p", false);
        query.setParameter("y", localDate.getYear());
        query.setParameter("m", localDate.getMonthValue());
        return Optional.ofNullable(query.getResultList());
    }

    @Override
    public Optional<List<Debt>> findByLoanId(Long loanId) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        String hql = "select d from Debt d  where d.loan.id =:l";
        Query<Debt> query = session.createQuery(hql, Debt.class);
        query.setParameter("l", loanId);
        return Optional.ofNullable(query.getResultList());
    }
}
