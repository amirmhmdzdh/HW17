package org.hw17.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hw17.base.entity.BaseEntity;
import org.hw17.model.enums.AcademicGrade;
import org.hw17.model.enums.LoanType;
import org.hw17.model.enums.Province;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan extends BaseEntity<Long> {

    @Enumerated(value = EnumType.STRING)
    private LoanType loanType;

    private Long amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student borrower;

    private LocalDate registrationDate;


    public Loan(LoanType loanType, Student borrower) {
        this.loanType = loanType;
        this.borrower = borrower;
        setLoanAmount(loanType, borrower);
    }

    private void setLoanAmount(LoanType loanType, Student borrower) {
        switch (loanType) {
            case EDUCATIONAL -> {
                AcademicGrade academicGrade = borrower.getAcademicGrade();
                if (academicGrade == AcademicGrade.ASSOCIATE || academicGrade == AcademicGrade.INTERRUPTED_BACHELOR
                        || academicGrade == AcademicGrade.UNINTERRUPTED_BACHELOR)
                    amount = 1_900_000L;
                else if (academicGrade == AcademicGrade.INTERRUPTED_DOCTORATE)
                    amount = 2_600_000L;
                else
                    amount = 2_250_000L;
            }
            case TUITION -> {
                AcademicGrade academicGrade = borrower.getAcademicGrade();
                if (academicGrade == AcademicGrade.ASSOCIATE || academicGrade == AcademicGrade.INTERRUPTED_BACHELOR
                        || academicGrade == AcademicGrade.UNINTERRUPTED_BACHELOR)
                    amount = 1_300_000L;
                else if (academicGrade == AcademicGrade.INTERRUPTED_DOCTORATE)
                    amount = 6_500_000L;
                else
                    amount = 2_600_000L;
            }
            case HOUSE_RENT -> {
                Province province = borrower.getProvince();
                if (province == Province.TEHRAN)
                    amount = 32_000_000L;
                else if (province.isMetropolis())
                    amount = 26_000_000L;
                else
                    amount = 19_500_000L;
            }
            default -> {
            }
        }
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanType=" + loanType +
                ", amount=" + amount +
                ", borrower=" + borrower +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
