package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 50)
    private String name;

    private int price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendor vendor;

}
