package org.hw17.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.hw17.base.entity.BaseEntity;
import org.hw17.model.enums.Bank;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class BankAccount extends BaseEntity<Long> {

    @Enumerated(value = EnumType.STRING)
    private Bank bank;


    @Pattern(regexp = "^[0-9]{16}$", message = "Invalid bank account card number!")
    @Column(unique = true)
    private String cardNumber;


    @Pattern(regexp = "^[0-9]{3,4}$", message = "Invalid cvv2 number!")
    private String cvv2;


    @Range(min = 1, max = 12, message = "Invalid card expiration month format!")
    private int expirationMonth;


    @Range(min = 1400, max = 1500, message = "Invalid card expiration year format!")
    private int expirationYear;


    @OneToOne(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private Student owner;


    @Range(min = 0)
    private Double balance;


}
