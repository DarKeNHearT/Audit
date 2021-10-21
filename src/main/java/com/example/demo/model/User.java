package com.example.demo.model;

import com.example.demo.model.Branch;
import com.example.demo.model.Role;
import com.example.demo.model.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_SEQ_GEN")
    @SequenceGenerator(name = "user_SEQ_GEN", sequenceName = "user_SEQ", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, unique = true)
    private String email;

    @JoinColumn(name = "branchId", referencedColumnName = "id")
    @ManyToOne
    private Branch branchId;

    @ManyToMany
    @JoinTable(name = "user_role_group", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Expose
    @JsonIgnore
    private Collection<Role> roles;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<Transaction> transactionList;

    public User(Long id) {
        this.id = id;
    }
}