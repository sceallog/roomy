package com.roomy.repositories;

import com.roomy.models.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    Optional<List<InventoryItem>> findAllByVendor_Id(Integer vendorId);
}
