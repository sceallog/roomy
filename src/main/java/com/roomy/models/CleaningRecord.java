package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CLEANING_RECORDS")
public class CleaningRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "CHECKER_ID")
    private User checker;

    private String status;
    private LocalDateTime cleaningStartTime;
    private LocalDateTime cleaningEndTime;
    private LocalDateTime checkTime;
    private String remarks;
}
