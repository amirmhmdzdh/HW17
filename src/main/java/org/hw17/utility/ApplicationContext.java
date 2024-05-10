package org.hw17.utility;

import com.github.mfathi91.time.PersianDate;
import org.hibernate.SessionFactory;
import org.hw17.connection.SessionFactorySingleton;
import org.hw17.repository.BankAccountRepository;
import org.hw17.repository.DebtRepository;
import org.hw17.repository.LoanRepository;
import org.hw17.repository.StudentRepository;
import org.hw17.repository.impl.BankAccountRepositoryImpl;
import org.hw17.repository.impl.DebtRepositoryImpl;
import org.hw17.repository.impl.LoanRepositoryImpl;
import org.hw17.repository.impl.StudentRepositoryImpl;
import org.hw17.service.BankAccountService;
import org.hw17.service.DebtService;
import org.hw17.service.LoanService;
import org.hw17.service.StudentService;
import org.hw17.service.impl.BankAccountServiceImpl;
import org.hw17.service.impl.DebtServiceImpl;
import org.hw17.service.impl.LoanServiceImpl;
import org.hw17.service.impl.StudentServiceImpl;

import java.time.LocalDate;

public class ApplicationContext {

    private static final SessionFactory SESSION_FACTORY;

    //==================================================================================================================

    private static final BankAccountRepository BANK_ACCOUNT_REPOSITORY;
    private static final BankAccountService BANK_ACCOUNT_SERVICE;

    //==================================================================================================================

    private static final LoanRepository LOAN_REPOSITORY;
    private static final LoanService LOAN_SERVICE;

    //==================================================================================================================

    private static final DebtRepository DEBT_REPOSITORY;
    private static final DebtService DEBT_SERVICE;

    //==================================================================================================================

    private static final StudentRepository STUDENT_REPOSITORY;
    private static final StudentService STUDENT_SERVICE;


    //==================================================================================================================

    public final static PersianDate currentPersianDate;
    public final static LocalDate currentDate;

    //==================================================================================================================


    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();

        //--------------------------------------------------------------------------------------------------------------

        BANK_ACCOUNT_REPOSITORY = new BankAccountRepositoryImpl(SESSION_FACTORY);
        BANK_ACCOUNT_SERVICE = new BankAccountServiceImpl(BANK_ACCOUNT_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        LOAN_REPOSITORY = new LoanRepositoryImpl(SESSION_FACTORY);
        LOAN_SERVICE = new LoanServiceImpl(LOAN_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        STUDENT_REPOSITORY = new StudentRepositoryImpl(SESSION_FACTORY);
        STUDENT_SERVICE = new StudentServiceImpl(STUDENT_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        DEBT_REPOSITORY = new DebtRepositoryImpl(SESSION_FACTORY);
        DEBT_SERVICE = new DebtServiceImpl(DEBT_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        currentPersianDate = PersianDate.of(1402, 8, 3);
        currentDate = currentPersianDate.toGregorian();
    }


    public static StudentService getStudentService() {
        return STUDENT_SERVICE;
    }

    public static LoanService getLoanService() {
        return LOAN_SERVICE;
    }

    public static DebtService getDebtService() {
        return DEBT_SERVICE;
    }

    public static BankAccountService getBankAccountService() {
        return BANK_ACCOUNT_SERVICE;
    }
}
