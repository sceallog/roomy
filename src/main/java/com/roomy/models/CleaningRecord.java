package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Cleaning_Records")
public class CleaningRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "checker_id")
    private User checker;

    private String status;
    private LocalDateTime cleaningStartTime;
    private LocalDateTime cleaningEndTime;
    private LocalDateTime checkTime;
    private String remarks;
}
