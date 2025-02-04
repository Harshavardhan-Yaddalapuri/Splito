package com.noobprogrammer.splito.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tbl_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "tbl_group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<Expense> expenses = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

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
}