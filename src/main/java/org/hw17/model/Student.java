package org.hw17.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
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

@SuperBuilder
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


    @Pattern(regexp = "\\d{1,10}", message = "Identity code has at most 10 digits!")
    @Column(unique = true)
    private String identityCode;


    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "National code must have 10 digits!")
    private String nationalCode;


    private LocalDate birthDate;


    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{8}$", message = "Student number must have 8 digits!")
    private String studentNumber;


    @Enumerated(value = EnumType.STRING)
    private UniversityType universityType;


    @Range(min = 1350, max = 1500, message = "Invalid entrance year format!")
    private int entranceYear;


    @Enumerated(value = EnumType.STRING)
    private AcademicGrade academicGrade;


    @Enumerated(value = EnumType.STRING)
    private AcceptanceType acceptanceType;


    @OneToOne(cascade = CascadeType.PERSIST)
    private BankAccount bankAccount;


    @Enumerated(value = EnumType.STRING)
    private Province province;


    private boolean isMarried;


    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid name format!")
    private String spouseFirstName;


    @Pattern(regexp = "^[a-zA-Z\s]{3,}$", message = "Invalid last name format!")
    private String spouseLastName;


    @Pattern(regexp = "\\d{10}", message = "National code must have 10 digits!")
    private String spouseNationalCode;


    private boolean inDorm;


    @Pattern(regexp = "^[0-9]{6,9}", message = "House rent contract number must have 6 to 9 digits!")
    private String houseContractNumber;


    @Size(min = 3, max = 100, message = "Invalid address format!")
    private String houseAddress;


    private int graduateYear;


    @OneToMany(mappedBy = "borrower")
    private List<Loan> takenLoans;


    @Column(unique = true)
    private String username;

    private String password;


}
