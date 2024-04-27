package org.hw17.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hw17.base.entity.BaseEntity;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Debt extends BaseEntity<Long> {

    private LocalDate dueDate;

    private Double amount;

    @ManyToOne
    private Loan loan;


    private boolean isPaid;

    private LocalDate paidDate;

    public Debt(Loan loan) {
        this.loan = loan;
        this.isPaid = false;
    }
}
