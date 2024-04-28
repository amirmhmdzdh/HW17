package org.hw17.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hw17.base.repository.impel.BaseRepositoryImpel;
import org.hw17.connection.SessionFactorySingleton;
import org.hw17.model.BankAccount;
import org.hw17.model.Student;
import org.hw17.repository.StudentRepository;

public class StudentRepositoryImpl extends BaseRepositoryImpel<Student, Long>
        implements StudentRepository {
    public StudentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Student checkUsernameAndPassword(String username, String password) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "select s from Student s where s.username =:u and s.password =:p";
        Query<Student> query = session.createQuery(hql, Student.class);
        query.setParameter("u", username);
        query.setParameter("p", password);

        return query.uniqueResult();
    }

    @Override
    public Student findByNationalCode(String nationalCode) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "select s from Student s where s.nationalCode =:n";
        Query<Student> query = session.createQuery(hql, Student.class);
        query.setParameter("n", nationalCode);
        return query.uniqueResult();
    }

    @Override
    public BankAccount findBankAccount(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "select s.bankAccount from Student s where s.id =:i";
        Query<BankAccount> query = session.createQuery(hql, BankAccount.class);
        query.setParameter("i", student.getId());
        return query.uniqueResult();
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;

    }
}
