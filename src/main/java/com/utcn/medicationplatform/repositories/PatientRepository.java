package com.utcn.medicationplatform.repositories;

import com.utcn.medicationplatform.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByUsername(String username);

    void deleteByUsername(String Username);

    List<Patient> findPatientsByCaregiverId(UUID caregiverId);

}
