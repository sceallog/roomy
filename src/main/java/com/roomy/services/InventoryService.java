package com.roomy.services;

import com.roomy.models.InventoryItem;
import com.roomy.models.Vendor;
import com.roomy.repositories.InventoryItemRepository;
import com.roomy.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepo;
    private final VendorRepository vendorRepo;

    public InventoryService(InventoryItemRepository inventoryItemRepo, VendorRepository vendorRepo) {
        this.inventoryItemRepo = inventoryItemRepo;
        this.vendorRepo = vendorRepo;
    }

    public List<InventoryItem> getAllItems() {
        return inventoryItemRepo.findAll();
    }

    public InventoryItem saveItem(InventoryItem item) {
        return inventoryItemRepo.save(item);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepo.findAll();
    }

    public Vendor getVendorById(int id) {
        Optional<Vendor> vendorOptional = vendorRepo.findById(id);
        return vendorOptional.orElseThrow(() -> new RuntimeException("仕入れ先を見つかりませんでした。"));
    }

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepo.save(vendor);
    }
}
