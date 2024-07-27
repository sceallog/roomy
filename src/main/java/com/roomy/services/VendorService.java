package com.roomy.services;

import com.roomy.models.Vendor;
import com.roomy.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    private final VendorRepository vendorRepo;

    public VendorService(VendorRepository vendorRepo) { this.vendorRepo = vendorRepo; }
    public List<Vendor> getAllVendors() {
        return vendorRepo.findAll();
    }

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepo.save(vendor);
    }
}
