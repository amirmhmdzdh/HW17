package org.hw17.service;

import org.hw17.base.service.BaseService;
import org.hw17.model.BankAccount;
import org.hw17.model.Student;

public interface StudentService extends BaseService<Student, Long> {

    Student checkUsernameAndPassword(String username, String password);

    boolean isGraduated(Student student);

    boolean canRepay(Student student);

    boolean isRegistrationOpen();

    Student findByNationalCode(String nationalCode);

    BankAccount findBankAccount(Student student);


}
