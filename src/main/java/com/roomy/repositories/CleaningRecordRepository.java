package com.roomy.repositories;

import com.roomy.models.CleaningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleaningRecordRepository extends JpaRepository<CleaningRecord, Integer> {
}
