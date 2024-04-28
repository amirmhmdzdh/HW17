package org.hw17.repository;

import org.hw17.base.repository.BaseRepository;
import org.hw17.model.Loan;
import org.hw17.model.Student;

import java.util.List;

public interface LoanRepository extends BaseRepository<Loan, Long> {

    List<Loan> getLoansOf(Student student);


}
