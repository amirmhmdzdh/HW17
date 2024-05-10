package org.hw17.service.impl;

import com.github.mfathi91.time.PersianDate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hw17.base.service.impel.BaseServiceImpel;
import org.hw17.exception.NotFoundExeption;
import org.hw17.model.Debt;
import org.hw17.model.Loan;
import org.hw17.model.Student;
import org.hw17.repository.DebtRepository;
import org.hw17.service.DebtService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DebtServiceImpl extends BaseServiceImpel<Debt, Long, DebtRepository>
        implements DebtService {

    public DebtServiceImpl(DebtRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Debt> calculateDebts(Loan loan) {
        List<Debt> debts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            if (i != 5) {
                for (int j = 1; j <= 12; j++) {
                    Debt debt = new Debt(loan);
                    // 0.00279 = (مبلغ وام * 1.04) / 372
                    debt.setAmount(Math.pow(2, i - 1) * 0.00279 * loan.getAmount());
                    int monthNumber = 12 * (i - 1) + j;
                    debt.setDueDate(calculateDueDate(loan, monthNumber));
                    debts.add(debt);
                }
            } else {
                for (int j = 1; j <= 11; j++) {
                    Debt debt = new Debt(loan);
                    debt.setAmount(Math.pow(2, i - 1) * 0.00279 * loan.getAmount());
                    int monthNumber = 12 * (i - 1) + j;
                    debt.setDueDate(calculateDueDate(loan, monthNumber));
                    debts.add(debt);
                }
                Debt finalDebt = new Debt(loan);
                finalDebt.setAmount(1.04 * loan.getAmount() - debts.stream().map(Debt::getAmount).reduce(0d, Double::sum));
                finalDebt.setDueDate(calculateDueDate(loan, 60));
                debts.add(finalDebt);
            }
        }
        return debts;
    }

    private LocalDate calculateDueDate(Loan loan, int monthNumber) {
        int year = loan.getBorrower().getGraduateYear() + 1;
        LocalDate date = loan.getRegistrationDate();
        PersianDate persianDate = PersianDate.fromGregorian(date);
        int day = persianDate.getDayOfMonth();
        year += monthNumber / 12;
        int month;
        if (monthNumber % 12 > 0)
            month = monthNumber % 12;
        else {
            month = 12;
            year -= 1;
        }
        return PersianDate.of(year, month, day).toGregorian();
    }

    @Override
    public List<Debt> getPaidDebts(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<List<Debt>> paidDebts = repository.getPaidDebts(student);
            transaction.commit();
            session.close();
            return paidDebts.orElseThrow(() ->
                    new NotFoundExeption("There are no paid debts for this user!"));

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Debt> getUnpaidDebts(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<List<Debt>> unpaidDebts = repository.getUnpaidDebts(student);
            transaction.commit();
            session.close();
            return unpaidDebts.orElseThrow(() -> new NotFoundExeption("User has paid all debts!"));
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Debt> getMonthlyUnpaidDebts(Student student, int year, int month) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<List<Debt>> monthlyUnpaidDebts = repository.getMonthlyUnpaidDebts(student, year, month);
            transaction.commit();
            session.close();
            return monthlyUnpaidDebts.orElseThrow(() -> new NotFoundExeption("No unpaid debts found in the specified month!"));

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public List<Debt> findByLoanId(Long loanId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<List<Debt>> byLoanId = repository.findByLoanId(loanId);
            transaction.commit();
            session.close();
            return byLoanId.orElseThrow(() -> new NotFoundExeption("Invalid loan id!"));

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }

    }
}