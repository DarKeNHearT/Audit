package com.varosa.audit.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(name ="branch")
public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_SEQ_GEN")
    @SequenceGenerator(name = "branch_SEQ_GEN", sequenceName = "branch_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name ="name",unique = true)
    private String name;

    @Transient
    @OneToMany(mappedBy = "branchId", fetch = FetchType.EAGER)
    private List<User> userList;

    public Branch(Long id) {
        this.id = id;
    }
}
