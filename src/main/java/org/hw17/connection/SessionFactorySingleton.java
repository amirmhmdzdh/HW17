package org.hw17.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hw17.model.BankAccount;
import org.hw17.model.Debt;
import org.hw17.model.Loan;
import org.hw17.model.Student;

public class SessionFactorySingleton {
    private SessionFactorySingleton() {
    }

    private static class SessionFactoryHelper {
        static org.hibernate.service.ServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        private static final SessionFactory INSTANCE =
                new MetadataSources(registry)
                        .addAnnotatedClass(Student.class)
                        .addAnnotatedClass(BankAccount.class)
                        .addAnnotatedClass(Loan.class)
                        .addAnnotatedClass(Debt.class)
                        .buildMetadata()
                        .buildSessionFactory();
    }
    public static SessionFactory getInstance() {
        return SessionFactoryHelper.INSTANCE;
    }
}
