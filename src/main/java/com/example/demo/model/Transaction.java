package com.example.demo.model;


import com.example.demo.model.Accounts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_SEQ_GEN")
    @SequenceGenerator(name = "transaction_SEQ_GEN", sequenceName = "transaction_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "Id")
    public Long id;

    @CreationTimestamp
    @Column(name ="createdDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "debitAccount",referencedColumnName = "id")
    public Accounts debitAccount;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "creditAccount",referencedColumnName = "id")
    public Accounts creditAccount;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId",referencedColumnName = "id")
    public User userId;

    @Column(name = "description")
    public String description;

    @Column(name = "amount")
    public long amount;

    @Column(name = "status")
    public boolean status;

    public Transaction(Long id) {
        this.id = id;
    }
}

