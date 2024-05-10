package org.hw17.service;

import org.hw17.base.service.BaseService;
import org.hw17.model.Debt;
import org.hw17.model.Loan;
import org.hw17.model.Student;

import java.util.List;

public interface DebtService extends BaseService<Debt, Long> {

    List<Debt> calculateDebts(Loan loan);

    List<Debt> getPaidDebts(Student student);

    List<Debt> getUnpaidDebts(Student student);

    List<Debt> getMonthlyUnpaidDebts(Student student, int year, int month);

    List<Debt> findByLoanId(Long loanId);

}
