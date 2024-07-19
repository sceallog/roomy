package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String name;

    @Column(length = 8)
    private String zipCode;

    @Column(length = 20)
    private String prefecture;

    @Column(length = 20)
    private String city;

    @Column(length = 100)
    private String address;

    @Column(length = 20)
    private String telephoneNo;

    @Column(length = 30)
    private String email;

    @Column(length = 20)
    private String representative;
}
