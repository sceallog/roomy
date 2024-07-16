package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ROOMS")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer roomNumber;
    private String roomType;
    private String cleaningStatus;

    @ManyToOne
    @JoinColumn(name = "CLEANER_ID")
    private User cleaner;

    @ManyToOne
    @JoinColumn(name = "CHECKER_ID")
    private User checker;
}
