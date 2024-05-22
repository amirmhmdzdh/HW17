package org.hw17.menu;

import com.github.mfathi91.time.PersianDate;
import org.hw17.exception.InvalidDateException;
import org.hw17.exception.NotFoundExeption;
import org.hw17.model.BankAccount;
import org.hw17.model.Debt;
import org.hw17.model.Loan;
import org.hw17.model.Student;
import org.hw17.model.enums.*;
import org.hw17.service.BankAccountService;
import org.hw17.service.DebtService;
import org.hw17.service.LoanService;
import org.hw17.service.StudentService;
import org.hw17.utility.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

public class LoanRegister {
    private static final Logger logger = LoggerFactory.getLogger(LoanRegister.class);
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = ApplicationContext.getStudentService();
    private static LoanService loanService = ApplicationContext.getLoanService();
    private static DebtService debtService = ApplicationContext.getDebtService();
    private static PersianDate currentPersianDate = ApplicationContext.currentPersianDate;
    private static Student savestudent = new Student();
    private static BankAccountService bankAccountService = ApplicationContext.getBankAccountService();

    public static void registryMenu() {
        try {
            System.out.println(" Pleas fill the required fields >>>> ");
            logger.info("Please fill the required fields >>>>");
            firstName(savestudent);
            lastName(savestudent);
            fatherName(savestudent);
            motherName(savestudent);
            nationalCode(savestudent);
            birthDate(savestudent);
            province(savestudent);
            maritalStatus(savestudent);
            if (savestudent.isMarried())
                spouseInformation(savestudent);
            universityType(savestudent);
            entranceYear(savestudent);
            studentCode(savestudent);
            if (savestudent.getUniversityType() == UniversityType.STATE)
                acceptanceType(savestudent);
            academicDegree(savestudent);
            residenceStatus(savestudent);
            if (!savestudent.isInDorm())
                address(savestudent);
            bankAccount(savestudent);
            savestudent.setGraduateYear(savestudent.getEntranceYear() + savestudent.getAcademicGrade().getGraduateDuration());

            savestudent.setUsernameAndPassword(savestudent);
            Student student = studentService.saveOrUpdate(savestudent);
            savestudent.showUsernameAndPassword(student);
        } catch (Exception e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }

    public static void signIn() {
        logger.info("User sign-in initiated ");
        System.out.println("Username ->> ");
        String userName = scanner.next();
        scanner.nextLine();

        System.out.println("Password ->> ");
        String password = scanner.next();
        scanner.nextLine();

        Student valid = studentService.checkUsernameAndPassword(userName, password);
        if (valid != null) {
            logger.info("User logged in successfully: {} " , userName);
            System.out.println(">>>> Welcome to your user panel <<<<");
            showLoanMenu(valid);
        } else
            logger.warn("Invalid username or password");
        System.out.println(">>>> Username or password is incorrect <<<<");
    }

    public static void showLoanMenu(Student student) {
        logger.info("Loan menu displayed for student: {}", student.getUsername());
        boolean loop = true;

        while (loop) {
            try {
                System.out.println("========================================");
                System.out.println("        * STUDENT LOAN MENU *            ");
                System.out.println("========================================");
                System.out.println("1) Loan Register ");
                System.out.println("2) loan Repay ");
                System.out.println("3) Logout ");
                System.out.println("----------------------------------------");

                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> {
                        logger.info("User selected Loan Register");
                        if (!studentService.isGraduated(student)) {
                            if (studentService.isRegistrationOpen())
                            showLoanRegisterMenu(student);
                        }
                    }
                    case "2" -> {
                        logger.info("User selected Loan Repay");
                        if (studentService.canRepay(student)) {
                            showRepayMenu(student);
                        }
                    }

                    case "3" -> {
                        logger.info("User selected Logout");
                        return;
                    }
                    default -> {
                        logger.warn("Invalid input: {}", input);
                        System.out.println(" <<< Wrong entry >>>");
                    }
                }


            } catch (Exception e) {
                logger.error("An exception occurred: {}", e.getMessage(), e);
                if (e instanceof InputMismatchException)
                    System.out.println("Wrong entry!");
                else
                    System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
    public static void showLoanRegisterMenu(Student student) {
        logger.info("Loan register menu displayed for student: {}", student.getUsername());
        while (true) {
            try {
                System.out.println("==========================================");
                System.out.println("        * Loan registration menu *        ");
                System.out.println("==========================================");
                System.out.println("1) See registered loans and debts ");
                System.out.println("2) Register for a loan ");
                System.out.println("3) Back ");
                System.out.println("------------------------------------------");


                List<Loan> possibleLoans = getPossibleLoans(student);
                List<String> toBePrinted = new ArrayList<>();
                possibleLoans.forEach(loan ->
                        toBePrinted.add(loan.getLoanType() + "\t" + loan.getAmount()));

                String input = scanner.nextLine();
                switch (input) {
                    case "1" -> {
                        logger.info("User selected 'See registered loans and debts'");
                        List<Loan> loans = loanService.getLoansOf(student);
                        List<String> loanStringList = new ArrayList<>();
                        loans.forEach(loan -> loanStringList.add("id: " + loan.getId() + " - type: "
                                + loan.getLoanType() + " - registered in:  "
                                + PersianDate.fromGregorian(loan.getRegistrationDate())
                                + " - amount: " + loan.getAmount()));

                        System.out.println("Select each of the taken loans to see debts :");
                        loanStringList.forEach(System.out::println);

                        if (!loans.isEmpty()) {
                            System.out.println("Loan id ->");
                            Long loanId = scanner.nextLong();
                            scanner.nextLine();
                            if (loans.stream().map(Loan::getId).toList().contains(loanId)) {
                                List<Debt> debts = debtService.findByLoanId(loanId);
                                if (!debts.isEmpty()) {
                                    System.out.println("Debts of loan by id of " + loanId);
                                    debts.forEach(debt -> {
                                        String debtString = "id: " + debt.getId() + " - due date: " +
                                                PersianDate.fromGregorian(debt.getDueDate()) +
                                                " - amount: " + debt.getAmount() + "is Paid: " + debt.isPaid();
                                        System.out.println(debtString);
                                    });
                                } else {
                                    System.out.println("No debts found for loan with id " + loanId);
                                }
                            } else {
                                System.out.println("Invalid loan id!");
                            }
                        } else {
                            System.out.println("male or female " + student.getLastName() + " You have not received a loan");
                        }
                    }
                    case "2" -> {
                        logger.info("User selected 'Register for a loan'");
                        if (!possibleLoans.isEmpty()) {
                            printListWithSelect(toBePrinted);
                            int selectedLoanIndex;
                            if (scanner.hasNextInt()) {
                                selectedLoanIndex = scanner.nextInt();
                                if (selectedLoanIndex > 0 && selectedLoanIndex <= possibleLoans.size()) {
                                    Loan selectedLoan = loanService.chooseLoan(possibleLoans.get(selectedLoanIndex - 1));
                                    if (selectedLoan != null) {
                                        BankAccount bankAccount = studentService.findBankAccount(selectedLoan.getBorrower());
                                        bankAccount.setBalance(bankAccount.getBalance() + selectedLoan.getAmount());
                                        bankAccountService.saveOrUpdate(bankAccount);
                                        loanService.saveOrUpdate(selectedLoan);
                                        List<Debt> debts = debtService.calculateDebts(selectedLoan);
                                        for (Debt debt : debts) {
                                            debtService.saveOrUpdate(debt);
                                        }

                                    } else {
                                        System.out.println("Invalid loan selection!");
                                    }
                                } else {
                                    System.out.println("Invalid loan index!");
                                }
                            } else {
                                System.out.println("Invalid input!");
                            }
                            scanner.nextLine();
                        }
                    }
                    case "3" -> {
                        logger.info("User selected 'Back'");
                        return;

                    }
                    default -> {
                        logger.warn("Invalid input: {}", input);
                        System.out.println("Wrong entry!");
                    }
                }
            } catch (Exception e) {
                logger.error("An exception occurred: {}", e.getMessage(), e);
                if (e instanceof InputMismatchException)
                    System.out.println(e.getMessage());
                else
                    System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

    }

    public static void showRepayMenu(Student student) {
        logger.info("Repay menu displayed for student: {}", student.getUsername());
        while (true) {
            try {
                System.out.println("==========================================");
                System.out.println("|             * REPAY MENU *             |");
                System.out.println("==========================================");
                System.out.println("1) List of paid debts ");
                System.out.println("2) List of all unpaid debts ");
                System.out.println("3) Unpaid debts list of a specific month ");
                System.out.println("4) Pay debt");
                System.out.println("5) BACK");
                System.out.println("------------------------------------------");
                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> {
                        logger.info("User selected 'List of paid debts'");
                        List<String> result = new ArrayList<>();
                        debtService.getPaidDebts(student).forEach(debt -> {
                            result.add(debt.getId() + " - type = " + debt.getLoan().getLoanType() + "\t"
                                    + PersianDate.fromGregorian(debt.getPaidDate()));
                        });
                        System.out.println("Paid Debts :");
                        result.forEach(System.out::println);
                    }
                    case "2" -> {
                        logger.info("User selected 'List of all unpaid debts'");
                        List<String> result = new ArrayList<>();
                        debtService.getUnpaidDebts(student).forEach(debt ->
                                result.add(debt.getId() + "- " + PersianDate.fromGregorian(debt.getDueDate())
                                        + "\t" + debt.getLoan().getLoanType() + "\t" + debt.getAmount()));

                        System.out.println("Unpaid Debts:");
                        result.forEach(System.out::println);
                    }
                    case "3" -> {
                        logger.info("User selected 'Unpaid debts list of a specific month'");
                        List<String> result = new ArrayList<>();

                        System.out.println("Specify the year and month of debts");
                        System.out.println("Year ->>");
                        int year = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Month ->>");
                        int month = scanner.nextInt();
                        scanner.nextLine();
                        debtService.getMonthlyUnpaidDebts(student, year, month).forEach(debt -> {
                            result.add(debt.getId() + "- " + PersianDate.fromGregorian(debt.getDueDate())
                                    + "\t" + debt.getAmount());
                        });
                        System.out.println("Specified month unpaid Debts " + result);
                    }
                    case "4" -> {
                        logger.info("User selected 'Pay debt'");
                        payDebt(student);
                    }
                    case "5" -> {
                        logger.info("User selected 'BACK'");
                        return;
                    }
                    default -> {
                        logger.warn("Invalid input: {}", input);
                        System.out.println(" <<< Wrong entry >>>");
                    }
                }
            } catch (Exception e) {
                logger.error("An exception occurred: {}", e.getMessage(), e);
                if (e instanceof InputMismatchException)
                    System.out.println(">>> Wrong entry <<<");
                else
                    System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    //======================================================Methods=====================================================
    private static void firstName(Student student) {
        System.out.println(" First Name -> ");
        student.setFirstName(scanner.nextLine());
    }

    private static void lastName(Student student) {
        System.out.println(" Last Name -> ");
        student.setLastName(scanner.nextLine());
    }

    private static void fatherName(Student student) {
        System.out.println(" Father Name -> ");
        student.setFatherName(scanner.nextLine());
    }

    private static void motherName(Student student) {
        System.out.println(" Mother Name -> ");
        student.setMotherName(scanner.nextLine());
    }

    private static void nationalCode(Student student) {
        System.out.println(" National Code -> ");
        student.setNationalCode(scanner.nextLine());
    }

    private static void birthDate(Student student) {
        do {
            student.setBirthDate(birthDateHelper());
        } while (student.getBirthDate() == null);
    }

    private static void province(Student student) {
        System.out.println(" Select your province from list bellow ->> ");
        printListWithSelect(Arrays.stream(Province.values()).map(Object::toString).toList());
        student.setProvince(Province.values()[scanner.nextInt() - 1]);

    }

    private static void maritalStatus(Student student) {
        System.out.println(" Marital Status ->> ");
        printListWithSelect(Arrays.stream(MaritalStatus.values()).map(Objects::toString).toList());
        int choice = scanner.nextInt();
        scanner.nextLine();
        student.setMarried(MaritalStatus.values()[choice - 1].name().equals("MARRIED"));
    }

    private static void spouseInformation(Student student) {

        System.out.println("Spouse's first name -> ");
        student.setSpouseFirstName(scanner.nextLine());
        System.out.println("Spouse's last name -> ");
        student.setSpouseLastName(scanner.nextLine());
        System.out.println("Spouse's national code -> ");
        student.setSpouseNationalCode(scanner.nextLine());
    }

    private static void universityType(Student student) {
        System.out.println("University name ->>");
        student.setUniversityName(scanner.nextLine());
        System.out.println("University type ->> ");
        printListWithSelect(Arrays.stream(UniversityType.values()).map(Objects::toString).toList());
        student.setUniversityType(UniversityType.values()[scanner.nextInt() - 1]);
    }

    private static void entranceYear(Student student) {
        System.out.println("Entrance year ->> ");
        int entranceYear = scanner.nextInt();
        scanner.nextLine();
        if (entranceYear >= (student.getBirthYear() + 15))
            student.setEntranceYear(entranceYear);
        else
            throw new InvalidDateException("Age can not be less than 15!");
    }

    private static void studentCode(Student student) {
        System.out.println("Student code ->> ");
        student.setStudentNumber(scanner.next());
        scanner.nextLine();
    }

    private static void acceptanceType(Student student) {
        System.out.println("Acceptance type ->> ");
        printListWithSelect(Arrays.stream(AcceptanceType.values()).map(Objects::toString).toList());
        student.setAcceptanceType(AcceptanceType.values()[scanner.nextInt() - 1]);
        scanner.nextLine();
    }

    private static void academicDegree(Student student) {
        System.out.println("Academic degree ->> ");
        printListWithSelect(Arrays.stream(AcademicGrade.values()).map(Objects::toString).toList());
        student.setAcademicGrade(AcademicGrade.values()[scanner.nextInt() - 1]);
        scanner.nextLine();
    }

    private static void residenceStatus(Student student) {
        System.out.println("Residence status ->> ");
        printListWithSelect(Arrays.stream(ResidenceStatus.values()).map(Object::toString).toList());
        student.setInDorm(scanner.nextInt() == ResidenceStatus.DORM.ordinal() + 1);
        scanner.nextLine();
    }

    private static void address(Student student) {
        System.out.println("House rent contract number ->> ");
        student.setHouseContractNumber(scanner.next());
        scanner.nextLine();
        System.out.println("House address ->> ");
        student.setHouseAddress(scanner.nextLine());
    }

    private static void bankAccount(Student student) {
        do {
            student.setBankAccount(validateBankInformation(new BankAccount()));
        } while (student.getBankAccount() == null);
    }

    private static BankAccount validateBankInformation(BankAccount bankAccount) {
        try {
            System.out.println("Select the Account bank ->> ");
            printListWithSelect(Arrays.stream(Bank.values()).map(Objects::toString).toList());
            bankAccount.setBank(Bank.values()[scanner.nextInt() - 1]);
            scanner.nextLine();
            System.out.println("Account card number ->> ");
            bankAccount.setCardNumber(scanner.nextLine());
            scanner.nextLine();
            System.out.println("cvv2 ->> ");
            bankAccount.setCvv2(scanner.next());
            scanner.nextLine();
            System.out.println("Card expiration month ->> ");
            bankAccount.setExpirationMonth(scanner.nextInt());
            scanner.nextLine();
            System.out.println("Card expiration year ->> ");
            bankAccount.setExpirationYear(scanner.nextInt());
            scanner.nextLine();
            System.out.println("Initial balance ->> ");
            bankAccount.setBalance(scanner.nextDouble());
            scanner.nextLine();
            return bankAccount;
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static LocalDate birthDateHelper() {
        try {
            System.out.println(" Year of birth -> ");
            int year = scanner.nextInt();
            scanner.nextLine();
            System.out.println(" Month of birth -> ");
            int month = scanner.nextInt();
            scanner.nextLine();
            System.out.println(" Day of birth -> ");
            int day = scanner.nextInt();
            scanner.nextLine();
            int diff = currentPersianDate.getYear() - year;
            if (diff < 15)
                throw new InvalidDateException(" <<< Age can not be less than 15 >>> ");
            if (year < 0 || month < 0 || day < 0 || month > 12 || day > 31)
                throw new InvalidDateException(" <<< Invalid date entry >>> ");
            return PersianDate.of(year, month, day).toGregorian();

        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            System.out.println(e.getMessage());
            scanner.nextLine();
            throw new InvalidDateException("<<< Invalid date entry >>>");
        }
    }

    private static void payDebt(Student student) {
        List<String> result = new ArrayList<>();
        debtService.getUnpaidDebts(student).forEach(debt ->
                result.add(debt.getId() + "- " + PersianDate.fromGregorian(debt.getDueDate())
                        + "\t" + debt.getLoan().getLoanType() + "\t" + debt.getAmount()));
        if (!result.isEmpty()) {
            System.out.println("Enter bank account information");
            System.out.println("*******************************");
            System.out.println("Card number ->> ");
            String cardNumber = scanner.nextLine();
            System.out.println("CVV2 ->> ");
            int cvv2 = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Expiration month");
            int month = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Expiration year");
            int year = scanner.nextInt();
            scanner.nextLine();
            try {
                BankAccount bankAccount = bankAccountService.findByCardNumber(cardNumber, cvv2);
                if (bankAccount.getCvv2().equals(cvv2) || bankAccount.getExpirationMonth() != month || bankAccount.getExpirationYear() != year)
                    throw new NotFoundExeption("Invalid card properties!");

                for (String item : result) {
                    System.out.println("Choose debt id " + item);
                }
                System.out.println("Enter Debt id ");
                long debtId = scanner.nextLong();
                scanner.nextLine();
                Debt debt = debtService.findById(debtId);
                if (debt != null)
                    bankAccount.setBalance(bankAccount.getBalance() - debt.getAmount());
                debt.setPaid(true);
                debt.setPaidDate(ApplicationContext.currentDate);
                bankAccountService.saveOrUpdate(bankAccount);
                debtService.saveOrUpdate(debt);
                System.out.println();
                System.out.println("=================================================================================");
                System.out.println("sir or madam " + student.getLastName() + " Thank you for your payment.\n" +
                        " On behalf of BANK : " + bankAccount.getBank().name());
                System.out.println("=================================================================================");


            } catch (Exception e) {
                logger.error("An error occurred: {}", e.getMessage());
                if (e instanceof InputMismatchException)
                    System.out.println(">>> Wrong entry <<<");
                else
                    System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public static void printListWithSelect(List<String> list) {
        System.out.println("------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ") " + list.get(i));
        }
        System.out.println("------------------------------------");
        System.out.print("Select -> ");
    }

    public static List<Loan> getPossibleLoans(Student student) {
        List<Loan> possibleLoans = new ArrayList<>();
        if (isHouseLoanPossible(student))
            possibleLoans.add(new Loan(LoanType.HOUSE_RENT, student));
        if (isEducationalLoanPossible(student))
            possibleLoans.add(new Loan(LoanType.EDUCATIONAL, student));
        if (isTuitionLoanPossible(student))
            possibleLoans.add(new Loan(LoanType.TUITION, student));
        return possibleLoans;
    }

    private static boolean isHouseLoanPossible(Student student) {
        if (!student.isMarried() || student.isInDorm())
            return false;
        else {
            Student spouse = studentService.findByNationalCode(student.getSpouseNationalCode());
            if (spouse != null && isLoanTaken(LoanType.HOUSE_RENT, spouse)) {
                return false;
            }
            return !isLoanTaken(LoanType.HOUSE_RENT, student);
        }
    }

    private static boolean isEducationalLoanPossible(Student student) {
        return !isLoanTaken(LoanType.EDUCATIONAL, student);
    }

    private static boolean isTuitionLoanPossible(Student student) {
        UniversityType universityType = student.getUniversityType();
        AcceptanceType acceptanceType = student.getAcceptanceType();
        if (universityType == UniversityType.STATE && acceptanceType == AcceptanceType.DAY_PROGRAM)
            return false;
        else
            return !isLoanTaken(LoanType.TUITION, student);
    }

    private static boolean isLoanTaken(LoanType loanType, Student student) {
        List<Loan> loans = loanService.getLoansOf(student);
        List<LoanType> loanTypes = loans.stream().map(Loan::getLoanType).toList();
        if (loans.isEmpty() || !loanTypes.contains(loanType)) {
            return false;
        } else {
            switch (loanType) {
                case TUITION -> {
                    List<Loan> tuitionLoans = loans.stream().filter(loan -> loan.getLoanType() == LoanType.TUITION).toList();
                    return areLoansInCurrentPeriod(tuitionLoans);
                }
                case EDUCATIONAL -> {
                    List<Loan> educationalLoans = loans.stream().filter(loan -> loan.getLoanType() == LoanType.EDUCATIONAL).toList();
                    return areLoansInCurrentPeriod(educationalLoans);
                }
                default -> {
                    return true;
                }
            }
        }
    }

    private static boolean areLoansInCurrentPeriod(List<Loan> takenLoans) {
        if (!takenLoans.isEmpty()) {
            for (Loan l : takenLoans) {
                PersianDate persianRegistrationDate = PersianDate.fromGregorian(l.getRegistrationDate());
                int year = persianRegistrationDate.getYear();
                int month = persianRegistrationDate.getMonthValue();
                switch (month) {
                    case 8 -> {
                        if (ApplicationContext.currentPersianDate.isAfter(PersianDate.of(year, 7, 30))
                                && ApplicationContext.currentPersianDate.isBefore(PersianDate.of(year, 8, 8)))
                            return true;
                    }
                    case 11, 12 -> {
                        if (ApplicationContext.currentPersianDate.isAfter(PersianDate.of(year, 11, 24))
                                && ApplicationContext.currentPersianDate.isBefore(PersianDate.of(year, 12, 2)))
                            return true;
                    }
                }
            }
        }
        return false;
    }


}
