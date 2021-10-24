package com.varosa.audit.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(name = "accounts")
public class Accounts implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_SEQ_GEN")
    @SequenceGenerator(name = "accounts_SEQ_GEN", sequenceName = "accounts_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "id")
    public Long id;

    @Column(name = "name",unique = true)
    public String name;

    @Column(name = "status")
    public boolean status;

    @Column(name = "level")
    public long level;

    @JsonIgnore
    @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY)
    private List<Accounts> accountsList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "id")
    public Accounts parentId;

    @JsonIgnore
    @OneToMany(mappedBy = "debitAccount", fetch = FetchType.LAZY)
    private List<Transaction> debitTransactionsList;

    @JsonIgnore
    @OneToMany(mappedBy = "creditAccount", fetch = FetchType.LAZY)
    private List<Transaction> creditTransactionsList;

    @Column(name ="all_parent")
    private String all_parent;

    public Accounts(Long id) {
        this.id = id;
    }
}
