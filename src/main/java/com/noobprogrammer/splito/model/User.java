package com.noobprogrammer.splito.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_user")
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
