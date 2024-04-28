package org.hw17.repository;

import org.hw17.base.repository.BaseRepository;
import org.hw17.model.BankAccount;
import org.hw17.model.Student;

public interface StudentRepository extends BaseRepository<Student, Long> {

    Student checkUsernameAndPassword(String username, String password);

    Student findByNationalCode(String nationalCode);

    BankAccount findBankAccount(Student student);


}
