package com.utcn.medicationplatform.repositories;

import com.utcn.medicationplatform.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, UUID> {

    Optional<Medication> findByName(String name);
    void deleteByName(String name);

}
