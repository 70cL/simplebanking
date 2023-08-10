package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp date;

    private Double amount;

    @Column(insertable = false, updatable = false)
    private String type;

    Transaction(double amount, String approvalCode) {
        this.date = new Timestamp(System.currentTimeMillis());
        this.amount = amount;
        this.approvalCode = approvalCode;
    }

    private String approvalCode;

    public abstract void process(Account account) throws InsufficientBalanceException;
}
