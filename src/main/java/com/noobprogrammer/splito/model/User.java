package com.noobprogrammer.splito.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "paidBy")
    private Set<Expense> expenses = new HashSet<>();

    @Column
    private LocalDateTime createddt;

    @Column
    private LocalDateTime updateddt;

    @PrePersist
    protected void onCreate() {
        createddt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateddt = LocalDateTime.now();
    }

    public Object toUserDetails() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toUserDetails'");
    }
}
