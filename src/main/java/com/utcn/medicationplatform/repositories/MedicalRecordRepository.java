package com.utcn.medicationplatform.repositories;

import com.utcn.medicationplatform.entities.MedicationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicationPlan, UUID> {
}
