package org.hw17.repository;

import org.hw17.base.repository.BaseRepository;
import org.hw17.model.Debt;
import org.hw17.model.Student;

import java.util.List;
import java.util.Optional;

public interface DebtRepository extends BaseRepository<Debt, Long> {

    Optional<List<Debt>> getPaidDebts(Student student);

    Optional<List<Debt>> getUnpaidDebts(Student student);

    Optional<List<Debt>> getMonthlyUnpaidDebts(Student student, int year, int month);

    Optional<List<Debt>> findByLoanId(Long loanId);


}
