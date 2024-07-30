package com.roomy.repositories;

import com.roomy.models.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer>, JpaSpecificationExecutor<InventoryItem> {
    Optional<List<InventoryItem>> findAllByVendor_Id(Integer vendorId);
}
