package org.hw17.service;

import org.hw17.base.service.BaseService;
import org.hw17.model.Loan;
import org.hw17.model.Student;

import java.util.List;

public interface LoanService extends BaseService<Loan, Long> {

    Loan chooseLoan(Loan loan);

    List<Loan> getLoansOf(Student student);

}
