package com.utcn.medicationplatform.repositories;

import com.utcn.medicationplatform.entities.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, UUID> {

    Optional<Caregiver> findByUsername(String username);

    void deleteByUsername(String username);
}
