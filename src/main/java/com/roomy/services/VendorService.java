package com.roomy.services;

import com.roomy.models.Vendor;
import com.roomy.repositories.VendorRepository;
import org.springframework.data.jpa.domain.Specification;
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

    public List<Vendor> searchVendors(String keyword) {
        return vendorRepo.findAll((Specification<Vendor>) (root, query, builder) ->
                builder.or(
                        builder.like(root.get("name"), "%" + keyword + "%"),
                        builder.like(root.get("zipCode"), "%" + keyword + "%"),
                        builder.like(root.get("city"), "%" + keyword + "%"),
                        builder.like(root.get("prefecture"), "%" + keyword + "%"),
                        builder.like(root.get("representative"), "%" + keyword + "%"),
                        builder.like(root.get("telephoneNo"), "%" + keyword + "%"),
                        builder.like(root.get("email"), "%" + keyword + "%"),
                        builder.like(root.get("address"), "%" + keyword + "%")
                )
        );
    }
}
