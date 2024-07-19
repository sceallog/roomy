package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String first_name;
    private String last_name;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;
}
