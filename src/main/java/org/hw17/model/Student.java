package org.hw17.model;

import com.github.mfathi91.time.PersianDate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.hw17.base.entity.BaseEntity;
import org.hw17.model.enums.AcademicGrade;
import org.hw17.model.enums.AcceptanceType;
import org.hw17.model.enums.Province;
import org.hw17.model.enums.UniversityType;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends BaseEntity<Long> {

    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid name format!")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid last name format!")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid name format!")
    private String fatherName;


    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid name format!")
    private String motherName;

    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "National code must have 10 digits!")
    private String nationalCode;

    private LocalDate birthDate;

    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{8}$", message = "Student number must have 8 digits!")
    private String studentNumber;


    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid name format!")
    private String universityName;

    @Enumerated(value = EnumType.STRING)
    private UniversityType universityType;

    @Range(min = 1350, max = 1500, message = "Invalid entrance year format!")
    private int entranceYear;


    @Enumerated(value = EnumType.STRING)
    private AcademicGrade academicGrade;


    @Enumerated(value = EnumType.STRING)
    private AcceptanceType acceptanceType;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private BankAccount bankAccount;


    @Enumerated(value = EnumType.STRING)
    private Province province;


    private boolean isMarried;

    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid name format!")
    private String spouseFirstName;

    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid last name format!")
    private String spouseLastName;

    @Pattern(regexp = "\\d{10}", message = "National code must have 10 digits!")
    @Column(unique = true)
    private String spouseNationalCode;


    private boolean inDorm;

    @Pattern(regexp = "^[0-9]{6,9}", message = "House rent contract number must have 6 to 9 digits!")
    @Column(unique = true)
    private String houseContractNumber;

    @Size(min = 3, max = 100, message = "Invalid address format!")
    private String houseAddress;


    private int graduateYear;


    @OneToMany(mappedBy = "borrower", fetch = FetchType.EAGER)
    private List<Loan> takenLoans;


    @Column(unique = true)
    private String username;

    private String password;

    public Student setUsernameAndPassword(Student student) {

        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = upperCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[8];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < 8; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        student.setUsername(student.getNationalCode());
        student.setPassword(String.valueOf(password));

        return student;
    }

    public void showUsernameAndPassword(Student student) {
        if (student != null) {
            System.out.println("_____________________________________");
            System.out.println(" USERNAME   : " + student.getUsername());
            System.out.println("-------------------------------------");
            System.out.println(" PASSWORD   : " + student.getPassword());
            System.out.println("_____________________________________");
        }
    }

    public void setBirthDate(int year, int month, int day) {
        PersianDate persianDate = PersianDate.of(year, month, day);
        birthDate = persianDate.toGregorian();
    }

    public int getBirthYear() {
        return PersianDate.fromGregorian(birthDate).getYear();
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", birthDate=" + birthDate +
                ", studentNumber='" + studentNumber + '\'' +
                ", universityName='" + universityName + '\'' +
                ", universityType=" + universityType +
                ", entranceYear=" + entranceYear +
                ", academicGrade=" + academicGrade +
                ", acceptanceType=" + acceptanceType +
//                ", bankAccount=" + bankAccount +
                ", province=" + province +
                ", isMarried=" + isMarried +
                ", spouseFirstName='" + spouseFirstName + '\'' +
                ", spouseLastName='" + spouseLastName + '\'' +
                ", spouseNationalCode='" + spouseNationalCode + '\'' +
                ", inDorm=" + inDorm +
                ", houseContractNumber='" + houseContractNumber + '\'' +
                ", houseAddress='" + houseAddress + '\'' +
                ", graduateYear=" + graduateYear +
                ", takenLoans=" + takenLoans +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
