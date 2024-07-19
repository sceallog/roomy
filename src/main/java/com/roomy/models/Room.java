package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer roomNumber;
    private String roomType;
    private String cleaningStatus;

    @ManyToOne
    @JoinColumn(name = "cleaner_id", referencedColumnName = "id")
    private User cleaner;

    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "id")
    private User checker;
}
