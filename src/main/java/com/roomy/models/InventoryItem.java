package com.roomy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Inventory_Items")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String name;

    private int price;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendor vendor;

}
