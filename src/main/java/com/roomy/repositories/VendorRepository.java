package com.roomy.repositories;


import com.roomy.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VendorRepository extends JpaRepository<Vendor, Integer>, JpaSpecificationExecutor<Vendor> {
}
