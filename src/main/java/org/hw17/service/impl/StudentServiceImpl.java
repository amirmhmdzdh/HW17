package org.hw17.service.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hw17.base.service.impel.BaseServiceImpel;
import org.hw17.exception.InvalidDateException;
import org.hw17.exception.NotProperTimeException;
import org.hw17.model.BankAccount;
import org.hw17.model.Student;
import org.hw17.repository.StudentRepository;
import org.hw17.service.StudentService;
import org.hw17.utility.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentServiceImpl
        extends BaseServiceImpel<Student, Long, StudentRepository>
        implements StudentService {

    public StudentServiceImpl(StudentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);

    }

    @Override
    public Student checkUsernameAndPassword(String username, String password) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Student student = repository.checkUsernameAndPassword(username, password);
            transaction.commit();
            session.close();
            return student;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isGraduated(Student student) {
        boolean result = ApplicationContext.currentPersianDate.getYear() > student.getGraduateYear();
        if (result)
            throw new NotProperTimeException("Can't register for a loan after graduation year!");
        return false;
    }

    @Override
    public boolean canRepay(Student student) {
        boolean result = ApplicationContext.currentPersianDate.getYear() > student.getGraduateYear();
        if (!result)
            throw new NotProperTimeException("Can't repay a loan until after graduation!");
        return true;
    }

    @Override
    public boolean isRegistrationOpen() {
        List<Integer> aban = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        List<Integer> bahman = new ArrayList<>(Arrays.asList(25, 26, 27, 28, 29, 30));
        List<Integer> esfand = new ArrayList<>(Arrays.asList(1));
        int currentMonth = ApplicationContext.currentPersianDate.getMonthValue();
        int currentDay = ApplicationContext.currentPersianDate.getDayOfMonth();
        if (currentMonth == 8 && aban.contains(currentDay))
            return true;
        else if (currentMonth == 11 && bahman.contains(currentDay))
            return true;
        else if (currentMonth == 12 && esfand.contains(currentDay))
            return true;
        throw new InvalidDateException("Registration is not active now!");
    }

    @Override
    public Student findByNationalCode(String nationalCode) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Student byNationalCode = repository.findByNationalCode(nationalCode);
            transaction.commit();
            session.close();
            return byNationalCode;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public BankAccount findBankAccount(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            BankAccount bankAccount = repository.findBankAccount(student);
            transaction.commit();
            session.close();
            return bankAccount;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }
    }
}
